package com.example.user.myapplication3;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyHolderjp extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView japan;
    ItemClickListener itemClickListener;
    public MyHolderjp(View itemView) {
        super(itemView);
        this.japan= (TextView) itemView.findViewById(R.id.japan);
        //this.posTxt= (TextView) itemView.findViewById(R.id.posTxt);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }
    public void setItemClickListener(ItemClickListener jp)
    {
        this.itemClickListener=jp;
    }
}
