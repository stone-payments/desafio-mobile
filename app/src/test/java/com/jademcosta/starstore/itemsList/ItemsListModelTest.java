package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.database.ItemsRepository;
import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.network.ItemsApi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import retrofit2.Callback;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ItemsListModelTest {

    private ItemsListModel subject;

    @Mock
    private ItemsListContract.Model.Presenter presenterMock;

    @Mock
    private ItemsRepository itemsRepositoryMock;

    @Mock
    private ItemsApi itemsApiMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ItemsListModel(itemsApiMock, itemsRepositoryMock);
        subject.setPresenter(presenterMock);
    }

    @Test
    public void whenAddingItemsToCart_itShouldAskRepositoryToStoreThem() {
        Item fakeItem = new Item();
        subject.addItemToCart(fakeItem);
        verify(itemsRepositoryMock).add(fakeItem);
    }

    @Test
    public void whenFetchingItemsFromApi_itShouldAskTheApiToFetchThem() {
        subject.getItemsList();
        verify(itemsApiMock).fetchItems(any(Callback.class));
    }

    @Test
    public void whenFetchingItemsFromApiFails_itShouldTellPresenterItFailed() {
        ArgumentCaptor<Callback> callbackCaptor = ArgumentCaptor.forClass(Callback.class);
        subject.getItemsList();
        verify(itemsApiMock).fetchItems(callbackCaptor.capture());

        Call fakeCall = mock(Call.class);
        callbackCaptor.getValue().onFailure(fakeCall, new Throwable());
        verify(presenterMock).onItemsListFetchFailure();
    }
}
