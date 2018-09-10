package com.hokagelab.www.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hokagelab.www.cataloguemovie.DetailMovies;
import com.hokagelab.www.cataloguemovie.R;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.model.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//By Aris Kurniawan

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private static final String TAG = "MovieAdapter";
    private List<Result> mMovieList;

    public MovieAdapter(List <Result> MovieList){
        this.mMovieList = MovieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final int pos = position;
        final Result movies = mMovieList.get(position);
        String imgPath = "http://image.tmdb.org/t/p/w185/"+movies.getPoster_path();

        if (movies.getPoster_path() == null) {
            holder.imagePoster.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }else{
            Glide.with(holder.itemView.getContext())
                    .load(imgPath) //url
                    .into(holder.imagePoster);
        }

        holder.tv_title.setText(mMovieList.get(position).getTitle());
        holder.tv_overview.setText(mMovieList.get(position).getOverview());
        holder.tv_releasedate.setText(mMovieList.get(position).getRelease_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "posisi dimana nih = " + pos);
                Result data = mMovieList.get(pos);
                Intent i = new Intent(holder.itemView.getContext(), DetailMovies.class);
                i.putExtra("title", data.getTitle());
                i.putExtra("overview", data.getOverview());
                i.putExtra("release_date", data.getRelease_date());
                i.putExtra("backdrop", data.getBackdrop_path());
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_overview, tv_releasedate, tv_voteAverage;
        ImageView imagePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            tv_releasedate = itemView.findViewById(R.id.tv_releasedate);
            imagePoster = itemView.findViewById(R.id.imgPoster);
        }
    }
}
