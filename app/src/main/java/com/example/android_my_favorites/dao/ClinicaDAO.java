package com.example.android_my_favorites.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android_my_favorites.model.Clinica;

import java.util.List;

@Dao
public interface ClinicaDAO {

    @Query("SELECT * FROM clinica")
    List<Clinica> getAllClinicas();

    @Query("SELECT * FROM clinica WHERE uniq_id = :uniq_id")
    List<Clinica> getClinica(String uniq_id);

    @Insert
    void insert (Clinica clinicas);

    @Delete
    void delete (Clinica... clinicas);

    @Update
    void update (Clinica... clinicas);

}