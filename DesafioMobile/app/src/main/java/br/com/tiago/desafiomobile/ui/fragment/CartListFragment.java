package br.com.tiago.desafiomobile.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.tiago.desafiomobile.R;
import br.com.tiago.desafiomobile.interactor.impl.TransactionInteractorImpl;
import br.com.tiago.desafiomobile.model.TransactionModel;
import br.com.tiago.desafiomobile.presenter.CartPresenter;
import br.com.tiago.desafiomobile.presenter.impl.CartPresenterImpl;
import br.com.tiago.desafiomobile.ui.adapter.CartRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TiagoCabral on 8/21/2017.
 */

public class CartListFragment extends BaseFragment {

    private static String TAG = CartListFragment.class.getSimpleName();
    private static CartRecyclerAdapter cartRecyclerAdapter;
    private CartPresenter cartPresenter;
    private Bundle bundle;
    private TransactionModel transactionModel;

    @BindView(R.id.cart_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab_shop)
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inicio");
        this.bundle = savedInstanceState;
        View inflate = inflater.inflate(R.layout.cart_fragment, container, false);
        ButterKnife.bind(this, inflate);

        cartRecyclerAdapter = new CartRecyclerAdapter();
        this.recyclerView.setAdapter(cartRecyclerAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cartPresenter = new CartPresenterImpl(this, new TransactionInteractorImpl(getContext()));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getCartList().isEmpty())
                    openDialog();
                else showToast("Carrinho vazio!", 0);
            }
        });

        return inflate;
    }

    public void openDialog() {

        View alertLayout = getLayoutInflater(this.bundle).inflate(R.layout.transaction_data_dialog, null);
        final EditText cardName = (EditText) alertLayout.findViewById(R.id.transaction_dialog_card_holder_name);
        final EditText cardNumber = (EditText) alertLayout.findViewById(R.id.transaction_dialog_card_number);
        final EditText cvv = (EditText) alertLayout.findViewById(R.id.transaction_dialog_cvv);
        final EditText expDate = (EditText) alertLayout.findViewById(R.id.transaction_dialog_exp_date);

        cardName.setHint(R.string.hint_card_name);
        cardNumber.setHint(R.string.hint_card_number);
        cvv.setHint(R.string.hint_cvv);
        expDate.setHint(R.string.hint_exp_date);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Dados do cartão");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                transactionModel = getTransactionModel();
                transactionModel.setCardHolderName(cardName.getText().toString());
                transactionModel.setCardNumber(cardNumber.getText().toString());
                transactionModel.setCvv(Integer.parseInt(cvv.getText().toString()));
                transactionModel.setExpDate(expDate.getText().toString());

                cartPresenter.insertTransaction(transactionModel);
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void cleanCartList() {
        getCartList().clear();
        cartRecyclerAdapter.notifyDataSetChanged();
    }

    public static CartRecyclerAdapter getCartRecyclerAdapter() {
        return cartRecyclerAdapter;
    }

    public void setTransactionModel(TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
    }
}
