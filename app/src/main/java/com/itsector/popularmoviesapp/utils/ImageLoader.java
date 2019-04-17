package com.itsector.popularmoviesapp.utils;

import android.widget.ImageView;

import com.itsector.popularmoviesapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;

/**
 * Abstraction layer to deal with Image Loading (using Picasso)
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
        /*Glide.with(context).load(url_str).placeholder(R.drawable.ic_launcher_background).into(imageView);*/
    }

    /**
     * Loads the image into a view, thought a target
     * @param url
     * @param target
     */
    public static void loadImage(URL url, Target target){
        String url_str = String.valueOf(url);

        Picasso.get().load(url_str).placeholder(R.drawable.ic_launcher_background).into(target);
    }

}
