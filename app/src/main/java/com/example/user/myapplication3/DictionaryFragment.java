package com.example.user.myapplication3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class DictionaryFragment extends android.app.Fragment {
    DatabaseAssets databaseAssets;
    private FragmentListener listener;
    private String value = "Hello everyone";
    String language;
    MyAdapter adapter;
    public DictionaryFragment(){

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dictionary, container, false);

        RecyclerView rv = view.findViewById(R.id.rv);
        databaseAssets = DatabaseAssets.getInstance(getActivity());
        databaseAssets.open();

        ArrayList<Eng_To_Jp> eng_to_jp = databaseAssets.getAll();

        databaseAssets.close();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(
                new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        rv.setItemAnimator(new DefaultItemAnimator());

         adapter = new MyAdapter(getActivity(), eng_to_jp);
        rv.setAdapter(adapter);

        return view;
    }



    public void setOnFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }

    public void filterValue(String s) {
        adapter.getFilter().filter(s);
        Log.d("filtervalue",s);
    }
}
