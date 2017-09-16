package victorcruz.dms;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ScrollView lojaScrollView;
    private ScrollView carrinhoScrollView;
    private ScrollView transacoesScrollView;
    private ExpandableHeightListView lojaListView;
    private ExpandableHeightListView carrinhoListView;
    private ExpandableHeightListView transacoesListView;
    private LinearLayout finalizarCompraLinearLayout;
    private TextView valorTotalTextView;
    private TextView tituloToolbarTextView;
    private TextView valorFinalTextView;

    private DownloadJSON downloadJSON;

    private ArrayList<Produto> produtosLoja;
    private ProdutoLojaAdapter produtoLojaAdapter;

    private ArrayList<Produto> produtosCarrinho;
    private ProdutoCarrinhoAdapter produtoCarrinhoAdapter;

    private PagamentoDialogFragment pagamentoDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        lojaScrollView = (ScrollView) findViewById(R.id.lojaScrollView);
        carrinhoScrollView = (ScrollView) findViewById(R.id.carrinhoScrollView);;
        transacoesScrollView = (ScrollView) findViewById(R.id.transacoesScrollView);;
        lojaListView = (ExpandableHeightListView) findViewById(R.id.lojaListView);
        carrinhoListView = (ExpandableHeightListView) findViewById(R.id.carrinhoListView);
        transacoesListView = (ExpandableHeightListView) findViewById(R.id.transacoesListView);
        finalizarCompraLinearLayout = (LinearLayout) findViewById(R.id.finalizarCompraLinearLayout);
        valorTotalTextView = (TextView) findViewById(R.id.valorTotalTextView);
        tituloToolbarTextView = (TextView) findViewById(R.id.tituloToolbarTextView);
        valorFinalTextView = (TextView) findViewById(R.id.valorFinalTextView);

        produtosLoja = new ArrayList<>();
        produtosCarrinho = new ArrayList<>();

        downloadJSON = new DownloadJSON();
        downloadJSON.execute("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json");
        show();

        pagamentoDialogFragment = new PagamentoDialogFragment();
    }

    public void mostrarDialogPagamento(View view){
        //DialogFragment dialog = new DialogFragment();
        //dialog.show( , "DialogFragment");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        builder.setMessage(R.string.pagamento)
                .setView(inflater.inflate(R.layout.input_cartao_dialog, null))
                .setPositiveButton(R.string.confirmar_pagamento, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancelar_pagamento, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        builder.show();
    }

    public void atualizarValorTotal(){

        int valorTotal = 0;

        for (int i = 0; i < produtosCarrinho.size(); i++){
             valorTotal += produtosCarrinho.get(i).getPreco();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,#####,00");
        String valor = decimalFormat.format((double)valorTotal);
        valor = "R$ " + valor;
        valorTotalTextView.setText(valor);


    }

    public void show(){
        produtoLojaAdapter = new ProdutoLojaAdapter(produtosLoja, this);
        lojaListView.setAdapter(produtoLojaAdapter);
        produtoCarrinhoAdapter = new ProdutoCarrinhoAdapter(produtosCarrinho, this);
        carrinhoListView.setAdapter(produtoCarrinhoAdapter);
        atualizarValorTotal();
    }

    public void removerDoCarrinho(View view){
        String titulo = "";
        int preco = -1;
        String vendedor = "";

        // registra o titulo, preco e vendedor do produto nos campos acima
        TextView textView = null;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        for (int itemPos = 0; itemPos < viewGroup.getChildCount(); itemPos++){
            View aux = viewGroup.getChildAt(itemPos);
            if (aux.getId() == R.id.titulo){
                textView = (TextView) aux;
                titulo = textView.getText().toString();
            } else
            if (aux.getId() == R.id.preco){
                textView = (TextView) aux;
                preco = Integer.parseInt(textView.getText().toString().substring(3).replace(".",""));
            } else
            if (aux.getId() == R.id.vendedor){
                textView = (TextView) aux;
                vendedor = textView.getText().toString();
            }
        }

        for (int i = 0; i < produtosCarrinho.size(); i++){
            if (produtosCarrinho.get(i).getTitulo().equals(titulo) &&
                    produtosCarrinho.get(i).getPreco() == preco &&
                    produtosCarrinho.get(i).getVendedor().equals(vendedor.substring(3))){

                System.out.println("Removido: " + produtosLoja.get(i).getTitulo());
                produtosCarrinho.remove(i);
                break;
            }
        }

        show();
    }

    public void adicionarAoCarrinho(View view){

        String titulo = "";
        int preco = -1;
        String vendedor = "";

        // registra o titulo, preco e vendedor do produto nos campos acima
        TextView textView = null;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        for (int itemPos = 0; itemPos < viewGroup.getChildCount(); itemPos++){
            View aux = viewGroup.getChildAt(itemPos);
            if (aux.getId() == R.id.titulo){
                textView = (TextView) aux;
                titulo = textView.getText().toString();
            } else
                if (aux.getId() == R.id.preco){
                    textView = (TextView) aux;
                    preco = Integer.parseInt(textView.getText().toString().substring(3).replace(".",""));
                } else
                if (aux.getId() == R.id.vendedor){
                    textView = (TextView) aux;
                    vendedor = textView.getText().toString();
                }
        }

        for (int i = 0; i < produtosLoja.size(); i++){
            if (produtosLoja.get(i).getTitulo().equals(titulo) &&
                    produtosLoja.get(i).getPreco() == preco &&
                    produtosLoja.get(i).getVendedor().equals(vendedor.substring(3))){

                produtosCarrinho.add(new Produto(produtosLoja.get(i)));
                System.out.println(produtosLoja.get(i).getTitulo());

                break;
            }
        }

        System.out.println("ADD TO CARRINHO!!!!");
        show();

    }

    public class DownloadJSON extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream = null;

            // Vai receber byte streams e decodificar em char
            InputStreamReader inputStreamReader;

            String result = "";

            try {

                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = inputStreamReader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            System.out.print("Resultado: " + result);

            try {
                JSONArray jsonArray = new JSONArray(result);

                System.out.println(produtosLoja.size());

                for (int i = 0; i < jsonArray.length(); i++) {
                    // separa o json em partes por item_loja
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // baixa a imagem de cada item_loja
                    MainActivity.DownloadImage downloadImage = new MainActivity.DownloadImage(i);
                    downloadImage.execute(jsonObject.getString("thumbnailHd"));

                    // cria cada item_loja
                    produtosLoja.add(new Produto(
                            jsonObject.getString("title"),
                            jsonObject.getInt("price"),
                            jsonObject.getString("zipcode"),
                            jsonObject.getString("seller"),
                            null,
                            jsonObject.getString("date")));


                    System.out.println("Produto eh: " + i + " " + produtosLoja.get(i).getTitulo());
                }

                System.out.println(produtosLoja.size());

                show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{

        int index;

        public DownloadImage(int index){
            this.index = index;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            try{
                URL url_value = new URL(params[0]);
                Bitmap image = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
                return image;
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);

            produtosLoja.get(index).setImagem(image);
            show();

            System.out.println("Download de imagem com sucesso!");

        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tituloToolbarTextView.setText("Loja");
                    lojaScrollView.setVisibility(View.VISIBLE);
                    carrinhoScrollView.setVisibility(View.INVISIBLE);
                    transacoesScrollView.setVisibility(View.INVISIBLE);
                    finalizarCompraLinearLayout.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    tituloToolbarTextView.setText("Carrinho");
                    lojaScrollView.setVisibility(View.INVISIBLE);
                    carrinhoScrollView.setVisibility(View.VISIBLE);
                    transacoesScrollView.setVisibility(View.INVISIBLE);
                    finalizarCompraLinearLayout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    tituloToolbarTextView.setText("Transações");
                    lojaScrollView.setVisibility(View.INVISIBLE);
                    carrinhoScrollView.setVisibility(View.INVISIBLE);
                    transacoesScrollView.setVisibility(View.VISIBLE);
                    finalizarCompraLinearLayout.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }

    };

}
