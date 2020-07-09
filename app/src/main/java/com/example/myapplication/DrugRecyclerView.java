package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DrugRecyclerView extends AppCompatActivity {

    RecyclerView rcv_drug;
    MyDrugList[] drug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_recycler_view);

        rcv_drug = findViewById(R.id.rcv_drug);
        drug = new MyDrugList[]{
                new MyDrugList("Acebutolol", android.R.drawable.ic_input_delete),
                new MyDrugList("Acticin", android.R.drawable.ic_dialog_alert),
                new MyDrugList("Paracetamol", android.R.drawable.ic_dialog_email),
                new MyDrugList("Azithromycin", android.R.drawable.ic_dialog_map),
                new MyDrugList("Hydroxychloroquinesaascas", android.R.drawable.ic_menu_send),
                new MyDrugList("Azithromycin", android.R.drawable.ic_dialog_map),

        };

        MyRecyclerAdapter adapter = new MyRecyclerAdapter(drug);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL
//                , false);
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        rcv_drug.setLayoutManager(manager);
        rcv_drug.setAdapter(adapter);
    }
}

class MyDrugList {
    String drugName;
    int imgId;

    public MyDrugList(String drugName, int imgId){
        this.drugName = drugName;
        this.imgId = imgId;
    }
    public String getDrugName(){
        return drugName;
    }

    public int getImgId() {
        return imgId;
    }
}

class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

    MyDrugList[] data;

    MyRecyclerAdapter(MyDrugList[] data){
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemList = inflater.inflate(R.layout.drug_items_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(itemList);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDrugList listData = data[position];
        holder.iv_drug.setImageResource(data[position].getImgId());
        holder.tv_drugDes.setText(data[position].getDrugName());
        holder.ll_drug_item.setOnClickListener(view -> Toast.makeText(view.getContext(), listData.getDrugName()+" will be delivered soon", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_drug;
        TextView tv_drugDes;
        RelativeLayout ll_drug_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_drug = itemView.findViewById(R.id.iv_drug);
            tv_drugDes = itemView.findViewById(R.id.tv_drug_des);
            ll_drug_item = itemView.findViewById(R.id.ll_drug_item);
        }
    }
}