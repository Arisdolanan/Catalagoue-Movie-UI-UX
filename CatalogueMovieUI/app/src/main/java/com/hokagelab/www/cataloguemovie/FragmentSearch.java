package com.hokagelab.www.cataloguemovie;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hokagelab.www.cataloguemovie.adapter.MovieAdapter;
import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterFragment;
import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterMulti;
import com.hokagelab.www.cataloguemovie.api.ApiClient;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.model.MovieResponse;
import com.hokagelab.www.cataloguemovie.model.Result;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearch extends Fragment implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "007c868395e80dc2e4a833416b24efa5";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mrecyclerView;
    Button btnCari;
    private EditText editText;

    public FragmentSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_search, container, false);


        mrecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(layoutManager);


        editText = view.findViewById(R.id.edt_search);
        btnCari = view.findViewById(R.id.btn_cari);
        btnCari.setOnClickListener(this);


        getAllMovie();

        return view;


    }

    private void getAllMovie() {
        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposMovieList(API_KEY);

        // panggil progressdialog biar enak kayak nunggu jodoh
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(50);
        progressDialog.setMessage("Harap Bersabar XD");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();

                List<Result> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                //lempar data ke adapter
                mAdapter = new MovieAdapterFragment(MovieList);
                mrecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity(), "koneksi error :(", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getSearchMovie(String EXTRAS_MOVIES){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(50);
        progressDialog.setMessage("Harap Bersabar XD");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposSearch(API_KEY, EXTRAS_MOVIES);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                List<Result> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                //lempar data ke adapter
                mAdapter = new MovieAdapterFragment(MovieList);
                mrecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity(), "koneksi error :(", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void onClick(View v) {
        if (v.getId() == R.id.btn_cari){
            String EXTRAS_MOVIES = editText.getText().toString();
            if (EXTRAS_MOVIES.isEmpty()){
                EXTRAS_MOVIES = "tidak ada";
                Log.e(TAG,"Data = "+EXTRAS_MOVIES);
                Toast.makeText(getActivity(), "Ada filmnya gak ? "+EXTRAS_MOVIES, Toast.LENGTH_SHORT).show();
                getSearchMovie(EXTRAS_MOVIES);

            }else {
                Log.e(TAG,"Data = "+EXTRAS_MOVIES);
                Toast.makeText(getActivity(), "Ada filmnya gak ? ada ini, semua film "+EXTRAS_MOVIES, Toast.LENGTH_SHORT).show();
                getSearchMovie(EXTRAS_MOVIES);
            }

        }
    }



}
