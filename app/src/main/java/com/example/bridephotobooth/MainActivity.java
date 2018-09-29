package com.example.bridephotobooth;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private Handler mBackgroundHandler;

    @ViewById
    CameraView mCameraView;

    @AfterViews
    void onFirstLoad(){
        mCameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                /*getBackgroundHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                "picture.jpg");
                        OutputStream os = null;
                        try {
                            os = new FileOutputStream(file);
                            os.write(jpeg);
                            os.close();

                            PreviewPhotoActivity_.intent(getContext())
                                    .mPhotoAbsolutePath(file.getAbsolutePath())
                                    .mPhotoFile(file)
                                    .start();
                        } catch (IOException e) {
                            Log.d("PHOTO TAKEN", "Cannot write to " + file, e);
                        } finally {
                            if (os != null) {
                                try {
                                    os.close();
                                } catch (IOException e) {
                                    // Ignore
                                }
                            }
                        }
                    }
                });*/

                        Log.d("AFTER PICTURE TAKEN", String.valueOf(jpeg.length));
                        File folder = createOrGetFolder();
                        File photo = createPhotoFile(folder.getAbsolutePath());

                        try {
                            OutputStream stream;
                            stream = new FileOutputStream(photo);
                            stream.write(jpeg);
                            stream.close();
                            /*FileOutputStream stream = new FileOutputStream(photo.getPath());
                            //Se escribe el byte array en el file
                            stream.write(jpeg);
                            stream.flush();
                            stream.close();*/

                            PreviewPhotoActivity_.intent(getContext())
                                    .mPhotoAbsolutePath(photo.getAbsolutePath())
                                    .mPhotoFile(photo)
                                    .start();
                        } catch (Exception e) {
                            Log.d("SAVE PHOTO", "Exception", e);
                        }
            }
        });
    }

    /*private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }*/

    private File createOrGetFolder() {
        //File folder = new File(this.getFilesDir() + File.separator + "BridePhotobooth" + File.separator);
        //File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "BridePhotobooth" + File.separator);

        //si el directorio no existe
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
        String photoFileName = "_photo_" + timestamp;

        photo = new File(folderAbsolutePath, photoFileName);
        Log.d("PHOTO PATH CREATED", photo.getAbsolutePath());
        return photo;
    }

    //imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
    private Context getContext() {
        return this;
    }

    @Click(R.id.mButtonTakePhoto)
    void onClickButtonTakePhoto(){
        mCameraView.capturePicture();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraView.destroy();
    }
}
