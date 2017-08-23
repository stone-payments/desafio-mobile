package br.com.tiago.desafiomobile.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.tiago.desafiomobile.R;
import br.com.tiago.desafiomobile.model.ProductModel;
import br.com.tiago.desafiomobile.ui.fragment.BaseFragment;
import br.com.tiago.desafiomobile.utils.FormatItemValue;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TiagoCabral on 8/21/2017.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder> {


    private List<ProductModel> cartList;

    public CartRecyclerAdapter() {
        this.cartList = BaseFragment.getCartList();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.title.setText(cartList.get(position).getTitle());
        holder.price.setText(FormatItemValue.priceFormat(cartList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public ProductModel getItem(int position) {
        return cartList.get(position);
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cart_title)
        TextView title;
        @BindView(R.id.cart_price)
        TextView price;

        public CartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
