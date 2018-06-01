package com.ashima.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ashima.android.Adapters.FeedAdapter;
import com.ashima.android.Models.FeedModel;
import com.ashima.android.Models.RowDataFeed;
import com.ashima.android.Utils.ApiInterface;
import com.ashima.android.Utils.RetrofitApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private RecyclerView recyclerViewFeeds;
    private FeedAdapter feedAdapter;
    private ArrayList<RowDataFeed> rowDataFeedList;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void initView() {
        // initialize all views by id
        toolbar = findViewById(R.id.toolbar);
        recyclerViewFeeds = findViewById(R.id.recyclerViewFeeds);
        //add a ToolBar to ActionBar
        setSupportActionBar(toolbar);
    }


    private void getFeedFromServer() {
        // not using progress dialog as specification says not block UI while loading json
        //showProgressDialogWithMsg("Please Wait While Receiving Feeds....");
        ApiInterface apiService =
                RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<FeedModel> call = apiService.getFeeds();
        call.enqueue(new Callback<FeedModel>() {
            @Override
            public void onResponse(Call<FeedModel> call, Response<FeedModel> response) {
                Log.d(TAG, "Success Feed GET");
                if (response != null && response.isSuccessful()) {

                    if (rowDataFeedList != null)
                        rowDataFeedList.clear();
                    else
                        rowDataFeedList = new ArrayList<>();

                    setToolbarTitle(response.body().getTitle());
                    rowDataFeedList.addAll(response.body().getRows());
                    setRecyclerViewAdapter();
                    //dismissProgressDialogWithMsg();
                }
            }

            @Override
            public void onFailure(Call<FeedModel> call, Throwable t) {
                //dismissProgressDialogWithMsg();
            }
        });
    }


    private void setRecyclerViewAdapter() {
        if (rowDataFeedList != null) {
            if (feedAdapter == null) {
                recyclerViewFeeds.setLayoutManager(new LinearLayoutManager(this));
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFeeds.getContext(),
                        LinearLayoutManager.VERTICAL);
                recyclerViewFeeds.addItemDecoration(dividerItemDecoration);
                feedAdapter = new FeedAdapter(this, rowDataFeedList);
                recyclerViewFeeds.setAdapter(feedAdapter);
            } else {
                feedAdapter.notifyDataSetChanged();
            }
        }

    }

    private void setToolbarTitle(String title) {
        if (title != null&&toolbar!=null)
            toolbar.setTitle(title);
    }


    public void showProgressDialogWithMsg(String msg) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void dismissProgressDialogWithMsg() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


}
