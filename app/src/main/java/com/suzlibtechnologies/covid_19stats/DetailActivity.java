package com.suzlibtechnologies.covid_19stats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;

public class DetailActivity extends AppCompatActivity {
    private int positionCountry;
    TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+AffectedCountries.countryModelsList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCountry = findViewById(R.id.tvCountry);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvrecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvactive);
        tvTodayCases = findViewById(R.id.tvtodayCases);
        tvTotalDeaths = findViewById(R.id.tvdeaths);
        tvTodayDeaths = findViewById(R.id.tvtodayDeaths);

       tvCountry.setText(AffectedCountries.countryModelsList.get(positionCountry).getCountry());
       tvCases.setText(AffectedCountries.countryModelsList.get(positionCountry).getCases());
       tvRecovered.setText(AffectedCountries.countryModelsList.get(positionCountry).getRecovered());
       tvCritical.setText(AffectedCountries.countryModelsList.get(positionCountry).getCritical());
       tvActive.setText(AffectedCountries.countryModelsList.get(positionCountry).getActive());
       tvTodayCases.setText(AffectedCountries.countryModelsList.get(positionCountry).getTodayCases());
       tvTotalDeaths.setText(AffectedCountries.countryModelsList.get(positionCountry).getDeaths());
       tvTodayDeaths.setText(AffectedCountries.countryModelsList.get(positionCountry).getTodayDeaths());

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}