package com.example.creately.questions.Interface;

import android.widget.ImageButton;

/**
 * Created by Alok on 13/12/16.
 */

public interface OnItemClickListener {
    void onItemClick(int position);

    void share(int position);

    void likeButtonClick(int position, ImageButton likeButton);
}