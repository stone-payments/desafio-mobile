package com.hernand.starwars.controller;

import com.hernand.starwars.model.dao.TransactDao;
import com.hernand.starwars.view.PaymentActivity;
import com.hernand.starwars.vo.ProductVO;
import com.hernand.starwars.vo.TransactionVO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nando on 30/05/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    private PaymentController controller;

    @Mock
    private PaymentActivity paymentActivity;

    @Mock
    private TransactDao dao;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        controller = new PaymentController(paymentActivity);

        Field f = PaymentController.class.getDeclaredField("transactDao");

        f.setAccessible(true);
        f.set(controller,dao);
    }

    @Test
    public void testShouldSaveTransact(){
        TransactionVO vo = new TransactionVO();
        vo.setNomePortador("Hernand");
        vo.setValor(20000L);
        vo.setNumeroCartao("8888222244446666");
        controller.saveTransaction(vo);

        Mockito.verify(dao).saveTransaction(vo);

    }
    @Test
    public void testTotalProducts(){
        List<ProductVO> productVOs = new ArrayList<>();
        ProductVO p1 = new ProductVO();
        p1.setPrice(20L);
        ProductVO p2 = new ProductVO();
        p2.setPrice(30L);
        productVOs.add(p1);
        productVOs.add(p2);
        Long total = controller.getTotal(productVOs);

        Assert.assertNotNull(total);
        Assert.assertEquals(50L,total.longValue());

    }

}
