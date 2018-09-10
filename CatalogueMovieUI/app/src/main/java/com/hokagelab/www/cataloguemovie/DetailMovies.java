package com.hokagelab.www.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.util.Objects;

//By Aris Kurniawan

public class DetailMovies extends AppCompatActivity {
    TextView tvTitle, tvRelease, tvOverview;
    ImageView imageBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        tvTitle = findViewById(R.id.tv_title_detail);
        tvRelease = findViewById(R.id.tv_releasedate_detail);
        tvOverview = findViewById(R.id.tv_overview_detail);
        imageBackdrop = findViewById(R.id.imgBackdrop_detail);
        getData();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details Movie");

    }

    private void getData(){

        String imgPath = "http://image.tmdb.org/t/p/w780"+getIntent().getStringExtra("backdrop");
        Glide.with(this)
                .load(imgPath)
                .into(imageBackdrop);
        tvTitle.setText(getIntent().getStringExtra("title"));
        tvRelease.setText(getIntent().getStringExtra("release_date"));
        tvOverview.setText(getIntent().getStringExtra("overview"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
