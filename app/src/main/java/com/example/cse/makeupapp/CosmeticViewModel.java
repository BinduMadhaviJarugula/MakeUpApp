package com.example.cse.makeupapp;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;
public class CosmeticViewModel extends AndroidViewModel {
    private final CosRepository cosRepository;
    private final LiveData<List<CosmeticModel>> getAllFavData;
    public CosmeticViewModel(@NonNull Application application) {
        super(application);
        cosRepository=new CosRepository(application);

        getAllFavData=cosRepository.getAllData();
    }
    public  void insertData(CosmeticModel contact){
        cosRepository.insert(contact);
    }
    public  void deleteData(CosmeticModel contact){
        cosRepository.delete(contact);
    }

    public  LiveData<List<CosmeticModel>> getAllFavData(){
        return getAllFavData;
    }

    public String getIdread(String id){
        return cosRepository.read(id);
    }
}
