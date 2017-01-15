package com.am.store.starwars.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.am.store.starwars.R;
import com.am.store.starwars.core.ShoppingCartManager;
import com.am.store.starwars.exception.StarWarPersistenceException;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.exception.StarWarsException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.helper.formatter.CurrencyFormatter;
import com.am.store.starwars.integration.store.service.PaymentService;
import com.am.store.starwars.integration.store.vo.request.payment.PaymentRequestVO;
import com.am.store.starwars.model.store.product.Purchase;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Augusto on 15/01/2017.
 */

public class CheckoutActivity extends AppCompatActivity {

    private static final String LOG_CONSTANT = CheckoutActivity.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private int MY_SCAN_REQUEST_CODE = 100; // arbitrary int

    private TextView txtAmount;
    private EditText editCardHolderName;
    private EditText editCardNumber;
    private EditText editValidate;
    private EditText editCVV;
    private Button btnConfirm;

    private ShoppingCartManager shoppingCartManager;
    private PaymentService paymentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_checkout);

        shoppingCartManager = new ShoppingCartManager();
        paymentService = new PaymentService();

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtAmount = (TextView) findViewById(R.id.activity_checkout_txtAmount);
        editCardHolderName = (EditText) findViewById(R.id.activity_checkout_name);
        editCardNumber = (EditText) findViewById(R.id.activity_checkout_card);
        editValidate = (EditText) findViewById(R.id.activity_checkout_validate);
        editCVV = (EditText) findViewById(R.id.activity_checkout_cvv);
        btnConfirm = (Button) findViewById(R.id.activity_checkout_btnConfirm);

        try {
            txtAmount.setText(CurrencyFormatter.transformToCurrency(String.valueOf(shoppingCartManager.getShoppingCartAmount())));
        } catch (StarWarsException e) {
            logger.error(LOG_CONSTANT, "Problems to show Shoppingcart amount in View!", e);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    paymentService.performPayment(createPaymentRequest());

                    new AlertDialog.Builder(CheckoutActivity.this)
                            .setTitle("Pagamento aprovado")
                            .setMessage("Parabéns, sua compra foi realizada com sucesso!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setIcon(R.drawable.darth_vader_icon)
                            .show();
                } catch (StarWarServiceException e) {
                    logger.error(LOG_CONSTANT, "Problems to send payment", e);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onScanPress(View v) {
        // This method is set up as an onClick handler in the layout xml
        // e.g. android:onClick="onScanPress"

        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false); // default: false

        // hides the manual entry button
        // if set, developers should provide their own manual entry mechanism in the app
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

        // matches the theme of your application
        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String resultStr;
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

            // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
            resultStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

            // Do something with the raw number, e.g.:
            // myService.setCardNumber( scanResult.cardNumber );

            if (scanResult.isExpiryValid()) {
                resultStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
            }

            if (scanResult.cvv != null) {
                // Never log or display a CVV
                resultStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
            }

            if (scanResult.postalCode != null) {
                resultStr += "Postal Code: " + scanResult.postalCode + "\n";
            }

            if (scanResult.cardholderName != null) {
                resultStr += "Cardholder Name : " + scanResult.cardholderName + "\n";
            }
        } else {
            resultStr = "Scan was canceled.";
        }

        logger.info(LOG_CONSTANT, resultStr);
    }

    private PaymentRequestVO createPaymentRequest() {
        PaymentRequestVO request = new PaymentRequestVO();
        request.setAmount(shoppingCartManager.getShoppingCartAmount());
        request.setCardHolderName(editCardHolderName.getText().toString());
        request.setCardNumber(editCardNumber.getText().toString());
        request.setCvv(Integer.parseInt(editCVV.getText().toString()));
        request.setExpDate(editValidate.getText().toString());

        return request;
    }
}