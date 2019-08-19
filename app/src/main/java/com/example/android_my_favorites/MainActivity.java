package com.example.android_my_favorites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android_my_favorites.dao.ClinicaDataBase;
import com.example.android_my_favorites.model.Clinica;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import util.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView tvHello;
    ListView lvClinicas;
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = findViewById(R.id.tv_hello);
        lvClinicas = findViewById(R.id.lv_clinicas);
        pbLoading = findViewById(R.id.pb_loading);
        //new BancoAsyncTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_all:
                listAllClinics();
                break;
            case R.id.menu_favorites:
                //list favorites
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listAllClinics(){
        URL url = NetworkUtil.buildUrlClinicas();
        MyAsyncTask task = new MyAsyncTask();
        task.execute(url);
    }

    public ListView listaClinicas (List<Clinica> clinica) {
        MyAdapter adapter = new MyAdapter(clinica, this);
        lvClinicas.setAdapter(adapter);
        return lvClinicas;
    }

    public void mostrarLoading(){
        tvHello.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
    }

    public void esconderLoading(){
        tvHello.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
    }

    public void setFavorite(Clinica clinica){

    }

    class MyAsyncTask extends AsyncTask<URL, Void, List<Clinica>> {

        @Override
        protected void onPreExecute() {
            mostrarLoading();
            super.onPreExecute();
        }

        @Override
        protected List<Clinica> doInBackground(URL... urls) {
            Object json="";
            URL url = urls[0];
            try {
                json = NetworkUtil.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TypeToken<List<Clinica>> token = new TypeToken<List<Clinica>>() {};

            if (json==null) {
                throw new AssertionError("Object cannot be null");
            }
            return new Gson().fromJson(json.toString(), token.getType());
        }

        @Override
        protected void onPostExecute(List<Clinica> clinicas){
            esconderLoading();
            if (clinicas == null) {
                tvHello.setText(R.string.not_found);
            } else {
                tvHello.setText(null);
                listaClinicas(clinicas);
            }
        }
    }
/*
    class BancoAsyncTask extends AsyncTask<Void, Void, Void>{
        Context context;

        BancoAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids){
            Clinica clinica = new Clinica();
            clinica.setNome_fantasia("Bemol");
            clinica.setRazao_social("Benchimol Ltda.");

            ClinicaDataBase.getInstance(context).getDao().insert(clinica);
            List<Clinica> clinicas = ClinicaDataBase.getInstance(context).getDao().getAllClinicas();
            for(Clinica c : clinicas){
                Log.d(TAG, "-->"+ c.toString());
            }

            return null;
        }
    }*/

    static class SetFavoriteAsyncTask extends AsyncTask<Clinica, Void, Boolean> {
        @SuppressLint("StaticFieldLeak")
        Context context;

        SetFavoriteAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Clinica... clinicas){
            Clinica clinica = clinicas[0];
            if (clinica.getFavorite()){
                ClinicaDataBase.getInstance(context).getDao().insert(clinica);
            } else {
                List<Clinica> cli = ClinicaDataBase.getInstance(context).getDao().getClinica(clinica.getUniq_id());
                for(Clinica c : cli) {
                    ClinicaDataBase.getInstance(context).getDao().delete(c);
                }
            }

            List<Clinica> clinis = ClinicaDataBase.getInstance(context).getDao().getAllClinicas();
            for(Clinica c : clinis){
                Log.d(TAG, "-->"+ c.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);
        }
    }

}
