package com.example.cse.makeupapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = CosmeticModel.class,version = 1,exportSchema = false)
public abstract class CosmeticDatabase extends RoomDatabase {
    public abstract CosmeticDao movieDao();

    private static CosmeticDatabase instance;

    public static CosmeticDatabase getInstance(Context context){
        if(instance==null){
            instance=Room.databaseBuilder(context,CosmeticDatabase.class,"roomLiveDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
