package br.com.valdir.desafiolojastarwars;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import br.com.valdir.desafiolojastarwars.data.ProdutosContract;

import static br.com.valdir.desafiolojastarwars.MainActivity.mCarrinho;

public class ProdutoDetalheFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    Carrinho carrinho = mCarrinho;

    ItemProduto itemProduto;

    private Uri filmeUri;

    private TextView titleView;

    private TextView priceView;

    private TextView zipcodeView;

    private TextView sellerView;

    private ImageView thumbnailHdView;

    private TextView dataView;

    private TextView totalValorProdutoCarrinho;

    private Button btnAdicionarCarrinho;

    private static final int PRODUTO_DETALHE_LOADER = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            filmeUri = (Uri) getArguments().getParcelable(MainActivity.PRODUTO_DETALHE_URI);
        }

        getLoaderManager().initLoader(PRODUTO_DETALHE_LOADER, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produto_detalhe, container,false);

        titleView = view.findViewById(R.id.item_title);

        priceView = view.findViewById(R.id.item_price);

        zipcodeView = view.findViewById(R.id.item_zipcode);

        sellerView = view.findViewById(R.id.item_seller);

        thumbnailHdView = view.findViewById(R.id.item_thumbnailhd);

        dataView = view.findViewById(R.id.item_data);

        totalValorProdutoCarrinho = view.findViewById(R.id.txt_total_preco);

        btnAdicionarCarrinho = view.findViewById(R.id.btn_adiciona_carrinho);

        btnAdicionarCarrinho.setOnClickListener(onClickAdicionarCarrinho());

        return view;
    }

    private View.OnClickListener onClickAdicionarCarrinho() {
        return new Button.OnClickListener(){
            public void onClick(View v) {
               mCarrinho.addToCart(itemProduto);
               totalValorProdutoCarrinho.setText("Valor do carrinho = R$ " + String.format("%.2f", mCarrinho.getValue()));
            }
        };
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ProdutosContract.ProdutoEntry._ID,
                ProdutosContract.ProdutoEntry.COLUMN_TITLE,
                ProdutosContract.ProdutoEntry.COLUMN_PRICE,
                ProdutosContract.ProdutoEntry.COLUMN_ZIPCODE,
                ProdutosContract.ProdutoEntry.COLUMN_SELLER,
                ProdutosContract.ProdutoEntry.COLUMN_THUMBNAILHD_PATH,
                ProdutosContract.ProdutoEntry.COLUMN_DATA
        };

        return new CursorLoader(getContext(), filmeUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry._ID);
            int titleIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_TITLE);
            int priceIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_PRICE);
            int zipcodeIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_ZIPCODE);
            int sellerIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_SELLER);
            int thumbnailHdIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_THUMBNAILHD_PATH);
            int dataIndex = cursor.getColumnIndex(ProdutosContract.ProdutoEntry.COLUMN_DATA);

            String idProduto = cursor.getString(idIndex);
            String title = cursor.getString(titleIndex);
            String price = cursor.getString(priceIndex);
            String zipcode = cursor.getString(zipcodeIndex);
            String seller = cursor.getString(sellerIndex);
            String thumbnailHd = cursor.getString(thumbnailHdIndex);
            String data = cursor.getString(dataIndex);

            itemProduto = new ItemProduto(Long.valueOf(idProduto), title,
                    Double.valueOf(price),
                    zipcode,
                    seller,
                    thumbnailHd,
                    data);

            titleView.setText(title);
            priceView.setText("R$ " + String.format("%.2f", Double.valueOf(price)));
            zipcodeView.setText(zipcode);
            sellerView.setText(seller);

            //new DownloadImageTask(thumbnailHdView).execute(thumbnailHd);

            Picasso.with(getContext()).load(thumbnailHd).into(thumbnailHdView);

//            if (thumbnailHdView != null) {
//                new DownloadImageTask(thumbnailHdView).execute(thumbnailHd);
//            }

            dataView.setText(data);
            totalValorProdutoCarrinho.setText("Valor do carrinho = R$ " + String.format("%.2f", mCarrinho.getValue()));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
