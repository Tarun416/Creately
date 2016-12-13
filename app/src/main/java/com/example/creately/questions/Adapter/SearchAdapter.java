package com.example.creately.questions.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.creately.R;
import com.example.creately.questions.Interface.OnItemClickListener;
import com.example.creately.questions.Model.Tag.Items;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 13/12/16.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Items> tagItems;
    private OnItemClickListener onItemClickListener;


    public SearchAdapter(Context context, ArrayList<Items> items, OnItemClickListener onClickListener) {
        this.context = context;
        this.tagItems = items;
        this.onItemClickListener=onClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(tagItems.get(position),onItemClickListener,position);
    }

    @Override
    public int getItemCount() {
        return tagItems.size();
    }

    public void updateList(ArrayList<Items> tagItems, boolean b) {
        this.tagItems=tagItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_tagname)
        TextView tagname;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void bind(Items items, final OnItemClickListener onItemClickListener, final int position) {
            tagname.setText(items.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });

        }
    }
}