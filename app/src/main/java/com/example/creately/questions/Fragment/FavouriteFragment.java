package com.example.creately.questions.Fragment;


import android.support.v4.app.Fragment;

/**
 * Created by rahul on 21/12/16.
 */

public class FavouriteFragment extends Fragment {

    private static FavouriteFragment instance;

    public static FavouriteFragment getInstance() {
        if (instance == null)
            return new FavouriteFragment();
        else
            return instance;
    }

}
