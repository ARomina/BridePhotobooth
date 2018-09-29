package com.example.bridephotobooth.adapter;

import com.xiaopo.flying.sticker.DrawableSticker;

public class StickerItem {
    //private Integer res;
    private DrawableSticker drawableSticker;
    private Boolean wasAdded;

    public StickerItem(DrawableSticker drawableSticker, Boolean wasAdded) {
        this.drawableSticker = drawableSticker;
        this.wasAdded = wasAdded;
    }

    /*public StickerItem(Integer res, Boolean wasAdded) {
        this.res = res;
        this.wasAdded = wasAdded;
    }

    public Integer getRes() {
        return res;
    }*/

    public DrawableSticker getDrawableSticker() {
        return drawableSticker;
    }

    public Boolean getWasAdded() {
        return wasAdded;
    }

    public void setWasAdded(Boolean wasAdded) {
        this.wasAdded = wasAdded;
    }
}
