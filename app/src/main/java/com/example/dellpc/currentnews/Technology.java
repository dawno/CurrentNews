package com.example.dellpc.currentnews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Technology extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private TFeedListAdapter listAdapter;
    private ImageView img;
    private int i ;
    TabLayout tabs ;
    private Date date , currentDate;
    private List<TFeedItem> feedItems;
    private JSONObject first,second;
    private String URL_FEED_1 = "https://newsapi.org/v1/articles?source=fortune&sortBy=top&apiKey=0e7fe6582da9471aa0e2a67dab5fb6a0";
    private String URL_FEED_2 = "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=top&apiKey=0e7fe6582da9471aa0e2a67dab5fb6a0";
    public Technology() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedItems = new ArrayList<>();
        feedItems.clear();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_technology, container, false);
        listView = (ListView)view. findViewById(R.id.list);
        img = (ImageView)view.findViewById(R.id.tprofilePic);
        listAdapter = new TFeedListAdapter(Technology.this.getActivity(), feedItems);
        tabs = (TabLayout)view.findViewById(R.id.tabs);
        listView.setAdapter(listAdapter);

        final JsonObjectRequest jsonReqc = new JsonObjectRequest(Request.Method.GET,
                URL_FEED_1, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    first = null;
                    first = response;

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReqc);
        JsonObjectRequest jsonReqd = new JsonObjectRequest(Request.Method.GET,
                URL_FEED_2, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response1) {
                VolleyLog.d(TAG, "Response: " + response1.toString());
                if (response1 != null) {
                    second = null;
                    second =  response1;

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReqd);

        JSONObject combined = new JSONObject();
        try {
            combined.put("BBC", first);
            combined.put("CRIC", second);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        parseJsonFeed(combined);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        RequestQueue requestQueue= Volley.newRequestQueue(Technology.this.getActivity());
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        super.onPause();
    }
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONObject BBC = response.getJSONObject("BBC");
            JSONObject CRIC = response.getJSONObject("CRIC");
            JSONArray BBCa = BBC.getJSONArray("articles");
            JSONArray CRICa = CRIC.getJSONArray("articles");
            for (  i = 0; i < BBCa.length(); i++) {
                JSONObject feedObj = (JSONObject) BBCa.get(i);
                if(feedItems.size()>15){

                    break;
                }
                TFeedItem item = new TFeedItem();
                item.setId(i);
                item.setSource("BBC SPORTS");
                String image = feedObj.isNull("urlToImage") ? null : feedObj
                        .getString("urlToImage");
                item.setImge(image);
                item.setTitle(feedObj.getString("title"));
                item.setDescription(feedObj.getString("description"));
                //  item.setProfilePic(feedObj.getString("profilePic"));
                // img.setImageResource(R.drawable.bbc);
                // item.setTimeStamp(feedObj.getString("publishedAt"));
                String time = feedObj.isNull("publishedAt") ? null : feedObj
                        .getString("publishedAt");
                item.setTimeStamp(time);

                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }
            int k = 0;
            for ( int j=i+1; i < CRICa.length()+i; j++) {
                if(feedItems.size()>15){

                    break;
                }
                JSONObject feedObj = (JSONObject) CRICa.get(k);
                k=k+1;
                TFeedItem item1 = new TFeedItem();
                item1.setId(j);
                item1.setSource("ESPN");
                String image = feedObj.isNull("urlToImage") ? null : feedObj
                        .getString("urlToImage");
                item1.setImge(image);
                item1.setTitle(feedObj.getString("title"));
                item1.setDescription(feedObj.getString("description"));
                //  item.setProfilePic(feedObj.getString("profilePic"));
                // img.setImageResource(R.drawable.bbc);
                // item.setTimeStamp(feedObj.getString("publishedAt"));
                String time = feedObj.isNull("publishedAt") ? null : feedObj
                        .getString("publishedAt");
                item1.setTimeStamp(time);

                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item1.setUrl(feedUrl);

                feedItems.add(item1);
                if(feedItems.size()>15){


                }
            }
            listAdapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

