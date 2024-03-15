package com.dating.blinddate.Adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.dating.blinddate.Model.CardModel;

import java.util.List;

public class CardStackCallback extends DiffUtil.Callback{
    private List<CardModel> old, baru;

    public CardStackCallback(List<CardModel> old, List<CardModel> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == baru.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}
