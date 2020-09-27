package com.example.user.myapplication3;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by Hp on 3/17/2016.
 */
public class CustomFilter extends Filter{

    MyAdapter adapter;
    ArrayList<Eng_To_Jp> filterList;
    FilterResults results;


    public CustomFilter(ArrayList<Eng_To_Jp> filterList, MyAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            String con=constraint.toString();

//            String con=constraint.toString().trim();
//           // String con=constraint.toString();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Eng_To_Jp> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getEnglish().toUpperCase().startsWith(con))
                {
                    Log.d("Database English",con);
                    Log.d("Eng notation",filterList.get(i).getEnglish());
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));

                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        Log.d("REsults", String.valueOf(results));
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

       adapter.list= (ArrayList<Eng_To_Jp>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
