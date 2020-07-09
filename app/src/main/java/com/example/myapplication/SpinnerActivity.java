package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SpinnerActivity extends AppCompatActivity {

    Spinner spinner;
    SearchView search;
    ListView lvContact;
    private String[] medicine = {"Acebutolol", "Acticin", "Paracetamol", "Azithromycin", "Hydroxychloroquine"};
    private ArrayList<String> contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        spinner = findViewById(R.id.spin);
        search = findViewById(R.id.sv);
        lvContact = findViewById(R.id.lv_contact);
        contact = new ArrayList<>(Arrays.asList("Ram Medicines", "Avadh Medicines", "Nachiketa Medicines",
                "Apollo Medicines", "Pharma Medicines"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, medicine);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(), medicine[pos] + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapterSv = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contact);
        lvContact.setAdapter(adapterSv);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterSv.getFilter().filter(s);
                return false;
            }
        });

        lvContact.setOnItemClickListener((adapterView, view, i, l) -> {
        });
    }
}
