package com.germano.desafiostone.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.germano.desafiostone.Constants;
import com.germano.desafiostone.R;
import com.germano.desafiostone.Singleton;
import com.germano.desafiostone.models.Payment;
import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.presenters.PaymentPresenter;
import com.germano.desafiostone.presenters.PaymentPresenterImpl;
import com.germano.desafiostone.services.HistoryRealm;
import com.germano.desafiostone.utils.CreditCardMask;
import com.germano.desafiostone.utils.FormatNumber;
import com.germano.desafiostone.utils.Keyboard;
import com.germano.desafiostone.views.PaymentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PaymentActivity extends AppCompatActivity implements PaymentView{

    @BindView(R.id.edittext_card_number)
    EditText mEditTextCardNumber;

    @BindView(R.id.edittext_name_holder)
    EditText mEditTextNameHolder;

    @BindView(R.id.edittext_month)
    EditText mEditTextMonth;

    @BindView(R.id.edittext_year)
    EditText mEditTextYear;

    @BindView(R.id.edittext_cvv)
    EditText mEditTextCvv;

    @BindView(R.id.textview_sum)
    TextView mTextViewSum;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    int mTotalValue;

    PaymentPresenter mEventHandler;

    ProgressDialog mProgressDialog;

    Intent mIntent;

    @OnClick(R.id.btn_buy_action)
    void onClickBuyAction(){
        mEventHandler.onPaymentClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        mIntent = getIntent();
        mTotalValue = mIntent.getIntExtra(Constants.TOTAL_VALUE, 0);

        mEventHandler = new PaymentPresenterImpl(this);
        mEventHandler.initView(this);
    }

    @Override
    public void contentView() {
        mTextViewSum.setText(getString(R.string.total_value_cart, FormatNumber.set(mTotalValue)));
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.wait));

        mToolbar.setTitle(getString(R.string.title_payment));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void showSuccessfulPayment() {
        Keyboard.hide(this);
        Toast.makeText(getApplicationContext(), "Pagamento realizado com sucesso!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        Singleton.getInstance().setProductList(new ArrayList<>());
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void callPaymentService() {
        if(verifyFields()){
            Payment payment = new Payment();
            payment.setCardHolderName(mEditTextNameHolder.getText().toString());
            payment.setCardNumber(mEditTextCardNumber.getText().toString());
            payment.setCvv(Integer.parseInt(mEditTextCvv.getText().toString()));
            payment.setValue(mTotalValue);
            payment.setExpDate(getString(R.string.exp_date,
                    Integer.parseInt(mEditTextMonth.getText().toString()),
                    Integer.parseInt(mEditTextYear.getText().toString())));
            mEventHandler.doPaymentService(payment);
        }
    }



    private boolean verifyFields(){
        if(mEditTextCardNumber.getText().toString().length() == 0){
            mEditTextCardNumber.setError("O campo Número do Cartão está vazio");
            mEditTextCardNumber.requestFocus();
            return false;
        } else if(mEditTextNameHolder.getText().toString().length() == 0){
            mEditTextNameHolder.setError("O campo Nome do Portador está vazio");
            mEditTextNameHolder.requestFocus();
            return false;
        } else if(mEditTextMonth.getText().toString().length() == 0){
            mEditTextMonth.setError("O campo Mês está vazio");
            mEditTextMonth.requestFocus();
            return false;
        } else if(mEditTextYear.getText().toString().length() == 0){
            mEditTextYear.setError("O campo Ano está vazio");
            mEditTextYear.requestFocus();
            return false;
        } else if(mEditTextCvv.getText().toString().length() == 0){
            mEditTextCvv.setError("O campo CVV está vazio");
            mEditTextCvv.requestFocus();
            return false;
        }
        return true;
    }


}
