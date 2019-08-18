package com.example.android_my_favorites;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = findViewById(R.id.tv_hello);
        lvClinicas = findViewById(R.id.lv_clinicas);
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

    class MyAsyncTask extends AsyncTask<URL, Void, List<Clinica>> {

        @Override
        protected List<Clinica> doInBackground(URL... urls) {
            Object json="";
            URL url = urls[0];
            Log.d(TAG, "URL utilizada: " + url.toString());
            try {
                json = NetworkUtil.getResponseFromHttpUrl(url);
                Log.d(TAG, "AsyncTask retornou: " + json);
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
            if (clinicas == null) {
                tvHello.setText(R.string.not_found);
            } else {
                tvHello.setText(null);
                listaClinicas(clinicas);
            }
        }
    }
}
