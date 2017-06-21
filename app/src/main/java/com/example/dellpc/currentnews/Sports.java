package com.example.dellpc.currentnews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dellpc.currentnews.R;

public class Sports extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private ImageView img;
    private  Date date , currentDate;
    private List<FeedItem> feedItems;
    private String URL_FEED = "https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=0e7fe6582da9471aa0e2a67dab5fb6a0";
    public Sports() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedItems = new ArrayList<FeedItem>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_sports, container, false);
        listView = (ListView)view. findViewById(R.id.list);
       img = (ImageView)view.findViewById(R.id.profilePic);
        listAdapter = new FeedListAdapter(Sports.this.getActivity(), feedItems);
        listView.setAdapter(listAdapter);
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
return view;
    }
  /*  String convertDate(String inputDate) {

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        form.setTimeZone(TimeZone.getTimeZone("GMT"));

        try
        {
            date = form.parse(inputDate);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String newDateStr = postFormater.format(date);
        try
        {
            currentDate = form.parse(newDateStr);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        long diffInMs = currentDate.getTime() - date.getTime();
        String s = String.valueOf(diffInMs);
       //  TimeUnit.DAYS.convert(currentDate.getTime() - date.getTime(), TimeUnit.SECONDS);
      //  String s = String.valueOf(TimeUnit.MILLISECONDS.convert(date - currentDate, TimeUnit.MILLISECONDS));
        return s;
    }*/
  String convertDate(String inputDate) {

      SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy hh:mm");
      form.setTimeZone(TimeZone.getTimeZone("GMT"));
      Date date = null;
      try
      {
          date = form.parse(inputDate);
      }
      catch (ParseException e)
      {

          e.printStackTrace();
      }


      String newDateStr = form.format(date);
     return newDateStr;
  }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("articles");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
               item.setId(i);
                item.setSource("BBC");

                // Image might be null sometimes
                String image = feedObj.isNull("urlToImage") ? null : feedObj
                        .getString("urlToImage");
                item.setImge(image);
                item.setTitle(feedObj.getString("title"));
                item.setDescription(feedObj.getString("description"));
              //  item.setProfilePic(feedObj.getString("profilePic"));
               // img.setImageResource(R.drawable.bbc);
                item.setTimeStamp(feedObj.getString("publishedAt"));
                 // item.setTimeStamp("1403375851930");
                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }}
