package com.example.abdallahmohammed.indextask1.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.abdallahmohammed.indextask1.R;
import com.example.abdallahmohammed.indextask1.model.ClientInformation;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private final String LOGIN_URL = "http://delevery.4rera.com/api/clients/login";
    private final int REQUEST_CODE=1;
    JSONObject clientLoginData;
    ClientInformation client;
    ProgressDialog progressDialog ;


    TextView userIdTextView, userNameTextView, userCountryTextView, userCityTextView, userAddressTextView, userMobileTextView;
    Button getNotificationButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userIdTextView = findViewById(R.id.user_id_TextView);
        userNameTextView = findViewById(R.id.user_name_TextView);
        userAddressTextView = findViewById(R.id.user_address_TextView);
        userCityTextView = findViewById(R.id.user_city_TextView);
        userMobileTextView = findViewById(R.id.user_mobile_TextView);
        userCountryTextView = findViewById(R.id.user_country_TextView);
        getNotificationButton=findViewById(R.id.get_notification_Button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Client Information..");

        getNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Notification.class);
                intent.putExtra("token",client.getToken());
                startActivity(intent);

            }
        });

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        clientLoginData = new JSONObject();
        try {
            clientLoginData.put("mobile", "01124269612");
            clientLoginData.put("password", "123456");
            clientLoginData.put("fbid", "dakamsdkloaaa");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.initialize(getApplicationContext());

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new StethoInterceptor()).build();

        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        AndroidNetworking.post(LOGIN_URL)
                .addJSONObjectBody(clientLoginData)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //Get all client infromation into ClientInformation Object
                            client = new ClientInformation(response.getJSONObject("client").get("id").toString(), response.getJSONObject("client").get("client_name").toString()
                                    , response.getJSONObject("client").get("client_city").toString(), response.getJSONObject("client").get("client_country").toString()
                                    , response.getJSONObject("client").get("mobile").toString(), response.getJSONObject("client").get("client_address").toString(), response.getJSONObject("client").get("token").toString());

                            userIdTextView.setText(client.getId());
                            userNameTextView.setText(client.getName());
                            userCountryTextView.setText(client.getCountry());
                            userCityTextView.setText(client.getCity());
                            userMobileTextView.setText(client.getMobile());
                            userAddressTextView.setText(client.getAddress());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.v("Error", anError.getErrorDetail().toString());
                    }
                });



    }
}
