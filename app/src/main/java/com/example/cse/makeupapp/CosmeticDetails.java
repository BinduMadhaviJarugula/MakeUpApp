package com.example.cse.makeupapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

public class CosmeticDetails extends AppCompatActivity {
    private TextView textname;

    String cosid, cosimage, costitle, cosdesc;
    MaterialFavoriteButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_details);
        ImageView imageView = findViewById(R.id.image);
        TextView textView = findViewById(R.id.textview);
        textname=findViewById(R.id.imagename);
        favoriteButton=findViewById(R.id.favid);

        String[] strings=getIntent().getStringArrayExtra("imagedes");
        Picasso.with(this).load(strings[0]).placeholder(R.drawable.ic_launcher_background).into(imageView);
        textView.setText(strings[1]);
        textname.setText(strings[2]);

        cosdesc = strings[1];
        costitle = strings[2];
        cosimage = strings[0];
        cosid = strings[3];

        String savestate;
        savestate=MainActivity.cosmeticViewModel.getIdread(cosid);
        if(savestate!=null){
            favoriteButton.setFavorite(true,true);
        }
        else{
            favoriteButton.setFavorite(false,true);
        }
        favoriteButton.setOnFavoriteChangeListener(     new MaterialFavoriteButton.OnFavoriteChangeListener() {

            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                CosmeticModel myModel=new CosmeticModel();
                if (favorite){
                    myModel.setId(cosid);
                    myModel.setImage_link(cosimage);
                    myModel.setName(costitle);
                    myModel.setDescription(cosdesc);
                    favoriteButton.setFavorite(true);
                    MainActivity.cosmeticViewModel.insertData(myModel);
                    Toast.makeText(CosmeticDetails.this, R.string.added, Toast.LENGTH_SHORT).show();

                }
                else{
                    myModel.setId(cosid);
                    Toast.makeText(CosmeticDetails.this, R.string.remove, Toast.LENGTH_SHORT).show();
                    MainActivity.cosmeticViewModel.deleteData(myModel);
                }
            }
        });
    }
}
