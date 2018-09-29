package com.example.bridephotobooth.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bridephotobooth.R;
import com.xiaopo.flying.sticker.DrawableSticker;

import java.util.ArrayList;
import java.util.List;

public class StickerListAdapter extends RecyclerView.Adapter<StickerListAdapter.ViewHolder> {
    /*private List<Integer> items;*/
    private List<StickerItem> itemsStickers = new ArrayList<>();
    private List<DrawableSticker> items;
    private OnClickItem onClickItem;
    private Boolean isAdded;

    public StickerListAdapter(List<DrawableSticker> items) {
        for(DrawableSticker d : items){
            itemsStickers.add(new StickerItem(d, false));
        }
    }

    /*public StickerListAdapter(List<Integer> items) {
        for(Integer i : items){
            itemsStickers.add(new StickerItem(i, false));
        }
    }*/

    /*public StickerListAdapter(List<DrawableSticker> items) {
        this.items = items;
    }*/

    @Override
    public StickerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sticker, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StickerListAdapter.ViewHolder holder, int position) {
        holder.onBind(itemsStickers.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsStickers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mSticker;

        public ViewHolder(View itemView) {
            super(itemView);

            mSticker = itemView.findViewById(R.id.mSticker);

            mSticker.setOnClickListener(view -> {
                //onClickItem.onClickItem(items.get(getAdapterPosition()));
                StickerItem stickerItem = itemsStickers.get(getAdapterPosition());
                if(stickerItem.getWasAdded()){
                    stickerItem.setWasAdded(false);
                    onClickItem.removeItem(stickerItem.getDrawableSticker());
                }else{
                    stickerItem.setWasAdded(true);
                    onClickItem.onClickItem(itemsStickers.get(getAdapterPosition()).getDrawableSticker());
                }
            });
        }

        public void onBind(StickerItem stickerItem){
            Glide.with(itemView.getContext())
                    .load(stickerItem.getDrawableSticker().getDrawable())
                    .into(mSticker);
        }

        /*public void onBind(DrawableSticker drawableSticker){
            Glide.with(itemView.getContext())
                    .load(drawableSticker.getDrawable())
                    .into(mSticker);
        }*/
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClickItem(DrawableSticker drawableSticker);
        void removeItem(DrawableSticker drawableSticker);
        //void onClickItem(Integer res);
        //void removeItem(Integer res);
    }
}
