package com.example.user.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class MyJpAdapter extends RecyclerView.Adapter<MyHolderjp> implements Filterable {
    Context cp;
    ArrayList<Eng_To_Jp> list, filterList;
    CustomJapanFilter filter;

    public MyJpAdapter(Context ctx, ArrayList<Eng_To_Jp> list) {
        this.cp = ctx;
        this.list = list;
        this.filterList = list;
    }


//    @Override
//    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
//
//        //HOLDER
//       // MyHolder holder=new MyHolder(v);
//        MyJpHolder holder=new MyJpHolder(v);
//        return holder;
//    }
//


    @Override
    public MyHolderjp onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modeljp, null);

        //HOLDER
        MyHolderjp holder = new MyHolderjp(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolderjp holder, int position) {
        holder.japan.setText(list.get(position).getKana_notation());

        //BIND DATA


        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Snackbar.make(v,list.get(pos).getDef(),Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), Detial_Jp_Activity.class);
               intent.putExtra("english1", list.get(pos).getEnglish());
                intent.putExtra("kanji1", list.get(pos).getKanji_notation());
               intent.putExtra("kana1", list.get(pos).getKana_notation());
               intent.putExtra("romaji1", list.get(pos).getRomaji());
               intent.putExtra("part_of_speech1", list.get(pos).getPart_of_speech());
                cp.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount()
        {
            return (list==null) ? 0 : list.size();
        }

        public Filter getFilter()
        {
            if(filter==null)
            {
                filter= new CustomJapanFilter(filterList,this);
            }

            return filter;
        }



    }
