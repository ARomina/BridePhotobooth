package com.example.bridephotobooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.bridephotobooth.adapter.StickerListAdapter;
import com.knef.stickerview.StickerImageView;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@EActivity(R.layout.activity_edit_photo)
public class EditPhotoActivity extends Activity implements StickerListAdapter.OnClickItem{
    /*StickerImageView iv_sticker;*/
    private StickerListAdapter mStickerListAdapter;
    List<DrawableSticker> stickers = new ArrayList<>();
    public static final int STATE_ANCHOR = 6;

    @ViewById
    Toolbar mToolbar;
    /*@ViewById
    ConstraintLayout mPhotoContainer;*/
    @ViewById
    ImageView mPhoto;
    /*@ViewById
    ImageView mSticker;*/
    @ViewById
    StickerView mStickerView;
    @ViewById
    RecyclerView mRecyclerStickers;

    @Extra
    String mPhotoAbsolutePath;
    @Extra
    File mPhotoFile;

    @AfterViews
    void onFirstLoad(){
        Log.d("PATH EDIT", mPhotoAbsolutePath);
        setActionBar(mToolbar);
        getActionBar().setTitle("Editar imagen");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        mStickerView.setLocked(false);
        mStickerView.setConstrained(true);
        setPreviewImage(mPhotoAbsolutePath);
        setRecyclerStickers();

        /*mSticker.setOnClickListener(view -> {
            mStickerView.addSticker(new DrawableSticker(mSticker.getDrawable()));
            mSticker.setVisibility(View.INVISIBLE);
        });*/

        /*mSticker.setOnClickListener(view -> {
            iv_sticker = new StickerImageView(this);
            iv_sticker.setImageDrawable((mSticker).getDrawable());
            iv_sticker.setClickable(true);
            mPhotoContainer.addView(iv_sticker);
            mSticker.setVisibility(View.INVISIBLE);
        });*/

        /*mPhotoContainer.setOnClickListener(view -> {
            if(iv_sticker != null){
                iv_sticker.setControlsVisibility(false);
            }
        });

        iv_sticker.setOnClickListener(view -> {
            iv_sticker.setControlsVisibility(true);
        });*/
    }

    private void setRecyclerStickers() {
        stickers.add(new DrawableSticker(getDrawable(R.drawable.glasses)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.intercontinental)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.hearts)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.rings)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.blue_fh)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.bow_tie)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.bunny_ears)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.cake)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.couple)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.crown)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.cyan_party_glasses)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.dark_mouth)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.fire)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.green_candy)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.green_glasses)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.green_party_glasses)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.pink_glasses)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.pink_mouth)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.pink_party_glasses)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.purple_mouth)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.rainbow)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.red_glasses)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.red_mouth)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.top_hat)));
        stickers.add(new DrawableSticker(getDrawable(R.drawable.yellow_glasses)));

        mStickerListAdapter = new StickerListAdapter(stickers);
        mStickerListAdapter.setOnClickItem(this);
        mRecyclerStickers.setAdapter(mStickerListAdapter);
        mRecyclerStickers.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId() == R.id.mOptionSave){
            mStickerView.setLocked(true);
            saveImage();
        }else if (item.getItemId() == R.id.mOptionShare){
            shareImage();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void shareImage() {
        File file = getNewFile(this, "Sticker");
        if (file != null) {
            /*Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "image/*");
            startActivity(intent);*/

            mStickerView.save(file);
            Intent i = new Intent(Intent.ACTION_SEND);
            Log.d("FILE", file.getAbsolutePath());
            Log.d("URI", String.valueOf(Uri.fromFile(file)));
            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            i.setType("image/*");
            startActivity(Intent.createChooser(i, "Compartir"));
        } else {
            Toast.makeText(this, "could not share file", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() {
        File file = getNewFile(this, "Sticker");
        if (file != null) {
            mStickerView.save(file);
            Toast.makeText(this, "saved in " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "the file is null", Toast.LENGTH_SHORT).show();
        }
    }

    public static File getNewFile(Context context, String folderName) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

        String timeStamp = simpleDateFormat.format(new Date());

        String path;
        path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + timeStamp + ".jpg";

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        return new File(path);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setPreviewImage(String absolutePath) {
        Glide.with(this)
                .load(absolutePath)
                .into(mPhoto);
    }

    @Override
    public void onClickItem(DrawableSticker drawableSticker) {
        /*Sticker sticker = null;
        if(stickers.contains(drawableSticker)){
            sticker = drawableSticker;
            sticker.setAlpha(1);
        }*/
        mStickerView.addSticker(drawableSticker);
    }

    @Override
    public void removeItem(DrawableSticker drawableSticker) {
        Sticker sticker = null;
        if(stickers.contains(drawableSticker)){
            sticker = drawableSticker;
            sticker.setAlpha(0);
        }
    }
}
