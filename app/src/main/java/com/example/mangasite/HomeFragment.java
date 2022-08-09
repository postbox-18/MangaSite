package com.example.mangasite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private ImageView add;
    private RecyclerView mangaList;
    private List<MangaSiteList> mangaSiteLists=new ArrayList<>();
    private AdapterMangaSite adapterMangaSite;

    private TextView name,site;
    private String s_name,s_site;
    private GetViewModel getViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        getViewModel = new ViewModelProvider(getActivity()).get(GetViewModel.class);

        add=view.findViewById(R.id.add);
        mangaList=view.findViewById(R.id.mangaList);
        mangaList.setHasFixedSize(true);
        mangaList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertMsg();
            }

            private void AlertMsg() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View customLayout = getLayoutInflater().inflate(R.layout.add_itembox, null);
                getViewModel = new ViewModelProvider(getActivity()).get(GetViewModel.class);
                name = customLayout.findViewById(R.id.name);
                site = customLayout.findViewById(R.id.site);

                builder.setView(customLayout);
                // add a button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        s_name = name.getText().toString();
                        s_site = site.getText().toString();
                        if(s_name.isEmpty())
                        {
                            name.setError("Please enter the name");
                        }
                        else if(s_site.isEmpty())
                        {
                            site.setError("Please enter the url");
                        }
                        else if(!URLUtil.isValidUrl(s_site))
                        {
                            site.setError("Please enter valid url");

                        }
                        else {

                            getViewModel.updateItem(s_name,s_site);
                            Toast.makeText(getContext(), "Item Added", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });

                AlertDialog dialog
                        = builder.create();
                dialog.show();
            }
        });

        getViewModel.getMangaSiteListsLiveData().observe(getViewLifecycleOwner(), new Observer<List<MangaSiteList>>() {
            @Override
            public void onChanged(List<MangaSiteList> mangaSiteLists) {
            adapterMangaSite=new AdapterMangaSite(getViewModel,getContext(),mangaSiteLists);
                mangaList.setAdapter(adapterMangaSite);
            }
        });



        return view;
    }
}