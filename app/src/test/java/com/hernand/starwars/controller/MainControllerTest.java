package com.hernand.starwars.controller;

import com.hernand.starwars.api.IProductService;
import com.hernand.starwars.model.Transact;
import com.hernand.starwars.model.dao.TransactDao;
import com.hernand.starwars.util.CartSingleton;
import com.hernand.starwars.vo.ProductVO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by Nando on 30/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    private MainController controller;
    @Mock
    private TransactDao dao;

    @Before
    public void setup(){
        controller = new MainController(dao);
    }

    @Test
    public void verifyTransactionsNotEmpty() throws Exception {

        List<Transact> transactList = getTransactList();
        Mockito.when(dao.listAll()).thenReturn(transactList);

        controller.listTransactions();

        Assert.assertNotNull("should not be null",transactList);
        Assert.assertFalse("should not be empty",transactList.isEmpty());
    }


    private List<Transact> getTransactList(){
        List<Transact> transactList = new ArrayList<>();
        transactList.add(new Transact());
        return  transactList;
    }

    @Test
    public void verifyProductsNotEmpty(){
        controller = new MainController(dao);

        CartSingleton.getInstance().getProdutos().add(new ProductVO());

        List<ProductVO> productVOs = controller.getProdutos();

        Assert.assertNotNull("should not be null",productVOs);
        Assert.assertFalse("should not be empty",productVOs.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void verifyProductsNullDao(){
        controller = new MainController();

        controller.listTransactions();
    }

}
