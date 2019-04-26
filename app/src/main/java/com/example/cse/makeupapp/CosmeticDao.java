package com.example.cse.makeupapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CosmeticDao {
    @Insert
     void insertCos(CosmeticModel cosmeticModel);

    @Query("select * from CosmeticModel")
     LiveData<List<CosmeticModel>> getfav();

    @Delete
     void deleteCos(CosmeticModel myModel);

    @Query("select id from CosmeticModel where id=:readid")
    String read(String readid);

}
