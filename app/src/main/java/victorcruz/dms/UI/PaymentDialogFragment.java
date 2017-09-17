package victorcruz.dms.UI;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import victorcruz.dms.R;
import victorcruz.dms.get_post_data.PostCardInformation;
import victorcruz.dms.produto.ProductHandler;

public class PaymentDialogFragment extends DialogFragment {

    private EditText cardNumberEditText, cardNameEditText, cardCVVEditText, cardExpDateEditText;
    private ProductHandler productHandler;

    public void setArguments(ProductHandler productHandler){
        this.productHandler = productHandler;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.input_cart_dialog, null);

        // coleta os dados para o POST
        final JSONObject jsonObject = makeJson(productHandler.getCartTotalValue(), view);

        builder.setMessage(R.string.finish_payment)
                .setView(view)
                .setPositiveButton(R.string.confirm_payment, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        PostCardInformation postCardInformation = new PostCardInformation();
                        postCardInformation.execute(jsonObject.toString());
                        System.out.println("O cartao eh: " + cardNumberEditText.getText());
                        productHandler.resetCart();
                    }
                })
                .setNegativeButton(R.string.cancel_payment, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public JSONObject makeJson(int valorTotal, View view){

        cardNumberEditText = (EditText)  view.findViewById(R.id.cardNumberEditText);
        cardNameEditText = (EditText)  view.findViewById(R.id.cardNameEditText);
        cardCVVEditText = (EditText)  view.findViewById(R.id.cardCVVEditText);
        cardExpDateEditText = (EditText)  view.findViewById(R.id.cardExpDateEditText);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("card_number", cardNumberEditText.getText());
            jsonObject.accumulate("value", valorTotal);
            jsonObject.accumulate("cvv", cardCVVEditText.getText());
            jsonObject.accumulate("card_holder_name", cardNameEditText.getText());
            jsonObject.accumulate("exp_date", cardExpDateEditText.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }



}
