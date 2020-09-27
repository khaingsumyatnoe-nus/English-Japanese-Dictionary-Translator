package com.example.user.myapplication3;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;

public class CustomJapanFilter extends Filter {
    MyJpAdapter adapter;
    ArrayList<Eng_To_Jp> filterList;
    public CustomJapanFilter(ArrayList<Eng_To_Jp> filterList,MyJpAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //Log.v("Constraint",constraint.toString());

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
           // constraint=constraint.toString().toUpperCase();
            //String con=constraint.toString().trim();
            constraint=constraint.toString().toUpperCase();
            String con=constraint.toString();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Eng_To_Jp> jp_filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                //Log.d("Database japan",con);
                //Log.d("Kana notation",filterList.get(i).getKana_notation());


                if(filterList.get(i).getRomaji().toUpperCase().startsWith(con))
                {
                    Log.d("Database japan",con);
                    Log.d("Kana notation",filterList.get(i).getKana_notation());

                    //ADD PLAYER TO FILTERED PLAYERS
                    jp_filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=jp_filteredPlayers.size();
            results.values=jp_filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }
        Log.d("REsults---------", String.valueOf(results));

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
       // adapter.list= (ArrayList<Jp_To_Eng>) results.values;
        adapter.list= (ArrayList<Eng_To_Jp>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }

    }

