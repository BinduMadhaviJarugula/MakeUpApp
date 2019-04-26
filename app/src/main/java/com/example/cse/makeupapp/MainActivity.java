package com.example.cse.makeupapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    ArrayList<CosmeticModel> arrayList;
    final String str="http://makeup-api.herokuapp.com/api/v1/products.json";
    static CosmeticViewModel cosmeticViewModel;
    //private ScrollView coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler);
        //coordinatorLayout=findViewById(R.id.scroll);
        progressBar=findViewById(R.id.progress);
        arrayList=new ArrayList<>();
        cosmeticViewModel= ViewModelProviders.of(this).get(CosmeticViewModel.class);

        new MakeAsyc().execute(str);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        recyclerView.setAdapter(new CosAdapter(this,arrayList));



    }
    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected() && netInfo.isAvailable();
    }

    public class MakeAsyc extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(str);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    return scanner.next();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            if (isOnline()) {
                if (s != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                           String id=jsonObject.getString(getString(R.string.id));
                            String name=jsonObject.getString(getString(R.string.name));
                            String image_link = jsonObject.getString(getString(R.string.image));
                            String description = jsonObject.getString(getString(R.string.descr));
                            arrayList.add(new CosmeticModel(id,name,image_link, description));


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MainActivity.this, R.string.nodata, Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.cos);
                builder.setMessage(R.string.check);
                builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cosmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuid:

                arrayList.clear();
                cosmeticViewModel.getAllFavData().observe(MainActivity.this, new Observer<List<CosmeticModel>>() {
                    @Override
                    public void onChanged(@Nullable List<CosmeticModel> cosmeticModels) {
                        if (!cosmeticModels.isEmpty()) {
                            Snackbar snackbar = Snackbar
                                    .make(recyclerView, "List of Favourites", Snackbar.LENGTH_LONG);

                            snackbar.show();
                            //Toast.makeText(MainActivity.this, "Favourites Activity", Toast.LENGTH_SHORT).show();
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                            recyclerView.setAdapter(new CosAdapter(MainActivity.this, (ArrayList<CosmeticModel>) cosmeticModels));

                        } else {
                            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(R.string.cosmetic);
                            builder.setMessage(R.string.nofav);
                            builder.setNegativeButton(R.string.cont, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.show();
                        }
                    }
                });
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
