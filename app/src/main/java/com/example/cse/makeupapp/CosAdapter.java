package com.example.cse.makeupapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CosAdapter extends RecyclerView.Adapter<CosAdapter.CosSubClass> {
    private final Context context;
    private final ArrayList<CosmeticModel> arrayList;
    private String[] str;

    SharedPreferences shared;
    SharedPreferences.Editor sharededit;


    public CosAdapter(MainActivity mainActivity, ArrayList<CosmeticModel> arrayList) {
        this.context = mainActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CosAdapter.CosSubClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowdesign, parent, false);
        return new CosSubClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CosAdapter.CosSubClass holder, final int position) {
        final CosmeticModel cosmeticModel = arrayList.get(position);
        holder.textView.setText(cosmeticModel.getName());
        Picasso.with(context).load(cosmeticModel.image_link).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = new String[4];
                str[0] = arrayList.get(position).getImage_link();
                str[1] = arrayList.get(position).getDescription();
                str[2] = arrayList.get(position).getName();
                str[3] = arrayList.get(position).getId();
                


                Intent intent = new Intent(context, CosmeticDetails.class);
                intent.putExtra("imagedes", str);
                context.startActivity(intent);

                shared = context.getSharedPreferences("cosmeticname", Context.MODE_PRIVATE);
                sharededit = shared.edit();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(cosmeticModel.getName());
                sharededit.putString("putintent", stringBuffer.toString());
                sharededit.apply();
                Intent intent1 = new Intent(context, CosmeticWidget.class);
                intent1.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] cosid = AppWidgetManager.getInstance(context).
                        getAppWidgetIds(new ComponentName(context.getApplicationContext(), CosmeticWidget.class));
                intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, cosid);
                context.sendBroadcast(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CosSubClass extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public CosSubClass(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textname);
            imageView = itemView.findViewById(R.id.image1);
        }
    }
}
