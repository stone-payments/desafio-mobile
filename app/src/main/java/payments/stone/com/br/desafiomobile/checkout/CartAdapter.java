package payments.stone.com.br.desafiomobile.checkout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.commons.Navigation;
import payments.stone.com.br.desafiomobile.commons.Utils;
import payments.stone.com.br.desafiomobile.model.CartItem;

/**
 * Created by william.gouvea on 9/21/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {
    private Context mContext;
    private List<CartItem> items;
    private Navigation mNavigation;
    private AddCartItemDialog.AddCartItemDialogListener listener;

    public CartAdapter(Context mContext, List<CartItem> items, Navigation mNavigation, AddCartItemDialog.AddCartItemDialogListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.mNavigation = mNavigation;
        this.listener = listener;
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new CartAdapter.CartItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        final CartItem item = items.get(position);
        holder.title.setText(item.getProduct().getTitle());
        holder.seller.setText(item.getProduct().getSeller());
        holder.price.setText(item.getCount() + " x " + Utils.getPriceFormatted(item.getProduct().getPrice()));

        if (!TextUtils.isEmpty(item.getProduct().getThumbnailHd())) {
            Glide
                    .with(mContext)
                    .load(item.getProduct().getThumbnailHd())
                    .into(holder.thumbnail);
        }

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigation.showQuantityDialog(item,listener);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title, seller, date, price;
        public ImageView thumbnail, overflow;
        public View mRoot;


        public CartItemViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView;
            title = (TextView) mRoot.findViewById(R.id.item_title);
            seller = (TextView) mRoot.findViewById(R.id.item_seller);
//            date = (TextView) mRoot.findViewById(R.id.date);
            price = (TextView) mRoot.findViewById(R.id.item_amount);
            thumbnail = (ImageView) mRoot.findViewById(R.id.item_image);
//            overflow = (ImageView) mRoot.findViewById(R.id.overflow);
        }
    }
}
