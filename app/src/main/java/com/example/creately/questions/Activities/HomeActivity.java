package com.example.creately.questions.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import com.example.creately.R;
import com.example.creately.questions.Adapter.QuestionsAdapter;
import com.example.creately.questions.ApiGenerator;
import com.example.creately.questions.ApiInterface.StackExchange;
import com.example.creately.questions.Model.Items;
import com.example.creately.questions.Model.Questions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rahul on 08/12/16.
 */

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private QuestionsAdapter questionsAdapter;
    private ArrayList<Items> questionItems;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        questionItems = new ArrayList<Items>();

        setRecyclerView();
        hitApi();

    }

    private void hitApi() {
        StackExchange stackExchangeApi = ApiGenerator.createService(StackExchange.class);
        stackExchangeApi.getAndroidUnansweredQueastion("android", "stackoverflow.com", new Callback<Questions>() {
            @Override
            public void success(Questions questions, Response response) {
                questionItems = questions.getItems();
                Log.d("sizebh",questionItems.size()+"");
                questionsAdapter = new QuestionsAdapter(HomeActivity.this, questionItems);
                recyclerView.setAdapter(questionsAdapter);


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void setRecyclerView() {
       // recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManger = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManger);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
