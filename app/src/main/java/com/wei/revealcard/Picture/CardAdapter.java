package com.wei.revealcard.Picture;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wei.revealcard.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jameson.io.library.util.ToastUtils;

/**
 * Created by wei on 2018/3/29/029.
 */

class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Map<String, Object>> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public CardAdapter(List<Map<String, Object>> cateList) {
        this.mList = cateList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        holder.mImageView.setImageBitmap((Bitmap) mList.get(position).get("pic"));
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastUtils.show(holder.mImageView.getContext(), "" + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
        }

    }

}
