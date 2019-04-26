package com.example.cse.makeupapp;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class CosRepository {
    private static CosmeticDao cosmeticDao;
    private final LiveData<List<CosmeticModel>> getAllData;

    public CosRepository(Context context) {
        CosmeticDatabase contactDatabase = CosmeticDatabase.getInstance(context);
        cosmeticDao = contactDatabase.movieDao();
        getAllData = cosmeticDao.getfav();
    }
    LiveData<List<CosmeticModel>> getAllData() {
        return getAllData;
    }
    public void insert(CosmeticModel cosmeticModel) {
        new InsertTask().execute(cosmeticModel);
    }
    public void delete(CosmeticModel contact) {
        new DeleteTask().execute(contact);
    }
    public static class InsertTask extends AsyncTask<CosmeticModel, Void, Void> {

        @Override
        protected Void doInBackground(CosmeticModel... cosmeticModels) {
            cosmeticDao.insertCos(cosmeticModels[0]);
            return null;
        }
    }
    public static class DeleteTask extends AsyncTask<CosmeticModel, Void, Void> {
        @Override
        protected Void doInBackground(CosmeticModel... cosmeticModels) {
            cosmeticDao.deleteCos(cosmeticModels[0]);
            return null;
        }
    }
    String read(String id){
        return cosmeticDao.read(id);
    }

}
