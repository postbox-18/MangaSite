package com.example.mangasite;

import android.app.Application;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetViewModel extends AndroidViewModel {

    private List<MangaSiteList> mangaSiteLists = new ArrayList<>();
    private List<MangaSiteList> selectedMangaSiteLists = new ArrayList<>();
    private MutableLiveData<List<MangaSiteList>> mangaSiteListsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MangaSiteList>> selectedMangaSiteListsLiveData = new MutableLiveData<>();
    //firebase database retrieve
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String TAG = "GetViewModel";

    public GetViewModel(@NonNull Application application) {
        super(application);
        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        GetMangaSite();
    }

    public MutableLiveData<List<MangaSiteList>> getMangaSiteListsLiveData() {
        return mangaSiteListsLiveData;
    }

    public MutableLiveData<List<MangaSiteList>> getSelectedMangaSiteListsLiveData() {
        return selectedMangaSiteListsLiveData;
    }

    public void setSelectedMangaSiteLists(List<MangaSiteList> selectedMangaSiteLists) {
        this.selectedMangaSiteLists = selectedMangaSiteLists;
        this.selectedMangaSiteListsLiveData.postValue(selectedMangaSiteLists);
    }

    private void GetMangaSite() {
        mangaSiteLists = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("MangaSite");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "snap>>" + snapshot);

                for (DataSnapshot shot : snapshot.getChildren()) {
                    Log.e(TAG, "snap>>" + shot.getKey());
                    Log.e(TAG, "snap>>" + shot.getValue());

                    Log.e(TAG, "snap>>shot>>\n"+">>Site>>"+shot.child("Site").getValue().toString());
                    Log.e(TAG, "snap>>shot>>\nName>>>" + shot.child("Name").getValue().toString() );
                    MangaSiteList mangaSiteList = new MangaSiteList();
                    mangaSiteList.setName(shot.child("Name").getValue().toString());
                    mangaSiteList.setSite(shot.child("Site").getValue().toString());


                    mangaSiteLists.add(mangaSiteList);


                }

                mangaSiteListsLiveData.postValue(mangaSiteLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(), "Fail to update data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateItem(String name, String site, DialogInterface dialog) {
        databaseReference = firebaseDatabase.getReference("MangaSite");
        DatabaseReference databaseReference1 = databaseReference.push();
        databaseReference1.child("Name").setValue(name);
        databaseReference1.child("Site").setValue(site);
        dialog.dismiss();

    }
}
