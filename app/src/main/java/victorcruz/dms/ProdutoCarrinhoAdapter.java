package victorcruz.dms;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ProdutoCarrinhoAdapter extends BaseAdapter {

    private final ArrayList<Produto> produtos;
    private final Activity act;

    public ProdutoCarrinhoAdapter(ArrayList<Produto> produtos, Activity act){
        this.produtos = produtos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_carrinho, parent, false);

        Produto produto = produtos.get(position);

        TextView titulo = (TextView) view.findViewById(R.id.titulo);
        TextView preco = (TextView) view.findViewById(R.id.preco);
        TextView vendedor = (TextView) view.findViewById(R.id.vendedor);
        ImageView imagem = (ImageView) view.findViewById(R.id.imagem);


        imagem.setImageBitmap(produto.getImagem());
        titulo.setText(produto.getTitulo());
        DecimalFormat decimalFormat = new DecimalFormat("#,#####,00");
        String valor = decimalFormat.format((double)produto.getPreco());
        valor = "R$ " + valor;
        preco.setText(valor);

        String _vendedor = produto.getVendedor();
        _vendedor = "de " + _vendedor;
        vendedor.setText(_vendedor);

        return view;
    }
}