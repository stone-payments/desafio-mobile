package br.com.valdir.desafiolojastarwars;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.valdir.desafiolojastarwars.data.ProdutosContract;

/**
 * Created by valdyrtorres on 13/11/2017.
 */

public class ProdutosAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_DESTAQUE = 0;

    private static final int VIEW_TYPE_ITEM = 1;

    private boolean useProdutoDestaque = false;

    public ProdutosAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public static class ItemProdutoHolder {
        TextView title;
        TextView price;
        TextView zipcode;
        TextView seller;
        ImageView thumbnailHd;
        TextView data;

        public ItemProdutoHolder(View view) {
           title = view.findViewById(R.id.item_title);
           price = view.findViewById(R.id.item_price);
           zipcode = view.findViewById(R.id.item_zipcode);
           seller = view.findViewById(R.id.item_seller);
           data = view.findViewById(R.id.item_data);
           thumbnailHd = view.findViewById(R.id.item_thumbnailhd);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        switch (viewType) {
            case VIEW_TYPE_DESTAQUE: {
                layoutId = R.layout.item_produto_destaque;
                break;
            }
            case VIEW_TYPE_ITEM: {
                layoutId = R.layout.item_produto;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ItemProdutoHolder holder = new ItemProdutoHolder(view);
        view.setTag(holder);

        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ItemProdutoHolder holder = (ItemProdutoHolder) view.getTag();

        int viewType = getItemViewType(cursor.getPosition());

        int titleIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_TITLE);
        int priceIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_PRICE);
        int zipcodeIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_ZIPCODE);
        int sellerIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_SELLER);
        int thumbnailHdIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_THUMBNAILHD_PATH);
        int dataIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_DATA);

        switch (viewType) {
            case VIEW_TYPE_DESTAQUE: {
                holder.title.setText(cursor.getString(titleIndex));
                holder.price.setText("R$ " + String.format("%.2f", cursor.getDouble(priceIndex)));
                Picasso.with(context).load(cursor.getString(thumbnailHdIndex)).into(holder.thumbnailHd);
                break;
            }
            case VIEW_TYPE_ITEM: {
                holder.title.setText(cursor.getString(titleIndex));
                holder.price.setText("R$ " + String.format("%.2f", cursor.getDouble(priceIndex)));
                holder.zipcode.setText(cursor.getString(zipcodeIndex));
                holder.seller.setText(cursor.getString(sellerIndex));
                Picasso.with(context).load(cursor.getString(thumbnailHdIndex)).into(holder.thumbnailHd);
                holder.data.setText(cursor.getString(dataIndex));
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && useProdutoDestaque ? VIEW_TYPE_DESTAQUE : VIEW_TYPE_ITEM);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void setUseProdutoDestaque(boolean useProdutoDestaque) {
        this.useProdutoDestaque = useProdutoDestaque;
    }
}
