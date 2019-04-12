/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helper methods for everything related with the Movies entity
 * Created by E936 on 4/12/2019.
 */
public class MovieUtils implements Constants {

    /**
     * Returns the full URL to the thumbnail image
     * @param imgPath - path to the img (e.g. '/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg')
     * @return the URL object
     */
    public static URL getImgURL(String imgPath){
        URL url = null;

        try {
            url = new URL(BASE_API_IMG_URL + BASE_API_IMG_SIZE + imgPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
