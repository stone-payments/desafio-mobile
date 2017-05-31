package com.hernand.starwars.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hernand.starwars.R;
import com.hernand.starwars.controller.PaymentController;
import com.hernand.starwars.model.dao.TransactDao;
import com.hernand.starwars.util.CartSingleton;
import com.hernand.starwars.vo.ProductVO;
import com.hernand.starwars.vo.TransactionVO;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends BaseCompatActivity  {
    @NotEmpty(messageResId = R.string.msg_erro_campo_obrigatorio)
    private EditText etCartao,etNome,etMesCartao,etAnoCartao,etCvv;
    private Validator validator;
    private TransactDao transacaoDao;

    private TextView totalCompras;
    private TransactionVO transacaoVO;

    private AppCompatButton fabPagar;
    private ProgressDialog statusDialog;
    private List<ProductVO> listaProdutos;

    private PaymentController controller;

    public static void navigate(Activity activity, List<ProductVO> produtoVOList){
        Intent intent = new Intent(activity, PaymentActivity.class);
        intent.putExtra(CheckoutActivity.EXTRA_PRODUTOS, (Serializable) produtoVOList);
        ActivityCompat.startActivity(activity,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_payment);
        controller = new PaymentController(this);
        initComponents();

        listaProdutos = (List<ProductVO>) getIntent().getSerializableExtra(EXTRA_PRODUTOS);
        if(listaProdutos != null && !listaProdutos.isEmpty()) {
            totalCompras.setText(String.format("Total R$: %s",controller.getTotal(listaProdutos).toString()));
            fabPagar.setEnabled(true);
        }

        fabPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();

        transacaoVO = preencherTrasacaoVO();
        controller.saveTransaction(transacaoVO);

        statusDialog.setMessage(getString(R.string.carregando));
        statusDialog.show();
        controller.chamadaApiRestPagamento(transacaoVO, new Callback<TransactionVO>() {
            @Override
            public void onResponse(Call<TransactionVO> call, Response<TransactionVO> response) {
                statusDialog.dismiss();
                CartSingleton.getInstance().getProdutos().clear();
                new AlertDialog.Builder(PaymentActivity.this)
                        .setCancelable(false)
                        .setMessage(getString(R.string.sucesso_transacao))
                        .setNeutralButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.navigate(PaymentActivity.this);
                            }
                        })
                        .create().show();
            }
            @Override
            public void onFailure(Call<TransactionVO> call, Throwable t) {
                Toast.makeText(PaymentActivity.this,getString(R.string.verifique_conexao_internet), Toast.LENGTH_LONG).show();
                statusDialog.dismiss();
            }
        });
    }

    public TransactionVO preencherTrasacaoVO(){
        TransactionVO transacaoVO = new TransactionVO();
        transacaoVO.setExpDate(controller.getExpirationMonthYear(etMesCartao,etAnoCartao));
        transacaoVO.setValor(controller.getTotal(listaProdutos));
        transacaoVO.setCvv(Long.valueOf(etCvv.getText().toString()));
        transacaoVO.setNomePortador(etNome.getText().toString());
        transacaoVO.setNumeroCartao(etCartao.getText().toString().replaceAll("-",""));
        return  transacaoVO;
    }

    /**
     * Inicializa os componentes dessa activity
     */
    private void initComponents() {
        initToolbar(getText(R.string.dados_pagamento).toString());
        validator = new Validator(this);
        validator.setValidationListener(this);
        fabPagar = (AppCompatButton) findViewById(R.id.fabPagar);
        etCartao = (EditText) findViewById(R.id.etCartao);
        etNome = (EditText) findViewById(R.id.etNome);
        etMesCartao = (EditText) findViewById(R.id.etMesCartao);
        etAnoCartao = (EditText) findViewById(R.id.etAnoCartao);
        etCvv = (EditText) findViewById(R.id.etCvv);
        totalCompras = (TextView) findViewById(R.id.totalCompras);
        transacaoDao = new TransactDao();

        statusDialog = new ProgressDialog(this);
    }

}
