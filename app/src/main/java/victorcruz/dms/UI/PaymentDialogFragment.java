package victorcruz.dms.UI;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import victorcruz.dms.R;

public class PaymentDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setMessage(R.string.finish_payment)
                .setView(inflater.inflate(R.layout.input_cart_dialog, null))
                .setPositiveButton(R.string.confirm_payment, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel_payment, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static PaymentDialogFragment newInstance(String titulo){

        PaymentDialogFragment paymentDialogFragment = new PaymentDialogFragment();
        Bundle bundle = new Bundle();
        //bundle.putString("titulo", titulo);
        paymentDialogFragment.setArguments(bundle);
        return paymentDialogFragment;

    }



}
