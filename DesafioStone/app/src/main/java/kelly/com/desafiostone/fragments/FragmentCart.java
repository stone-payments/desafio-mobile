package kelly.com.desafiostone.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kelly.com.desafiostone.R;
import kelly.com.desafiostone.adapters.ItemAdapter;
import kelly.com.desafiostone.interfaces.ComunicatorActivityFragment;
import kelly.com.desafiostone.interfaces.ComunicatorFragmentActivity;
import kelly.com.desafiostone.models.Item;


public class FragmentCart extends Fragment implements ComunicatorActivityFragment {

    private ListView mItemList;
    private TextView mTextViewTotal;
    private ComunicatorFragmentActivity mComunicatorFragmentActivity;

    public FragmentCart() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewTotal = (TextView) view.findViewById(R.id.tv_total);

        mItemList = (ListView) view.findViewById(R.id.lv_itens_list);
        ItemAdapter itemAdapter = new ItemAdapter(getContext(), new ArrayList<Item>(), null);
        mItemList.setAdapter(itemAdapter);

        Button btnProcedCheckout = (Button) view.findViewById(R.id.btn_proceed_checkout);
        btnProcedCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProceedCheckoutCartDialog();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mComunicatorFragmentActivity = (ComunicatorFragmentActivity) getContext();
    }

    @Override
    public void setListItensAdded(ArrayList<Item> itensAddedCart) {
        ItemAdapter itemAdapter = new ItemAdapter(getContext(), itensAddedCart, null);
        mItemList.setAdapter(itemAdapter);

        mTextViewTotal.setText("R$" + String.format("%.2f", getTotal(itensAddedCart)));
    }

    private double getTotal (ArrayList<Item> itensList){
        double total = 0;

        for (Item item : itensList){
            total += item.getPrice();
        }

        return total;
    }

    private void showProceedCheckoutCartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.message_proceed_to_checkou);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mComunicatorFragmentActivity.procedToCheckout();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
