package com.example.android_my_favorites.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.android_my_favorites.model.Clinica;

@Database(entities = {Clinica.class}, version = 1)
public abstract class ClinicaDataBase extends RoomDatabase {

    private static final String TAG = "ClinicaDataBase";
    private static final String DB_NAME = "clinicas.db";
    private static volatile ClinicaDataBase instance;

    public static ClinicaDataBase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static ClinicaDataBase create(Context context){
        return Room.databaseBuilder(context, ClinicaDataBase.class, DB_NAME).build();
    }

    public abstract ClinicaDAO getDao();
}