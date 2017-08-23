package br.com.tiago.desafiomobile.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.tiago.desafiomobile.model.ProductModel;
import br.com.tiago.desafiomobile.model.TransactionModel;

/**
 * Created by TiagoCabral on 8/21/2017.
 */

public abstract class BaseFragment extends Fragment {

    private static List<ProductModel> cartList;
    private static List<ProductModel> productList;
    private static TransactionModel transactionModel;

    /**
     * Show toast method.
     *
     * @param msg
     * @param duration - (SHORT  = 0, LONG = 1)
     */
    public void showToast(String msg, int duration) {
        Toast.makeText(getActivity(), msg, duration).show();
    }

    public static List<ProductModel> getProductList() {
        return productList == null ? productList = new ArrayList<>() : productList;
    }

    public static List<ProductModel> getCartList() {
        return cartList == null ? cartList = new ArrayList<>() : cartList;
    }

    public static TransactionModel getTransactionModel() {
        return transactionModel == null ? transactionModel = new TransactionModel() : transactionModel;
    }
}
