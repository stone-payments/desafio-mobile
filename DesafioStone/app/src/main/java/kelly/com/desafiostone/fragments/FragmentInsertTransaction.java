package kelly.com.desafiostone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kelly.com.desafiostone.R;

public class FragmentInsertTransaction extends Fragment {


    public FragmentInsertTransaction() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insert_transaction, container, false);
    }

}
