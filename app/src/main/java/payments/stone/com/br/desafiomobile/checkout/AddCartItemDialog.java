package payments.stone.com.br.desafiomobile.checkout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.model.CartItem;

/**
 * Created by william.gouvea on 9/26/17.
 */

public class AddCartItemDialog extends DialogFragment {

    private EditText mEditText;
    private Button mIncrementButton;
    private Button mDecrementButton;

    private AddCartItemDialogListener listener;

    public AddCartItemDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddCartItemDialog newInstance(String title, CartItem current, AddCartItemDialogListener listener) {
        AddCartItemDialog frag = new AddCartItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putParcelable("item",current);
        frag.setArguments(args);
        frag.setListener(listener);
        return frag;
    }

    public interface AddCartItemDialogListener {
        void onFinishAddCartItemDialog(CartItem item, int amount);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        final CartItem item = getArguments().getParcelable("item");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
//        alertDialogBuilder.setMessage("Are you sure?");

        LayoutInflater inflater = (LayoutInflater) ShopitApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.add_cart_item_dialog, null);
        mEditText = (EditText) dialogView.findViewById(R.id.item_count);
        mIncrementButton = (Button)dialogView.findViewById(R.id.inc_button);
        mDecrementButton = (Button)dialogView.findViewById(R.id.dec_button);


        mEditText.setText(item.getCount() + "");


        mIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.parseInt(mEditText.getText().toString());
                current++;
                mEditText.setText(current + "");
            }
        });

        mDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.parseInt(mEditText.getText().toString());
                if(current > 0) {
                    current--;
                    mEditText.setText(current + "");
                }
            }
        });

        alertDialogBuilder.setView(dialogView);

        alertDialogBuilder.setPositiveButton("Save",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                if(listener!=null){
//                    item.setCount(Integer.parseInt(mEditText.getText().toString()));
                    listener.onFinishAddCartItemDialog(item, Integer.parseInt(mEditText.getText().toString()));
                }

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }

        });

        return alertDialogBuilder.create();
    }


    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.add_cart_item_dialog, container);
//    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        // Get field from view
//        mEditText = (EditText) view.findViewById(R.id.item_count);
//        // Fetch arguments from bundle and set title
//        String title = getArguments().getString("title", "Quantity");
//        int currentQuantity = getArguments().getInt("current");
//
//        getDialog().setTitle(title);
//        // Show soft keyboard automatically and request focus to field
//        mEditText.setText(currentQuantity + "");
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//    }

    public void setListener(AddCartItemDialogListener listener) {
        this.listener = listener;
    }
}
