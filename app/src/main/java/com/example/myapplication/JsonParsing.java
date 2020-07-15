package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class JsonParsing extends AppCompatActivity {

    RecyclerView rcv_json;
    String jsonUrl = "https://api.github.com/users";
    Gson gson;
    MyJsonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parsing);
        rcv_json = findViewById(R.id.rcv_json);
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        rcv_json.setLayoutManager(manager);

        StringRequest stringRequest = new StringRequest(jsonUrl,
                response -> {
                    gson = new GsonBuilder().create();
                    JsonPojo[] user = gson.fromJson(response, JsonPojo[].class);
                    adapter = new MyJsonAdapter(getApplication(), user);
                    rcv_json.setAdapter(adapter);
                },
                error -> Toast.makeText(JsonParsing.this, "Error is request", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

/*class JsonItem {
    String name;
    String profileId;

    public JsonItem(String name, String imgId) {
        this.name = name;
        profileId = imgId;
    }

    public String getName() {
        return name;
    }

    public String getProfileId() {
        return profileId;
    }
}*/

class MyJsonAdapter extends RecyclerView.Adapter<MyJsonAdapter.ViewHolder> {

    JsonPojo[] data;
    Context context;

    MyJsonAdapter(Context c, JsonPojo[] list) {
        data = list;
        context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.json_parsing_rcv_items
                , parent
                , false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JsonPojo listData = data[position];
        Picasso.with(context).load(listData.getAvatarUrl()).into(holder.iv_profile);
        holder.tv_name.setText(listData.getLogin());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile;
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_json);
            tv_name = itemView.findViewById(R.id.tv_json);
        }
    }
}