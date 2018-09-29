package com.example.bridephotobooth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Romina on 08/09/2018.
 */

@EActivity(R.layout.activity_preview_photo)
public class PreviewPhotoActivity extends Activity {

    @ViewById
    ImageView mPreviewPhoto;
    @ViewById
    Button mButtonCancel;
    @ViewById
    Button mButtonContinue;

    @Extra
    String mPhotoAbsolutePath;
    @Extra
    File mPhotoFile;

    @AfterViews
    void onFistLoad(){
        Log.d("PATH PREVIEW", mPhotoAbsolutePath);
        setPreviewImage(mPhotoAbsolutePath);
    }

    private void setPreviewImage(String absolutePath) {
        Glide.with(this)
                .load(absolutePath)
                .into(mPreviewPhoto);
    }

    @Click(R.id.mButtonCancel)
    void onClickCancel(){
        mPhotoFile.delete();
        MainActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .start();
    }

    @Click(R.id.mButtonContinue)
    void onClickContinue(){
        EditPhotoActivity_.intent(this)
                .mPhotoAbsolutePath(mPhotoAbsolutePath)
                .mPhotoFile(mPhotoFile)
                .start();

        //Uri uriSavedImage = FileProvider.getUriForFile(this, "com.example.bridephotobooth.fileprovider", mPhotoFile);
        /*Intent i = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(i, 1);*/
        /*try {
            FileOutputStream fos = context.openFileOutput("file_name"+".txt",Context.MODE_PRIVATE);
            Writer out = new OutputStreamWriter(fos);
            out.write(mytext);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
