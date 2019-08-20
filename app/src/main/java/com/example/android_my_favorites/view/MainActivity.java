package com.example.android_my_favorites.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_my_favorites.MyAdapter;
import com.example.android_my_favorites.R;
import com.example.android_my_favorites.dao.ClinicaDataBase;
import com.example.android_my_favorites.model.Clinica;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import util.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainCAMILA";
    TextView tvHello;
    @SuppressLint("StaticFieldLeak")
    static ListView lvClinicas;
    ProgressBar pbLoading;
    ImageButton ibStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = findViewById(R.id.tv_hello);
        lvClinicas = findViewById(R.id.lv_clinicas);
        pbLoading = findViewById(R.id.pb_loading);
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
                listAllFavoritesClinics();
                break;
            case R.id.menu_newact:
                Intent intent = new Intent(MainActivity.this, ClinicaInfo.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listAllClinics(){
        URL url = NetworkUtil.buildUrlClinicas();
        CallWebAsyncTask task = new CallWebAsyncTask();
        task.execute(url);
    }

    public void listAllFavoritesClinics(){
        GetAllFavoritesAsyncTask task = new GetAllFavoritesAsyncTask(this);
        task.execute();
    }

    public ListView listaClinicas (final List<Clinica> clinica) {
        Log.d(TAG, "entrou.....");
        MyAdapter adapter = new MyAdapter(clinica, this);
        lvClinicas.setAdapter(adapter);

        lvClinicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicou no item...");
                Intent intent = new Intent(MainActivity.this, ClinicaInfo.class);
                intent.putExtra("clinica", (Parcelable) clinica.get(position));
                startActivity(intent);
            }
        });
        Log.d(TAG, "setou listener...");
        return lvClinicas;
    }

    public void mostrarLoading(){
        tvHello.setText(null);
        pbLoading.setVisibility(View.VISIBLE);
        clearList();
    }

    public void clearList(){
        lvClinicas.setAdapter(null);
    }

    public void esconderLoading(){
        tvHello.setText(null);
        pbLoading.setVisibility(View.GONE);
    }

    public static void setFavorite(Integer position){
        Clinica cli = (Clinica) lvClinicas.getAdapter().getItem(position);
        View view = lvClinicas.getChildAt(position);
        if(view != null) {
            TextView tv = view.findViewById(R.id.tv_clinica);
            if (cli.getNome_fantasia().equals(tv.getText().toString())) {
                cli.setFav(true);
                ((ImageButton) view.findViewById(R.id.ib_star)).setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_star_full));
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class CallWebAsyncTask extends AsyncTask<URL, Void, List<Clinica>> {

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
                listaClinicas(clinicas);
            }
        }
    }

    public static class SetFavoriteAsyncTask extends AsyncTask<Clinica, Void, Void> {
        @SuppressLint("StaticFieldLeak")
        Context context;

        public SetFavoriteAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Clinica... clinicas){
            Clinica clinica = clinicas[0];
            if (clinica.getFavorite()){
                ClinicaDataBase.getInstance(context).getDao().insert(clinica);
            } else {
                List<Clinica> cli = ClinicaDataBase.getInstance(context).getDao().getClinica(clinica.getUniq_id());
                for(Clinica c : cli) {
                    ClinicaDataBase.getInstance(context).getDao().delete(c);
                }
            }
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetAllFavoritesAsyncTask extends AsyncTask<Void, Void, List<Clinica>>{
        Context context;

        GetAllFavoritesAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected List<Clinica> doInBackground(Void... voids) {
            return ClinicaDataBase.getInstance(context).getDao().getAllClinicas();
        }

        @Override
        protected void onPostExecute(List<Clinica> clinicas) {
            if (clinicas.size() == 0) {
                clearList();
                tvHello.setText(R.string.without_favorite);
            } else {
                tvHello.setText(null);
                listaClinicas(clinicas);
                Log.d(TAG, "setar listener...");
            }

            super.onPostExecute(clinicas);
        }
    }

    public static class GetClinicAsyncTask extends AsyncTask<String, Void, List<Clinica>>{
        @SuppressLint("StaticFieldLeak")
        Context context;
        Integer position;

        public GetClinicAsyncTask(Context context, Integer position){
            this.context = context;
            this.position = position;
        }

        @Override
        protected List<Clinica> doInBackground(String... strings) {
            return ClinicaDataBase.getInstance(this.context).getDao().getClinica(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Clinica> clinicas) {
            if (clinicas.size() > 0){
                setFavorite(this.position);
            }
            super.onPostExecute(clinicas);
        }
    }

}
