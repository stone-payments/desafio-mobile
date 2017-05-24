package com.stone.lfernandosantos.storewars.views;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.controlers.ListComprasAdapter;
import com.stone.lfernandosantos.storewars.models.Card;
import com.stone.lfernandosantos.storewars.models.CardDAO;
import com.stone.lfernandosantos.storewars.models.Order;
import com.stone.lfernandosantos.storewars.models.OrderDAO;
import com.stone.lfernandosantos.storewars.models.Product;
import com.stone.lfernandosantos.storewars.models.ProductDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CartActivity extends AppCompatActivity implements Runnable{

    private ListView listCompras;
    private Button btnComprar;
    private List<Product> products;

    private TextView cardSaved;
    private RelativeLayout layoutCardSaved;

    private ProgressDialog progressDialog;

    private Double totalPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setTitle("Carrinho");
        progressDialog = new ProgressDialog(this);

        findViews();

        layoutCardSaved.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                startActivity(new Intent(CartActivity.this, ListCardsActivity.class));
                return false;
            }
        });


        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            CardDAO daoCard = new CardDAO(CartActivity.this);
            List<Card> cards = daoCard.getCards();

            if (cards != null && cards.size() > 0){

                paymentConfirm(cards);
                //startActivity(new Intent(CartActivity.this, CardDataActivity.class));

            }else {
                finish();
                startActivity(new Intent(CartActivity.this, CardDataActivity.class));
            }

            }
        });

        runCartList();
        runCardList();
    }


    private void findViews() {
        btnComprar = (Button) findViewById(R.id.buyBtn);
        listCompras = (ListView) findViewById(R.id.listaCompras);
        cardSaved = (TextView) findViewById(R.id.txtNumCardSaved);
        layoutCardSaved = (RelativeLayout) findViewById(R.id.layoutCardSaved);
    }

    private void paymentConfirm(List<Card> cards) {
        if (products != null && products.size() > 0) {
            totalPayment = 0.0;

            for (Product p : products) {
                totalPayment = totalPayment + Double.valueOf(p.price);
            }

            totalPayment = totalPayment / 100;

            LayoutInflater inflater = CartActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_confirm_payment, null);

            ImageView imgBandeira = (ImageView) view.findViewById(R.id.imgBandeiraDialogPaymentConfirm);
            TextView txtTotalPayment = (TextView) view.findViewById(R.id.txtTotalPaymentDialog);
            TextView txtNumCard = (TextView) view.findViewById(R.id.txtCardNumPaymentDialog);
            TextView txtEndEntrega = (TextView) view.findViewById(R.id.txtEndPaymentDialog);

            Glide.with(CartActivity.this).load(R.drawable.ic_bandeira_visa).into(imgBandeira);

            txtTotalPayment.setText("R$ " + String.format("%.2f",totalPayment));
            txtNumCard.setText(" ... " + cards.get(cards.size()-1).numCard);
            txtEndEntrega.setText("Casa");

            new AlertDialog.Builder(CartActivity.this)
                    .setView(view)
                    .setTitle("CONCLUIR A COMPRA?")
                    .setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.setMessage("Aguarde...");
                            progressDialog.show();
                            doPayment(totalPayment);
                        }
                    })
                    .setNegativeButton("cancelar", null)
                    .setCancelable(false)
                    .create()
                    .show();
        }
    }

    private void doPayment(Double total) {
        ProductDAO dao = new ProductDAO(CartActivity.this);
        OrderDAO orderDAO = new OrderDAO(CartActivity.this);

        Random random = new Random();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Order order = new Order();

        order.idOrder = random.nextInt(99999);
        order.date = format.format(date);
        order.total = total;
        order.itens = String.valueOf(products.size());

        //        Client client = ClientBuilder.newClient();
//        Entity payload = Entity.json("{  'card_number': '1234123412341234',  'value': 7990,  'cvv': 789,  'card_holder_name': 'Luke',  'exp_date': '12/15'}");
//        javax.ws.rs.core.Response response = client.target("http://polls.apiblueprint.org/pay")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .post(payload);
//
//        System.out.println("status: " + response.getStatus());
//        //System.out.println("headers: " + response.getHeaders());
//        System.out.println("body:" + response.readEntity(String.class));

        for (Product p : products){
            p.compra = String.valueOf(order.idOrder);
            p.carrinho = "0";
            dao.saveProduct(p);
        }

        orderDAO.saveOrder(order);
        orderDAO.close();

        List<Product> listDelet = dao.getProductsCarrinho();
        for (Product p : listDelet){
            dao.deletar(p);
        }
        dao.close();

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);



    }



    @Override
    protected void onResume() {
        super.onResume();

        progressDialog.setMessage("Aguarde...");

        runCartList();
        runCardList();

        progressDialog.dismiss();
    }

    private void finaliza() {

        finish();
    }
    private void runCartList() {

        ProductDAO dao = new ProductDAO(this);
        products = dao.getProductsCarrinho();

        if (products != null && products.size()>0) {
            ListComprasAdapter adapter = new ListComprasAdapter(products, this);
            listCompras.setAdapter(adapter);
        }
        dao.close();
    }

    private void runCardList() {
        CardDAO daoCard = new CardDAO(this);
        List<Card> cards = daoCard.getCards();

        if (cards != null && cards.size() > 0){
            cardSaved.setText("..." + cards.get(cards.size()-1).numCard);
        }else {
            layoutCardSaved.setVisibility(View.INVISIBLE);
        }
        daoCard.close();
    }

    @Override
    public void run() {
        progressDialog.dismiss();

        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Pedido Realizado!")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finaliza();
                    }
                })
                .create().show();

    }
}
