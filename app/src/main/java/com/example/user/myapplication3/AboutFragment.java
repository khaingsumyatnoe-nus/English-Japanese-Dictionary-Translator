package com.example.user.myapplication3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutFragment extends android.app.Fragment {

    public AboutFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView deveinfo=(TextView) view.findViewById(R.id.deveinfo);
        TextView contact=(TextView) view.findViewById(R.id.contact);
        deveinfo.setText("Khaing Su Myat Noe 6IST-61"+"Supervisor: Dr.Phyu Phyu Tar");
        contact.setText("khaingsumyatnoepko@gmail.com");

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
