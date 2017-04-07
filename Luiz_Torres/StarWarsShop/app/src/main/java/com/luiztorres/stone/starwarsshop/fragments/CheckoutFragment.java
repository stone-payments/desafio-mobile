package com.luiztorres.stone.starwarsshop.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.luiztorres.stone.starwarsshop.R;
import com.luiztorres.stone.starwarsshop.extras.CartManager;
import com.luiztorres.stone.starwarsshop.extras.Connection;
import com.luiztorres.stone.starwarsshop.extras.Order;
import com.luiztorres.stone.starwarsshop.extras.ScreenManager;
import com.luiztorres.stone.starwarsshop.extras.StorageManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Dindal on 03/01/2017.
 */

public class CheckoutFragment extends Fragment implements View.OnFocusChangeListener {
    HashMap<String, EditText> et_all_list = new HashMap<>();
    RelativeLayout im_btn_check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_checkout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView totalCount = (TextView) getActivity().findViewById(R.id.tv_checkout_totalPrice);
        totalCount.setText("Total R$:"+ CartManager.getInstance(getActivity()).getTotalPrice());

        et_all_list.put("cardNumber", (EditText) getActivity().findViewById(R.id.et_checkout_credit_card_number));
        et_all_list.put("cvv", (EditText) getActivity().findViewById(R.id.et_checkout_credit_card_cvv));
        et_all_list.put("name", (EditText) getActivity().findViewById(R.id.et_checkout_credit_card_name));
        et_all_list.put("exp_date_m", (EditText) getActivity().findViewById(R.id.et_checkout_credit_card_exp_date_m));
        et_all_list.put("exp_date_a", (EditText) getActivity().findViewById(R.id.et_checkout_credit_card_exp_date_a));

        for (Object key : et_all_list.keySet()) {
            TextView value = et_all_list.get(key);
            Drawable drawable = value.getBackground();
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
            if(Build.VERSION.SDK_INT > 16) {
                value.setBackground(drawable);
            }else{
                value.setBackgroundDrawable(drawable);
            }
            value.setOnFocusChangeListener(this);
        }

        im_btn_check = (RelativeLayout) getActivity().findViewById(R.id.btn_checkout_confirm) ;
        im_btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeConfirmationRequest();
            }
        });

        ((EditText)et_all_list.get("name")).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER  && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if ( ((EditText)v).getLineCount() > 0 ) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Drawable drawable = v.getBackground();

        if(hasFocus) {
            drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        else {
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        }

        if(Build.VERSION.SDK_INT > 16)
            v.setBackground(drawable);
        else
            v.setBackgroundDrawable(drawable);
    }

    private String formatDate()
    {
        Locale brasil = new Locale("pt", "BR");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", brasil);
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();

        return dateFormat.format(data);
    }

    private String lastLetters(String str, int n)
    {
        return str.substring(str.length() - n);
    }

    private void MakeConfirmationRequest()
    {
        String url = "http://private-91bd72-ecommercestonesimulator.apiary-mock.com/credit-card";
        String tag_json_obj = "json_obj_req";
        final JSONObject params = new JSONObject();

        if(et_all_list.get("cardNumber").getText().toString().length() > 16 ||
                et_all_list.get("cardNumber").getText().toString().length() < 13) {
            Toast.makeText(getActivity(), R.string.error_card_length, Toast.LENGTH_LONG).show();
            return;
        }

        for (Object key : et_all_list.keySet()) {
            TextView value = et_all_list.get(key);
            if(value.getText().length() <= 0)
            {
                Toast.makeText(getActivity(), R.string.error_blank, Toast.LENGTH_LONG).show();
                return;
            }
        }

        try {
            params.put("card_number", et_all_list.get("cardNumber").getText().toString());
            params.put("value", CartManager.getInstance(getActivity()).getTotalPrice());
            params.put("cvv", Integer.parseInt(et_all_list.get("cvv").getText().toString()));
            params.put("card_holder_name", et_all_list.get("name").getText().toString());
            params.put("exp_date", et_all_list.get("exp_date_m").getText().toString()+"/"+et_all_list.get("exp_date_m").getText().toString());
        }
        catch (JSONException e)
        {
            Toast.makeText(getActivity(), R.string.error_put, Toast.LENGTH_LONG).show();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,url,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response != null) {
                            try {
                                if(response.get("sucess").equals("true"))
                                {
                                    Toast.makeText(getActivity(), "Order complete", Toast.LENGTH_SHORT).show();

                                    CartManager.getInstance(getActivity()).Clear();
                                    Order order = new Order(((Double) params.get("value")), formatDate(),
                                            lastLetters(params.get(("card_number")).toString(), 4), params.get("card_holder_name").toString());
                                    StorageManager.getInstance(getActivity()).addOrder(order);
                                    ScreenManager.getInstance(getActivity()).Replace(new ShopFragment());
                                }
                            }
                            catch (JSONException e)
                            {
                                Toast.makeText(getActivity(), "Pa Checkout: "+getResources().getText(R.string.error_response), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), R.string.error_connection, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };;

        Connection.getInstance(getActivity()).addToRequestQueue(req, tag_json_obj);
    }
}
