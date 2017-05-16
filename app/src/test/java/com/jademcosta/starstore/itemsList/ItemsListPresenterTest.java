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
    private ItemsListContract.Model modelMock;

    @Mock
    private ItemsListContract.View viewMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        subject = new ItemsListPresenter();
        subject.setModel(modelMock);
        subject.setView(viewMock);
    }

    @Test
    public void whenOnCreateIsCalled_itShouldAskViewToShowTheLoading() {
         subject.onCreate();
        verify(viewMock).showLoading();
        verify(viewMock).hideList();
    }

    @Test
    public void whenOnCreateIsCalled_itShouldAskModelToFetchItems() {
        subject.onCreate();
        verify(modelMock).getItemsList();
    }

    @Test
    public void whenItemsListIsFetched_itShouldSetThemOnView() {
        List<Item> fakeItemsList = new ArrayList<>();
        subject.onItemsListFetched(fakeItemsList);
        verify(viewMock).setListItems(fakeItemsList);
    }

    @Test
    public void whenItemsListIsFetched_itShouldAskViewToShowTheList() {
        subject.onItemsListFetched(new ArrayList<Item>());
        verify(viewMock).hideLoading();
        verify(viewMock).showList();
    }
}
