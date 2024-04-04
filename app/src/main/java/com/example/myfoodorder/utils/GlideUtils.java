package com.example.myfoodorder.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myfoodorder.R;

public class GlideUtils {
    public static void loadUrlBanner(String url, ImageView imageView) {
        if (StringUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.img_no_image);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.img_no_image)
                .dontAnimate()
                .into(imageView);
    }

    public static void loadUrl(String url, ImageView imageView) {
        if (StringUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.image_no_available);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.image_no_available)
                .dontAnimate()
                .into(imageView);
    }
}
