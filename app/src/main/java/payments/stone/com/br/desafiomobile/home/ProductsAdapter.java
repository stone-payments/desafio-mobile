package payments.stone.com.br.desafiomobile.home;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.checkout.AddCartItemDialog;
import payments.stone.com.br.desafiomobile.commons.Navigation;
import payments.stone.com.br.desafiomobile.model.CartItem;
import payments.stone.com.br.desafiomobile.model.Product;
import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.commons.Utils;

/**
 * Created by william.gouvea on 9/19/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context mContext;
    private List<Product> productList;
    private Navigation mNavigation;
    private AddCartItemDialog.AddCartItemDialogListener listener;

    public ProductsAdapter(Context mContext, List<Product> productList, Navigation mNavigation, AddCartItemDialog.AddCartItemDialogListener listener) {
        this.mContext = mContext;
        this.productList = productList;
        this.mNavigation = mNavigation;
        this.listener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.title.setText(product.getTitle());
        holder.seller.setText(product.getSeller());
        holder.date.setText(product.getDate());
        holder.price.setText(Utils.getPriceFormatted(product.getPrice()));
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigation.whenGoToDetails(product);
            }
        });

        // loading album cover using Glide library
        Glide
                .with(mContext)
                .load(product.getThumbnailHd())
                .into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, product, listener);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, Product product, AddCartItemDialog.AddCartItemDialogListener listener) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_product_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new ProductOverflowMenuItemClickListener(product,listener));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    public class ProductOverflowMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        Product product;
        AddCartItemDialog.AddCartItemDialogListener listener;

        public ProductOverflowMenuItemClickListener(Product product, AddCartItemDialog.AddCartItemDialogListener listener) {
            this.product = product;
            this.listener = listener;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_cart:
                    mNavigation.showQuantityDialog(new CartItem(product),listener);

                    return true;

                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView title, seller, date, price;
        public ImageView thumbnail, overflow;
        public View mRoot;


        public ProductViewHolder(View view) {
            super(view);
            mRoot = view;
            title = (TextView) mRoot.findViewById(R.id.title);
            seller = (TextView) mRoot.findViewById(R.id.seller);
            date = (TextView) mRoot.findViewById(R.id.date);
            price = (TextView) mRoot.findViewById(R.id.count);
            thumbnail = (ImageView) mRoot.findViewById(R.id.thumbnail);
            overflow = (ImageView) mRoot.findViewById(R.id.overflow);
        }
    }
}
