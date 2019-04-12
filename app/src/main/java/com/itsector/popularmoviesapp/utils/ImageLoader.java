package com.itsector.popularmoviesapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.itsector.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by E936 on 4/11/2019.
 */
public class ImageLoader {

    /**
     * Loads the image into the image view provided
     * @param url
     * @param imageView
     */
    public static void loadImage(URL url, ImageView imageView){
        String url_str = String.valueOf(url);

        /*Picasso.get().setLoggingEnabled(true);*/
        Picasso.get().load(url_str).placeholder(R.drawable.ic_launcher_background).into(imageView);
    }

}
