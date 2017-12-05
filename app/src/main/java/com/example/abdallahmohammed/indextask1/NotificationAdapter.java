package com.example.abdallahmohammed.indextask1;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abdallahmohammed.indextask1.model.NotificationResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Abdallah Mohammed on 12/4/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<NotificationResponse> notificationResponses;
    Activity activity;
    OnLoadMoreListner onLoadMoreListner;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading=false;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public void setOnLoadMoreListner(OnLoadMoreListner mOnLoadMoreListner) {
        this.onLoadMoreListner = mOnLoadMoreListner;
    }



    public NotificationAdapter(RecyclerView notificationRecyclerView, ArrayList<NotificationResponse> notificationResponses, Activity activity) {
        this.notificationResponses = notificationResponses;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) notificationRecyclerView.getLayoutManager();
        notificationRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListner != null) {
                        onLoadMoreListner.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return notificationResponses.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationContentTextView, notificationDateTextView, notificationTypeTextView;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            notificationContentTextView = itemView.findViewById(R.id.notification_content_TextView);
            notificationDateTextView = itemView.findViewById(R.id.notification_date_TextView);
            notificationTypeTextView = itemView.findViewById(R.id.notification_type_TextView);

        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType==VIEW_TYPE_ITEM){

           View view = LayoutInflater.from(activity).inflate(R.layout.notification_item, parent, false);
           return new NotificationViewHolder(view);
       }
       else if(viewType==VIEW_TYPE_LOADING){
           View view = LayoutInflater.from(activity).inflate(R.layout.notification_loading, parent, false);
           return new LoadingViewHolder(view);

       }
       return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NotificationViewHolder){
            NotificationResponse notificationResponse = notificationResponses.get(position);
            NotificationViewHolder notificationViewHolder = (NotificationViewHolder) holder;
            notificationViewHolder.notificationContentTextView.setText(notificationResponse.getContent());
            Date date = new Date(notificationResponse.getDate());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            String dateText = simpleDateFormat.format(date);
            notificationViewHolder.notificationDateTextView.setText(dateText);
            notificationViewHolder.notificationTypeTextView.setText(notificationResponse.getType());

        }
        else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }



    @Override
    public int getItemCount() {
        return notificationResponses == null ? 0 : notificationResponses.size();
    }

    public void setLoaded(){
        isLoading=false;
    }

}
