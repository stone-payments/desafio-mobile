package com.stone.lfernandosantos.storewars.views;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CardDataActivity extends AppCompatActivity implements Runnable{

    private Button btnDadosCard;
    private EditText bandeira;
    private EditText numCard;
    private EditText nome;
    private EditText cardMonth;
    private EditText cardYear;
    private EditText cvv;
    private List<Product> products;
    private Double totalPayment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);

        progressDialog = new ProgressDialog(this);
        setTitle("Dados de Pagamento");
        btnDadosCard = (Button) findViewById(R.id.btnSaveCard);

        numCard = (EditText) findViewById(R.id.edtCard);
        nome = (EditText) findViewById(R.id.edtNomeCard);
        cardMonth = (EditText) findViewById(R.id.edtCardMonth);
        cardYear = (EditText) findViewById(R.id.edtCardYear);
        cvv = (EditText) findViewById(R.id.edtCVVCard);


        btnDadosCard.setText("Concluir");
        btnDadosCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fazer validação dos campos

                Card card = new Card();
                CardDAO dao = new CardDAO(CardDataActivity.this);

                card.bandeira = "VISA";
                card.nome = nome.getText().toString();
                String stringNumCard = numCard.getText().toString();

                String  lastFour = stringNumCard.substring(stringNumCard.length() - 4);
                card.numCard = lastFour;

                card.validade = cardMonth.getText().toString() + "/" + cardYear.getText().toString();
                card.cvv = cvv.getText().toString();

                dao.saveCard(card);
                dao.close();

              paymentConfirm(card);
            }
        });

    }

    private void paymentConfirm(Card card) {

        ProductDAO dao = new ProductDAO(this);

        products = dao.getProductsCarrinho();
        dao.close();

        if (products != null && products.size() > 0) {
            totalPayment = 0.0;

            for (Product p : products) {
                totalPayment = totalPayment + Double.valueOf(p.price);
            }

            totalPayment = totalPayment / 100;

            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_confirm_payment, null);

            ImageView imgBandeira = (ImageView) view.findViewById(R.id.imgBandeiraDialogPaymentConfirm);
            TextView txtTotalPayment = (TextView) view.findViewById(R.id.txtTotalPaymentDialog);
            TextView txtNumCard = (TextView) view.findViewById(R.id.txtCardNumPaymentDialog);
            TextView txtEndEntrega = (TextView) view.findViewById(R.id.txtEndPaymentDialog);

            Glide.with(this).load(R.drawable.ic_bandeira_visa).into(imgBandeira);

            txtTotalPayment.setText("R$ " + String.format("%.2f",totalPayment));
            txtNumCard.setText(" ... " + card.numCard);
            txtEndEntrega.setText("Casa");

            new AlertDialog.Builder(this)
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
        ProductDAO dao = new ProductDAO(this);
        OrderDAO orderDAO = new OrderDAO(this);

        Random random = new Random();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Order order = new Order();

        order.idOrder = random.nextInt(99999);
        order.date = format.format(date);
        order.total = total;
        order.itens = String.valueOf(products.size());

        for (Product p : products){
            p.compra = String.valueOf(order.idOrder);
            p.carrinho = "0";
            dao.saveProduct(p);
        }

        //        Client client = ClientBuilder.newClient();
//        Entity payload = Entity.json("{  'card_number': '1234123412341234',  'value': 7990,  'cvv': 789,  'card_holder_name': 'Luke',  'exp_date': '12/15'}");
//        javax.ws.rs.core.Response response = client.target("http://polls.apiblueprint.org/pay")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .post(payload);
//
//        System.out.println("status: " + response.getStatus());
//        System.out.println("body:" + response.readEntity(String.class));

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

    private void finaliza() {
        finish();
    }
}
