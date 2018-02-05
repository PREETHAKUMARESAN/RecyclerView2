package com.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by PREETHA KUMARESAN on 05-02-2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Contact> data= Collections.emptyList();
    Contact current;

    public ReviewAdapter(Context context,List<Contact> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_item, parent,false);
        MyHolder holder=new MyHolder(view);
        Log.e("Tag","createholder");
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder= (MyHolder) holder;
        current=data.get(position);
        myHolder.name.setText(current.name);
        myHolder.id.setText("ID: " + current.id);
        myHolder.email.setText(current.email);
        myHolder.address.setText(current.address);
        myHolder.gender.setText(current.gender);
        Log.e("Tag","bindholder");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class MyHolder extends RecyclerView.ViewHolder{

    TextView name;
    TextView email;
    TextView id;
    TextView address;
    TextView gender;

    // create constructor to get widget reference
    public MyHolder(View itemView) {
        super(itemView);
        id= (TextView) itemView.findViewById(R.id.id);
        name= (TextView) itemView.findViewById(R.id.name);
        email= (TextView) itemView.findViewById(R.id.email);
        gender= (TextView) itemView.findViewById(R.id.gender);
        address= (TextView) itemView.findViewById(R.id.address);

    }

}

