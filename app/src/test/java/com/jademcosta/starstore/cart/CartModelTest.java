package com.jademcosta.starstore.cart;


import com.jademcosta.starstore.database.ItemsRepository;
import com.jademcosta.starstore.entity.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartModelTest {

    private CartModel subject;

    @Mock
    private ItemsRepository itemsRepositoryMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new CartModel(itemsRepositoryMock);
    }

    @Test
    public void whenAskedToFetchCartItems_itShouldAskTheRepository() {
        subject.getCartItemsList();
        verify(itemsRepositoryMock).getItems();
    }

    @Test
    public void whenAskedToFetchCartItems_itShouldAnswerExactlyWhatTheRepositoryGives() {
        List<Item> fakeItemsList = new ArrayList<>();
        when(itemsRepositoryMock.getItems()).thenReturn(fakeItemsList);
        assertTrue(fakeItemsList == subject.getCartItemsList());
    }
}
