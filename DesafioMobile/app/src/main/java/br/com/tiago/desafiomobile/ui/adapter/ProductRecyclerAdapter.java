package br.com.tiago.desafiomobile.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tiago.desafiomobile.R;
import br.com.tiago.desafiomobile.model.ProductModel;
import br.com.tiago.desafiomobile.presenter.ProductListPresenter;
import br.com.tiago.desafiomobile.utils.FormatItemValue;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    private List<ProductModel> products;
    private ProductListPresenter productListPresenter;

    public ProductRecyclerAdapter(ProductListPresenter productListPresenter) {
        this.productListPresenter = productListPresenter;
        this.products = new ArrayList<>();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        holder.title.setText(products.get(position).getTitle());
        holder.price.setText(FormatItemValue.priceFormat(products.get(position).getPrice()));
        holder.seller.setText(FormatItemValue.sellerFormat(products.get(position).getSeller()));
        holder.image.setImageBitmap(products.get(position).getBitmap());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListPresenter.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addAll(List<ProductModel> products) {
        if (products != null && products.size() > 0) {
            this.products.clear();
            this.products.addAll(products);
            notifyDataSetChanged();
        }
    }

    public ProductModel getItem(int position) {
        return products.get(position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_title)
        TextView title;
        @BindView(R.id.product_price)
        TextView price;
        @BindView(R.id.product_image)
        ImageView image;
        @BindView(R.id.product_seller)
        TextView seller;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
