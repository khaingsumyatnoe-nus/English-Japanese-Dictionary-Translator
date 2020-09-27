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

/**
 * Created by Hp on 3/17/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable{

    Context c;
    ArrayList<Eng_To_Jp> list,filterList;
    CustomFilter filter;


    public MyAdapter(Context ctx,ArrayList<Eng_To_Jp> list)
    {
        this.c=ctx;
        this.list=list;
        this.filterList=list;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);

        //HOLDER
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
            holder.english.setText(list.get(position).getEnglish());

        //BIND DATA


        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Snackbar.make(v,list.get(pos).getDef(),Snackbar.LENGTH_SHORT).show();
                Intent intent=new Intent(v.getContext(),Detail_Activity.class);
                intent.putExtra("english",list.get(pos).getEnglish());
                intent.putExtra("kanji",list.get(pos).getKanji_notation());
                intent.putExtra("kana",list.get(pos).getKana_notation());
                intent.putExtra("romaji",list.get(pos).getRomaji());
                intent.putExtra("part_of_speech",list.get(pos).getPart_of_speech());
                c.startActivity(intent);

            }
        });

    }

    //GET TOTAL NUM OF PLAYERS
    @Override
    public int getItemCount() {
        return list.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }

}
