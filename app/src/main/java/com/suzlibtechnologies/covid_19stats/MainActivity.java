package com.suzlibtechnologies.covid_19stats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
TextView tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvAffectiveCountries;
SimpleArcLoader simpleArcLoader;
ScrollView scrollView;
PieChart pieChart;
SwipeRefreshLayout swipeRefreshLayout;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnection();
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvtcases);
        tvTotalDeaths = findViewById(R.id.tvtotaldeaths);
        tvTodayDeaths = findViewById(R.id.tvtdeaths);
        tvAffectiveCountries = findViewById(R.id.tvafcountries);
        swipeRefreshLayout = findViewById(R.id.swipe);
        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollbarStats);
        pieChart = findViewById(R.id.piechart);

        fetchdata();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }


   

    private void fetchdata() {
        String url = "https://corona.lmao.ninja/v2/all/";
        simpleArcLoader.start();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvAffectiveCountries.setText(jsonObject.getString("affectedCountries"));


                    PieChart pieChart;
                    pieChart = findViewById(R.id.piechart);

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#ffa726")));

                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()),Color.parseColor("#66bb6a")));

                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()),Color.parseColor("#ef5350")));

                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()),Color.parseColor("#29b6f6")));
                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);



                }catch (JSONException e){
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                // Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
               Intent intentt = new Intent(Intent.ACTION_SEND);
               intentt.setType("text/plain");
               intentt.putExtra(Intent.EXTRA_SUBJECT,"Share This App");
               String shareMessage="https://play.google.com/store/app/detail?="+ BuildConfig.APPLICATION_ID+"\n\n";
               intentt.putExtra(Intent.EXTRA_TEXT,shareMessage);
               startActivity(Intent.createChooser(intentt,"share by"));
                break;

            case R.id.privacy:
                Intent intent = new Intent(MainActivity.this,PrivacyPolicy.class);
                startActivity(intent);
                finish();
                break;

            case R.id.dis:
               Dialog dis = new Dialog(MainActivity.this);
               dis.requestWindowFeature(Window.FEATURE_NO_TITLE);
               dis.setContentView(R.layout.disclaimer);
               dis.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dis.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    public void checkConnection(){

        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(null!=activeNetwork){
            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_SHORT).show();
            }
            else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(this, "Data Network Enabled", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure You Want To Exit?")
                .setCancelable(true)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                }).show();


    }



}