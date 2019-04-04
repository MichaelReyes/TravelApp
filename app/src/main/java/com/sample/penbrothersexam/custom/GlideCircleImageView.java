package com.sample.penbrothersexam.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.sample.penbrothersexam.R;
import com.sample.penbrothersexam.utils.GlideApp;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlideCircleImageView extends CircleImageView {

    public GlideCircleImageView(Context context) {
        this(context, null);
    }

    public GlideCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl == null)
            return;
        GlideApp.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(this);
    }

    public void setImageFile(File file) {
        GlideApp.with(getContext())
                .load(file)
                .placeholder(R.drawable.ic_launcher_background)
                .into(this);
    }


}
