package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class ContactCallActivity extends AppCompatActivity {

    ListView lv_contact;

    String[] contactName = {"Dad", "Mom", "Brother", "Uncle"};
    String[] phNo = {"9853254685", "1236589745", "1213657789", "1234567890"};
    String selectedContact;
    ArrayList<Item> al;


    private static final int REQUEST_MAKE_CALL = 1;
    private static final int REQUEST_SEND_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        lv_contact = findViewById(R.id.lv_contact);
        al = new ArrayList<>();
        for (int i = 0; i < contactName.length; i++) al.add(new Item(phNo[i], contactName[i]));

        MyAdapter adapter = new MyAdapter(this, R.layout.contact_list_item, al);
        lv_contact.setAdapter(adapter);
        lv_contact.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(this, contactName[position] + " will be called", Toast.LENGTH_SHORT).show();
        });

        registerForContextMenu(lv_contact);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_context_menu, menu);
        menu.setHeaderTitle("Select the action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        selectedContact = phNo[position];
        switch (item.getItemId()){
            case R.id.mi_makeCall:{
                makeCall();
                Toast.makeText(this, "Making call to: "+contactName[position], Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.mi_sendSms:{
                sendSMS();
                Toast.makeText(this, "Sending Message to: "+contactName[position], Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return true;
    }

    public void sendSMS(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},REQUEST_SEND_SMS);
        }else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("smsto:"+selectedContact));
            intent.setType("vnd.android-dir/mms-sms");
            intent.putExtra("address", new String[]{selectedContact});
            intent.putExtra("sms_body", "Hello there Medicine Required please send");
            startActivity(intent);
        }
    }

    public void makeCall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_MAKE_CALL);
        } else {
            if(selectedContact.equals("")){
                Toast.makeText(this, "Invalid Contact", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+selectedContact));
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_MAKE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                } else {
                    Toast.makeText(this, "Call request denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case REQUEST_SEND_SMS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendSMS();
                }else{
                    Toast.makeText(this, "SMS Request denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}

class MyAdapter extends ArrayAdapter<Item> {
    ArrayList<Item> al;
    Activity context;

    MyAdapter(Activity context, int listViewFileID, ArrayList<Item> al) {
        super(context, listViewFileID, al);
        this.context = context;
        this.al = al;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.contact_list_item, null);
        TextView conName = row.findViewById(R.id.tv_contact);
        TextView conPh = row.findViewById(R.id.tv_contact_ph);

        conName.setText(al.get(position).getName());
        conPh.setText(al.get(position).getPh());
        return row;
    }
}

class Item {
    String ph;
    String name;

    public Item(String ph, String name) {
        this.name = name;
        this.ph = ph;
    }

    public String getPh() {
        return ph;
    }

    public String getName() {
        return name;
    }
}