package kelly.com.desafiostone.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import kelly.com.desafiostone.R;
import kelly.com.desafiostone.models.Item;

/**
 * Created by kelly on 30/11/17.
 */

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> itens;
    private LayoutInflater mInflater;

    private ListAdapterListener mListener;

    public ItemAdapter(Context context, ArrayList <Item> produtos, ListAdapterListener mListener) {
        this.context = context;
        this.itens = produtos;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
        this.mListener = mListener;
    }

    public interface ListAdapterListener {
        void onClickAtItem(Item item);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Item getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Item item = itens.get(position);
        view = mInflater.inflate(R.layout.item_list, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(item.getTitle());

        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvPrice.setText("R$" + String.format("%.2f", item.getPrice()));

        TextView tvSeller = (TextView) view.findViewById(R.id.tv_seller);
        tvSeller.setText(item.getSeller());

        ImageView imageItem = (ImageView) view.findViewById(R.id.image_item);
        if (item.getImageBitmap() != null){
            imageItem.setImageBitmap(item.getImageBitmap());
        } else{
            new DownloadImagesTask(imageItem, item).execute(item.getImageURL());
        }

        Button btnAddCart = (Button) view.findViewById(R.id.btn_add_cart);
        if (mListener != null) {
            btnAddCart.setVisibility(View.VISIBLE);
            btnAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickAtItem(item);
                }
            });
        } else {
            btnAddCart.setVisibility(View.GONE);
        }

        return view;

    }

    public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView = null;
        Item item;

        public DownloadImagesTask(ImageView image, Item item) {
            this.item = item;
            imageView = image;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return download_Image(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (imageView != null) {
                imageView.setImageBitmap(result);
                item.setImageBitmap(result);
            }
        }

        private Bitmap download_Image(String url) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("Hub", "Error getting the image from server : " + e.getMessage().toString());
            }
            return bm;
        }
    }
}
