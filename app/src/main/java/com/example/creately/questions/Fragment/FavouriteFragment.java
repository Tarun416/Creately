package com.example.creately.questions.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creately.R;
import com.example.creately.questions.Adapter.QuestionsAdapter;
import com.example.creately.questions.Interface.OnItemClickListener;
import com.example.creately.questions.Model.UnansweredQues.Items;
import com.example.creately.questions.Utils.CommonUtils;
import com.example.creately.questions.Utils.EndlessRecyclerOnScrollListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 21/12/16.
 */

public class FavouriteFragment extends Fragment {

    private static FavouriteFragment instance;
    @BindView(R.id.rotateloading)
    RotateLoading rotateloading;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nodatatext)
    TextView nodatatext;
    @BindView(R.id.floatingbutton)
    FloatingActionButton floatingbutton;
    private LinearLayoutManager linearLayoutManger;
    private EndlessRecyclerOnScrollListener endlessScrollListener;
    private QuestionsAdapter questionsAdapter;
    private ArrayList<Items> items;
    private DatabaseReference database;
    private DatabaseReference myRef;


    public static FavouriteFragment getInstance() {
        if (instance == null)
            return new FavouriteFragment();
        else
            return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //  return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.unanswered_ques, container, false);
        ButterKnife.bind(this, view);
        getDataFromFirebase();
        floatingbutton.setVisibility(View.GONE);
        return view;
    }

    private void getDataFromFirebase() {
        rotateloading.start();
        items = new ArrayList<>();


        database = FirebaseDatabase.getInstance().getReference();

        myRef = database.child("favQuestions/");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rotateloading.stop();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    items.add(datasnapshot.getValue(Items.class));
                }

                if (items.size() == 0) {
                    nodatatext.setVisibility(View.VISIBLE);
                    nodatatext.setText("No Favourite Ques Found");

                } else {
                    nodatatext.setVisibility(View.GONE);
                    setRecyclerView(items);
                }
                  //  Log.d("result", items.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                rotateloading.stop();
                Log.d("resulterror", databaseError.getMessage());

            }
        });
    }


    private void setRecyclerView(final ArrayList<Items> items) {
        linearLayoutManger = new LinearLayoutManager(getActivity());
        linearLayoutManger.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManger);
        questionsAdapter = new QuestionsAdapter(getActivity(), items, recyclerView, true, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openQuesInBrowser(position);
            }

            @Override
            public void share(int position) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    String sAux = items.get(position).getLink();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }

            @Override
            public void likeButtonClick(int position, ImageButton likeButton) {

              myRef.child(items.get(position).getQuestion_id()).removeValue();
                items.remove(items.get(position));

                questionsAdapter.notifyDataSetChanged();

                if (items.size() == 0) {
                    nodatatext.setVisibility(View.VISIBLE);
                    nodatatext.setText("No Favourite Ques Found");

                } else {
                    nodatatext.setVisibility(View.GONE);
                    setRecyclerView(items);
                }


            }
        });
        recyclerView.setAdapter(questionsAdapter);


    }

    private void openQuesInBrowser(int position) {
        Log.d("position", position + "");
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(items.get(position).getLink()));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
