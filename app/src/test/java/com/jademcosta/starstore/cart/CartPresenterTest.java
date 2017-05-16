package com.jademcosta.starstore.cart;


import android.content.Context;

import com.jademcosta.starstore.entity.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class CartPresenterTest {

    private CartPresenter subject;

    @Mock
    private CartContract.Model modelMock;

    @Mock
    private CartContract.View viewMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        subject = new CartPresenter();
        subject.setModel(modelMock);
        subject.setView(viewMock);
    }

    @Test
    public void whenOnCreateIsCalled_itShouldAskTheModelToFetchTheList() {
        subject.onCreate();
        verify(modelMock).getCartItemsList();
    }

    @Test
    public void whenOnCreateIsCalled_AndListIsEmpty_itShouldAskViewToShowEmpty() {
        when(modelMock.getCartItemsList()).thenReturn(new ArrayList<Item>());
        subject.onCreate();
        verify(viewMock).showEmptyState();
    }

    @Test
    public void whenOnCreateIsCalled_AndListHasItems_itShouldPassItemsListToView() {
        List<Item> fakeItemsList = new ArrayList<>();
        fakeItemsList.add(new Item());

        when(modelMock.getCartItemsList()).thenReturn(fakeItemsList);
        subject.onCreate();
        verify(viewMock).setCartItems(fakeItemsList);
    }

    @Test
    public void whenOnCreateIsCalled_AndListHasItems_itShouldPassTheSumOfItemsPriceToView() {
        List<Item> fakeItemsList = new ArrayList<>();
        int fakePrice1 = 2;
        int fakePrice2 = 77;
        fakeItemsList.add(new Item("", fakePrice1, "", "", "", ""));
        fakeItemsList.add(new Item("", fakePrice2, "", "", "", ""));

        when(modelMock.getCartItemsList()).thenReturn(fakeItemsList);
        subject.onCreate();
        verify(viewMock).setCartItemsTotalPrice(String.valueOf(fakePrice1 + fakePrice2));
    }

    @Test
    public void whenCheckingOut_AndListHasNoItems_itShouldNotStartTheNextActivity() {
        when(modelMock.getCartItemsList()).thenReturn(new ArrayList<Item>());
        Context contextMock = mock(Context.class);
        subject.checkoutClicked(contextMock);
        verifyZeroInteractions(contextMock);
    }
}
