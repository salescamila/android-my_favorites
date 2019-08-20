package com.example.android_my_favorites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_my_favorites.model.Clinica;
import com.example.android_my_favorites.view.ClinicaInfo;
import com.example.android_my_favorites.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import util.NetworkUtil;


public class MyAdapter extends BaseAdapter {

    private List<Clinica> listClinicas;
    private Activity activity;
    final private String TAG = "MyAdapter";

    public MyAdapter(List<Clinica> list, Activity act){
        this.listClinicas = list;
        this.activity = act;
    }

    public void clearList(){
        this.listClinicas.clear();
    }

    @Override
    public int getCount() {
        return listClinicas.size();
    }

    @Override
    public Object getItem(int position) {
        return listClinicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = activity.getLayoutInflater().inflate(R.layout.clinica_layout, parent, false);

        // Recuperando a clinica e setando o nome fantasia no layout
        final Clinica clinica = (Clinica) getItem(position);
        TextView tvNomeFantasia = view.findViewById(R.id.tv_clinica);
        tvNomeFantasia.setText(clinica.getNome_fantasia());

        // Imagem logo da clinica
        ImageView imageView = view.findViewById(R.id.iv_clinica);
        URL urlPhoto = NetworkUtil.buildUrlPhoto(clinica.getUniq_id(), clinica.getFoto());
        Picasso.with(view.getContext())
                .load(String.valueOf(urlPhoto))
                .into(imageView);

        // Verifica se Clinica é favorita e marca estrela
        new MainActivity.GetClinicAsyncTask(view.getContext(), position).execute(clinica.getUniq_id());

        // Botão para favoritar a clinica
        final ImageButton ibStar = view.findViewById(R.id.ib_star);
        if (clinica.getFavorite()){
            ibStar.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.ic_star_full));
        }


        // Evento para setar Clinica Favorita
        ibStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clinica.setFavorite();
                Log.d(TAG, "click favorite..."+clinica.getFavorite());
                if (clinica.getFavorite()){
                    ibStar.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.ic_star_full));
                }else{
                    ibStar.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.ic_star_empty));
                }
                new MainActivity.SetFavoriteAsyncTask(view.getContext()).execute(clinica);
            }
        });

        return view;
    }
}
