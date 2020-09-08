package com.the43appmart.aniruddha.nfcpay;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyCards extends Fragment {

    private RecyclerView recyclerView;
    private MyCardsAdapter adapter;
    private List<MyCardsData> data_list = null;
    private SharedPreferences savedUser;
    private String Id;
    private SharedPreferences SaveBatchId;
    private String GETBATCHID;
    private ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cards, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();

        savedUser = PreferenceManager.getDefaultSharedPreferences(getContext());
        Id = savedUser.getString("strId", "");
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Please Wait");
        progress.setMessage("Getting batches...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);

          load_data_from_server();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MyCardsAdapter(this, data_list);
        recyclerView.setAdapter(adapter);


        return view;
    }

        private void load_data_from_server() {
        progress.show();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://43appmart.com/NFCPay/GetMyCards.php?id=" + Id)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        MyCardsData data = new MyCardsData(object.getString("UserId"),
                                object.getString("Name"),
                                object.getString("CardNumber"),
                                object.getString("CVV"),
                                object.getString("Expiry"),
                                object.getString("Brand"));
                        data_list.add(data);
                    }


                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                } catch (
                        JSONException e)

                {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                progress.hide();
            }
        };

        task.execute();
    }

}
