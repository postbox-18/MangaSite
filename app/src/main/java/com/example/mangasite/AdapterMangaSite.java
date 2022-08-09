package com.example.mangasite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AdapterMangaSite extends RecyclerView.Adapter<AdapterMangaSite.ViewHolder> {

    private List<MangaSiteList> mangaSiteLists = new ArrayList<>();
    private Context context;
    private String TAG = "AdapterMangaSite";
    private GetViewModel getViewModel;




    public AdapterMangaSite(GetViewModel getViewModel, Context context, List<MangaSiteList> mangaSiteLists) {
        this.getViewModel=getViewModel;
        this.context=context;
        this.mangaSiteLists=mangaSiteLists;
    }

    @NonNull
    @Override
    public AdapterMangaSite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cardview, parent, false);
        return new AdapterMangaSite.ViewHolder(view);
        //return view;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMangaSite.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MangaSiteList item1 = mangaSiteLists.get(position);
        holder.name.setText(item1.getName());
        holder.site.setText(item1.getSite());


    }


    @Override
    public int getItemCount() {
        Log.e(TAG, "item>>49>>" + mangaSiteLists.size());
        return mangaSiteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,site;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            site = view.findViewById(R.id.site);

        }
    }
}

