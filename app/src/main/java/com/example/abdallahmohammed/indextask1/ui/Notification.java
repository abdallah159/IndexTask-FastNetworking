package com.example.abdallahmohammed.indextask1.ui;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.abdallahmohammed.indextask1.NotificationAdapter;
import com.example.abdallahmohammed.indextask1.OnLoadMoreListner;
import com.example.abdallahmohammed.indextask1.R;
import com.example.abdallahmohammed.indextask1.model.NotificationResponse;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class Notification extends AppCompatActivity {

    NotificationResponse notificationResponse;
    ArrayList<NotificationResponse> notificationResponses;
    private String TOKEN;
    private int PAGE_NUMBER = 1;
    private int LIMIT = 10;
    RecyclerView notificationRecyclerView;
    NotificationAdapter notificationAdapter;
    ProgressDialog progressDialog;

    Boolean flage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Bundle bundle = getIntent().getExtras();
        TOKEN = bundle.getString("token");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Notifications..");

        notificationResponses = new ArrayList<>();

        notificationRecyclerView = findViewById(R.id.notifications_RecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notificationRecyclerView.setLayoutManager(linearLayoutManager);


        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.initialize(getApplicationContext());
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new StethoInterceptor()).build();

        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        getData(LIMIT);


    }

    public void getData(int limit) {
        AndroidNetworking.get("http://delevery.4rera.com/api/clients/1/notifications?page=" + PAGE_NUMBER + "&limit=" + limit)
                .addHeaders("Authorization", "Bearer " + TOKEN)
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                            response.getJSONArray("notifications").length()
                    int end = notificationResponses.size() + 5 ;
                    for (int i = notificationResponses.size(); i < end; i++) {
                        notificationResponse = new NotificationResponse(((Integer) response.getJSONArray("notifications").getJSONObject(i).get("notification_id"))
                                , ((String) response.getJSONArray("notifications").getJSONObject(i).get("notification_content"))
                                , response.getJSONArray("notifications").getJSONObject(i).getLong("notification_date")
                                , ((String) response.getJSONArray("notifications").getJSONObject(i).get("notification_type"))
                                , ((Integer) response.getJSONArray("notifications").getJSONObject(i).get("order_id"))
                                , ((Integer) response.getJSONArray("notifications").getJSONObject(i).get("employee_id")));

                        notificationResponses.add(notificationResponse);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!flage) {
                    notificationAdapter = new NotificationAdapter(notificationRecyclerView, notificationResponses, Notification.this);
                    notificationRecyclerView.setAdapter(notificationAdapter);

                    flage = true;
                }
                progressDialog.dismiss();
                onLoadMore();

            }

            @Override
            public void onError(ANError anError) {
                Log.d("Error", anError.toString());
            }
        });
    }

    public void onLoadMore() {
        notificationAdapter.setOnLoadMoreListner(new OnLoadMoreListner() {
            @Override
            public void onLoadMore() {
                if (notificationResponses.size() <= 20) {
                    notificationResponses.add(null);
                    notificationAdapter.notifyItemInserted(notificationResponses.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            notificationResponses.remove(notificationResponses.size() - 1);
                            int index = notificationResponses.size()+5;
                            getData(index);
                            notificationAdapter.notifyDataSetChanged();
                            notificationAdapter.setLoaded();


                        }
                    }, 3000);
                } else {
                    Toast.makeText(Notification.this, "All Notifications Loaded.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
