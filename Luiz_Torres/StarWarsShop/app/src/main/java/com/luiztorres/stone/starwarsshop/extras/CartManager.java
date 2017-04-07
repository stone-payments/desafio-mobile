package com.luiztorres.stone.starwarsshop.extras;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luiztorres.stone.starwarsshop.MainActivity;
import com.luiztorres.stone.starwarsshop.R;
import com.luiztorres.stone.starwarsshop.adapters.CartListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dindal on 03/01/2017.
 */

public class CartManager {
    private static CartManager mInstance;
    private Context ctx;
    private ArrayList<Item> itemsInCart = new ArrayList<Item>();

    private CartManager(Context c) {
        this.ctx = c;
    }

    public static synchronized CartManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CartManager(context);
        }
        return mInstance;
    }

    public void AddItem(final Item item)
    {
        Snackbar.make(((Activity) ctx).findViewById(R.id.activity_main), item.getName()+": "+
                ctx.getResources().getText(R.string.item_add),
                Snackbar.LENGTH_LONG)
                .setAction("Remove", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        RemoveItem(item);
                    }
                })
                .show();

        if(verifyIfExist(item.getName()))
        {
            itemsInCart.get(getIndexOfItemByName(item.getName())).addCount(1);
        }
        else {
            itemsInCart.add(item);
        }

        FeedList();
    }

    public void RemoveItem(Item item)
    {
        if(verifyIfExist(item.getName()))
        {
            if(itemsInCart.get(getIndexOfItemByName(item.getName())).getCount() > 1)
                itemsInCart.get(getIndexOfItemByName(item.getName())).removeCount(1);
            else
                itemsInCart.remove(getIndexOfItemByName(item.getName()));


            Snackbar.make(((Activity) ctx).findViewById(R.id.activity_main), item.getName()+": "+
                    ctx.getResources().getText(R.string.item_remove),
                    Snackbar.LENGTH_SHORT)
                    .show();

            FeedList();
        }
    }

    public List<Item> GetItems()
    {
        return itemsInCart;
    }

    private boolean verifyIfExist(String name)
    {
        for (Item i:itemsInCart) {
            if(i.getName().equals(name))
                return true;
        }

        return false;
    }

    public Item getItemByName(String name)
    {
        for(Item i:itemsInCart)
        {
            if(i.getName().equals(name))
                return i;
        }

        return null;
    }

    public int getIndexOfItemByName(String name)
    {
        for(int i=0;i<itemsInCart.size();i++)
        {
            if(itemsInCart.get(i).getName().equals(name))
            {
                return i;
            }
        }

        return 0;
    }

    public double getTotalPrice()
    {
        double total = 0;

        for (Item i : itemsInCart) {
            total += i.getPrice()*i.getCount();
        }
        return total;
    }

    public void Clear()
    {
        itemsInCart.clear();
    }

    public void FeedList()
    {
        RecyclerView rv_listOfCards = (RecyclerView) ((Activity) ctx).findViewById(R.id.rv_listItemsCart);
        if(rv_listOfCards == null)
            return;

        rv_listOfCards.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(((Activity) ctx));
        rv_listOfCards.setLayoutManager(layoutManager);

        CartListAdapter mAdapter = new CartListAdapter(((Activity) ctx), CartManager.getInstance(((Activity) ctx)).GetItems());
        rv_listOfCards.setAdapter(mAdapter);


        TextView totalCount = (TextView) ((Activity) ctx).findViewById(R.id.tv_cart_totalPrice);
        totalCount.setText("Total R$:" + CartManager.getInstance(((Activity) ctx)).getTotalPrice());
    }
}
