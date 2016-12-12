package com.example.creately.questions.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.creately.R;
import com.example.creately.questions.Model.Items;
import com.example.creately.questions.Model.Questions;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 08/12/16.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {


    private Context context;

    private ArrayList<Items> questionItems;


    public QuestionsAdapter(Context context, ArrayList<Items> questionItems) {
        this.questionItems = questionItems;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.unansered_questions, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Items items = questionItems.get(position);
        Log.d("itemmm", items.getTitle());
        holder.questionDescription.setText(items.getTitle());
        holder.rating.setText("Rating : " + items.getScore());
        Glide.with(context).load(items.getOwner().getProfile_image()).into(holder.profiileImage);
        for (int i = 0; i < items.getTags().size(); i++) {
            holder.tags.append(items.getTags().get(i)+" ");
        }
        holder.timeStamp.setText(convertTime(Long.parseLong(items.getCreation_date())));

    }

    private String convertTime(Long creation_date) {
        Date date=new Date(creation_date);
        Format format=new SimpleDateFormat("dd MMMM yyyy  HH:mm");
        return format.format(date);
    }


    @Override
    public int getItemCount() {
        Log.d("size", questionItems.size() + "");
        return questionItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        @BindView(R.id.profiileImage)
        ImageView profiileImage;
        @BindView(R.id.questionDescription)
        TextView questionDescription;
        @BindView(R.id.tags)
        TextView tags;
        @BindView(R.id.rating)
        TextView rating;

        @BindView(R.id.timeStamp)
        TextView timeStamp;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);


        }
    }
}
