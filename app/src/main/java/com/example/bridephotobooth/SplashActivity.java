package com.example.bridephotobooth;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Romina on 08/09/2018.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity{
    static final int REQUEST_IMAGE_CAPTURE = 250;
    String mPhotoAbsolutePath;
    File mPhotoFile;

    @ViewById
    Button mButtonSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @AfterViews
    void onFirstLoad(){

    }

    @Click(R.id.mButtonSplash)
    void onClickButton(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getContext().getPackageManager()) != null) {
            File folder = createOrGetFolder();
            if(folder != null){
                File photo = createPhotoFile(folder.getAbsolutePath());
                if (photo != null) {
                    mPhotoFile = photo;
                    Uri mOutputFileUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName()
                            + ".fileprovider", photo);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
                    i.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                }
            }
        }

    }

    private File createOrGetFolder() {
        //File folder = new File(this.getFilesDir() + File.separator + "BridePhotobooth" + File.separator);
        //File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "BridePhotobooth" + File.separator);
        //File folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //if directory does not exist
        if(!folder.exists() || folder.isDirectory()){
            folder.mkdirs();
        }

        Log.d("FOLDER PATH CREATED", folder.getAbsolutePath());
        return folder;
    }

    private File createPhotoFile(String folderAbsolutePath) {
        File photo = null;
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("ddMMyyyy_hhmmss");
        String timestamp = datetimeFormat.format(new Date());
        String photoFileName = "photo_" + timestamp;

        //absolute path is used for the camera intent
        photo = new File(folderAbsolutePath, photoFileName);
        Log.d("PHOTO PATH CREATED", photo.getAbsolutePath());
        mPhotoAbsolutePath = photo.getAbsolutePath();
        return photo;
    }

    private Context getContext() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            EditPhotoActivity_.intent(this)
                    .mPhotoAbsolutePath(mPhotoAbsolutePath)
                    .mPhotoFile(mPhotoFile)
                    .start();
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
