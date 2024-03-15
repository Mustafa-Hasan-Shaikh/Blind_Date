package com.dating.blinddate.Model;

import android.widget.ImageView;
import android.widget.TextView;

public class CardModel {

    int image;

    String textView;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }

    public CardModel(int image, String textView) {
        this.image = image;
        this.textView = textView;
    }


}
