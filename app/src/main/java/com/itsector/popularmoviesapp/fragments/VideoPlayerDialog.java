/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itsector.popularmoviesapp.R;

/**
 * Created by E936 on 4/23/2019.
 */
public class VideoPlayerDialog extends DialogFragment {
    public String mVideoID;

    public VideoPlayerDialog(){}

    public void setVideoID(String mVideoID) {
        this.mVideoID = mVideoID;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        new AlertDialog.Builder(getActivity())
                .setTitle("asa")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                })
                .create();

        View view = inflater.inflate(R.layout.video_alert_box, null);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.video_container, new YoutubePlayerFragment().newInstance(mVideoID));

        ft.commit();

        return view;
    }
}
