package com.example.android_my_favorites.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_my_favorites.R;

public class ClinicaInfo extends AppCompatActivity {

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

        tv_clinica = findViewById(R.id.tv_clinica);
        tv_descricao = findViewById(R.id.tv_descricao);
        tv_beneficios = findViewById(R.id.tv_beneficios);
        tv_segmentos = findViewById(R.id.tv_segmentos);
        tv_telefone = findViewById(R.id.tv_telefone);
        tv_endereco = findViewById(R.id.tv_endereco);
        iv_foto = findViewById(R.id.iv_clinica);
        ib_star = findViewById(R.id.ib_star);
    }
}
