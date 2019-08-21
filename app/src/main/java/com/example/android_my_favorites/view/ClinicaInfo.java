package com.example.android_my_favorites.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_my_favorites.R;
import com.example.android_my_favorites.model.Clinica;
import com.squareup.picasso.Picasso;

import java.net.URL;
import util.NetworkUtil;

public class ClinicaInfo extends AppCompatActivity {

    Context context;
    Clinica clinica;
    String linkPhoto;
    private  static final String TAG = "ClinicaInfo";
    Boolean favorita = false;

    TextView tv_clinica;
    TextView tv_descricao;
    TextView tv_beneficios;
    TextView tv_segmentos;
    TextView tv_telefone;
    TextView tv_endereco;
    ImageView iv_foto;
    ImageButton ib_star;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinica_info_layout);

        context = this.getApplicationContext();
        tv_clinica = findViewById(R.id.tv_clinica);
        tv_descricao = findViewById(R.id.tv_descricao);
        tv_beneficios = findViewById(R.id.tv_beneficios);
        tv_segmentos = findViewById(R.id.tv_segmentos);
        tv_telefone = findViewById(R.id.tv_telefone);
        tv_endereco = findViewById(R.id.tv_endereco);
        iv_foto = findViewById(R.id.iv_clinica);
        ib_star = findViewById(R.id.ib_star);

        Intent intent = getIntent();
        clinica = intent.getParcelableExtra("clinica");

        SetTextInfo();
        SetFavoriteButton();
        SetPhoto();
    }

    @SuppressLint("SetTextI18n")
    private void SetTextInfo(){
        tv_clinica.setText(clinica.getNome_fantasia());
        tv_descricao.setText(clinica.getDescricao());
        tv_beneficios.setText(getString(R.string.beneficios) +' '+ clinica.getBeneficios());
        tv_segmentos.setText(getString(R.string.segmentos) +' '+ clinica.getSegmento());
        tv_telefone.setText(getString(R.string.telefone) +' '+ clinica.getTelefone());
        tv_endereco.setText(getString(R.string.endereco) +' '+ clinica.getEndereco());
    }

    private void SetFavoriteButton(){
        ib_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorita) {
                    favorita = false;
                    ib_star.setImageResource(R.drawable.ic_star_empty);
                    Toast.makeText(ClinicaInfo.this, "Desfavoritado!", Toast.LENGTH_SHORT).show();
                    // To-do: Remover favorita do banco
                }else{
                    favorita = true;
                    ib_star.setImageResource(R.drawable.ic_star_full);
                    Toast.makeText(ClinicaInfo.this, "Favoritado!", Toast.LENGTH_SHORT).show();
                    // To-do: Salvar favorita no banco
                }
            }
        });
    }

    private void SetPhoto(){
        URL urlPhoto = NetworkUtil.buildUrlPhoto(clinica.getUniq_id(), clinica.getFoto());
        Picasso.with(this.context)
                .load(String.valueOf(urlPhoto))
                .into(iv_foto);
    }
}
