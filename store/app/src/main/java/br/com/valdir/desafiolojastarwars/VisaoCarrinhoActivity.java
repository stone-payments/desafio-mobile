package br.com.valdir.desafiolojastarwars;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class VisaoCarrinhoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visao_carrinho);

        Carrinho carrinho = MainActivity.mCarrinho;

        ItemProduto itemProduto;

        LinearLayout carrinhoLayout = findViewById(R.id.carrinho);

        Set<Long> idItemProdutos = carrinho.getIdItemProdutos();

        Iterator iterator = idItemProdutos.iterator();
        while(iterator.hasNext())
        {
            Long idItemProduto = (Long) iterator.next();

            // logic
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView nome = new TextView(this);
            TextView quantidade = new TextView(this);
            TextView precoTotal = new TextView(this);

            itemProduto = carrinho.mProduto.get(idItemProduto);

            if (carrinho.getQuantidade(itemProduto) > 0 ) {

                nome.setText(itemProduto.getTitle());
                quantidade.setText(Integer.toString(carrinho.getQuantidade(itemProduto)));

                precoTotal.setText("R$ " + String.format("%.2f", Double.valueOf(itemProduto.getPrice()*carrinho.getQuantidade(itemProduto))));

                linearLayout.addView(nome);
                linearLayout.addView(quantidade);
                linearLayout.addView(precoTotal);

                // display
                nome.setTextSize(12);
                quantidade.setTextSize(12);
                precoTotal.setTextSize(12);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        200, Gravity.CENTER);
                layoutParams.setMargins(20, 50, 20, 50);
                linearLayout.setLayoutParams(layoutParams);

                nome.setLayoutParams(new TableLayout.LayoutParams(0,
                        ActionBar.LayoutParams.WRAP_CONTENT, 1));

                quantidade.setLayoutParams(new TableLayout.LayoutParams(0,
                        ActionBar.LayoutParams.WRAP_CONTENT, 1));

                nome.setGravity(Gravity.CENTER);
                quantidade.setGravity(Gravity.CENTER);

                carrinhoLayout.addView(linearLayout);
            }
        }
    }
}
