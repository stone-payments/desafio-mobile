package kelly.com.desafiostone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kelly.com.desafiostone.R;

public class FragmentListTransactions extends Fragment {


    public FragmentListTransactions() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_transactions, container, false);
    }

}
