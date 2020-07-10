package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    Button btn_camera;
    Button btn_gallery;
    Button btn_cal;
    Button btn_motionSen;
    RelativeLayout rl_login;
    final static int REQUEST_CAMERA = 1;
    final static int RESULT_LOAD_IMAGE = 2;
    final static int MICROPHONE_REQ = 3;
    final static int STORAGE_REQ = 4;

    SharedPreferences appSettings;
    SharedPreferences.Editor appSettingsEdit;
    boolean isDarkModeOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*btn_camera = findViewById(R.id.btn_camera);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_cal = findViewById(R.id.btn_cal);
        btn_motionSen = findViewById(R.id.btn_motionSen);*/
        rl_login = findViewById(R.id.rl_login);

        appSettings = getSharedPreferences("AppSettings", 0);
        appSettingsEdit = appSettings.edit();
        isDarkModeOn = appSettings.getBoolean("nightMode", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void checkForTheme() {
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            appSettingsEdit.putBoolean("nightMode", false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            appSettingsEdit.putBoolean("nightMode", true);
        }
        appSettingsEdit.apply();
    }

    public void openGallery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
        } else {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }

    public void openCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null)
                startActivityForResult(i, REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                }
            }
            break;
            case RESULT_LOAD_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openGallery();
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (resultCode == RESULT_OK) {
                    Bundle extra = data.getExtras();
                    Bitmap bmp = (Bitmap) extra.get("data");
                    rl_login.setBackground(new BitmapDrawable(bmp));
                }
            }
            break;
            case RESULT_LOAD_IMAGE: {
                if (resultCode == RESULT_OK) {
                    Bitmap bmp = null;
                    try {
                        InputStream is = getContentResolver().openInputStream(data.getData());
                        bmp = BitmapFactory.decodeStream(is);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    rl_login.setBackground(new BitmapDrawable(bmp));
                }
            }
            break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_calculator: {
                Intent cal = new Intent(getApplicationContext(), CalculatorActivity.class);
                startActivity(cal);
            }
            break;
            case R.id.mi_changeBG_camera: {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                } else {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (i.resolveActivity(getPackageManager()) != null)
                        startActivityForResult(i, REQUEST_CAMERA);
                }
            }
            break;
            case R.id.mi_changeBG_gallery: {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
                } else {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/*");
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
            break;
            case R.id.mi_motion: {
                Intent sen = new Intent(getApplicationContext(), SensorTest.class);
                startActivity(sen);
            }
            break;
            case R.id.mi_changeTheme: {
                checkForTheme();
            }
            break;
            case R.id.mi_web: {
                Intent web = new Intent(getApplicationContext(), WebActivity.class);
                startActivity(web);
            }
            break;
            case R.id.mi_contact:{
                Intent con = new Intent(getApplicationContext(), ContactCallActivity.class);
                startActivity(con);
            }
            break;
            case R.id.mi_drug_rcv: {
                Intent i = new Intent(getApplicationContext(), DrugRecyclerView.class);
                startActivity(i);
            }
            break;
            case R.id.mi_videoRecord: {
                Intent i = new Intent(getApplicationContext(), VideoRecording.class);
                startActivity(i);
            }
            break;
            case R.id.mi_progressDialog: {
                Intent i = new Intent(getApplicationContext(), ProgressDialogDemo.class);
                startActivity(i);
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu1, menu);
        return true;
    }
}
