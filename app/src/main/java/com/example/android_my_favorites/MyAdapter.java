package com.example.android_my_favorites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_my_favorites.model.Clinica;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import util.NetworkUtil;

public class MyAdapter extends BaseAdapter {

    private List<Clinica> listClinicas;
    private Activity activity;


    MyAdapter(List<Clinica> list, Activity act){
        this.listClinicas = list;
        this.activity = act;
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

        Clinica clinica = (Clinica) getItem(position);
        TextView tvNomeFantasia = view.findViewById(R.id.tv_clinica);
        tvNomeFantasia.setText(clinica.getNome_fantasia());

        ImageView imageView = view.findViewById(R.id.iv_clinica);

        URL urlPhoto = NetworkUtil.buildUrlPhoto(clinica.getUniq_id(), clinica.getFoto());
        Picasso.with(view.getContext())
                .load(String.valueOf(urlPhoto))
                .into(imageView);

        return view;
    }
}
