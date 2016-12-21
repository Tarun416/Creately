package com.example.creately.questions.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.creately.R;
import com.example.creately.questions.Interface.OnItemClickListener;
import com.example.creately.questions.Model.UnansweredQues.Items;
import com.example.creately.questions.Utils.CommonUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 08/12/16.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {


    private Context context;
    private OnMoreLoadListener onMoreLoadListener;
    private int totalItemCount,lastVisiblePosition;
    private int visibleThreshhold=2;
    private boolean loading;

    private ArrayList<Items> questionItems;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;



    public QuestionsAdapter(Context context, ArrayList<Items> questionItems, RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.questionItems = questionItems;
        this.context = context;
        this.onItemClickListener=onItemClickListener;
       /* if(recyclerView.getLayoutManager() instanceof LinearLayoutManager)
        {
          final  LinearLayoutManager lw= (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount=lw.getItemCount();
                    lastVisiblePosition=lw.findLastVisibleItemPosition();
                    if(!loading && totalItemCount<=lastVisiblePosition+visibleThreshhold )
                    {
                        if(onMoreLoadListener!=null)
                            onMoreLoadListener.onLoadMore();

                        loading=true;
                    }
                }
            });
        }*/
    }


    public void setOnMoreLoadListener(OnMoreLoadListener onMoreLoadListener) {
        this.onMoreLoadListener = onMoreLoadListener;
    }

    public void setLoaded() {
        loading=false;
    }


    public interface OnMoreLoadListener {
        void onLoadMore();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.unansered_questions, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Items items = questionItems.get(position);
        Log.d("itemmm", items.getTitle());
        holder.questionDescription.setText(items.getTitle());
        holder.rating.setText("Rating : " + items.getScore());
        Glide.with(context).load(items.getOwner().getProfile_image()).into(holder.profiileImage);
        for (int i = 0; i < items.getTags().size(); i++) {
            holder.tags.append(items.getTags().get(i) + " ");
        }
        holder.timeStamp.setText(CommonUtils.toRelativeTime(new DateTime(Long.parseLong(items.getLast_activity_date()) * 1000, DateTimeZone.getDefault())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.d("size", questionItems.size() + "");
        return questionItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        @BindView(R.id.shareButton)
        ImageButton shareButton;
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
