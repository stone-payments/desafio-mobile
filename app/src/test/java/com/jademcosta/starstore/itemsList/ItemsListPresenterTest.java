package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.entity.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

public class ItemsListPresenterTest {

    private ItemsListPresenter subject;

    @Mock
    private ItemsListContract.Model model;

    @Mock
    private ItemsListContract.View view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        subject = new ItemsListPresenter();
        subject.setModel(model);
        subject.setView(view);
    }

    @Test
    public void whenOnCreateIsCalled_itShouldAskViewToShowTheLoading() {
         subject.onCreate();
        verify(view).showLoading();
        verify(view).hideList();
    }

    @Test
    public void whenOnCreateIsCalled_itShouldAskModelToFetchItems() {
        subject.onCreate();
        verify(model).getItemsList();
    }

    @Test
    public void whenItemsListIsFetched_itShouldSetThemOnView() {
        List<Item> fakeItemsList = new ArrayList<>();
        subject.onItemsListFetched(fakeItemsList);
        verify(view).setListItems(fakeItemsList);
    }

    @Test
    public void whenItemsListIsFetched_itShouldAskViewToShowTheList() {
        subject.onItemsListFetched(new ArrayList<Item>());
        verify(view).hideLoading();
        verify(view).showList();
    }
}
