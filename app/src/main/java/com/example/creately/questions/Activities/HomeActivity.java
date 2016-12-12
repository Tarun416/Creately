package com.example.creately.questions.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private QuestionsAdapter questionsAdapter;
    private ArrayList<Items> questionItems;
    MenuItem searchViewItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        questionItems = new ArrayList<Items>();
        setToolbar();
        setRecyclerView();
        hitApi();

    }

    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbarTitle.setText("StackOverflow");

        }
    }

    private void hitApi() {
        StackExchange stackExchangeApi = ApiGenerator.createService(StackExchange.class);
        stackExchangeApi.getAndroidUnansweredQueastion("android", "stackoverflow.com", new Callback<Questions>() {
            @Override
            public void success(Questions questions, Response response) {
                questionItems = questions.getItems();
                questionsAdapter = new QuestionsAdapter(HomeActivity.this, questionItems);
                recyclerView.setAdapter(questionsAdapter);


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManger = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManger);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.search:
               loadToolbarSearch();

        }
        return super.onOptionsItemSelected(item);
    }

    private void loadToolbarSearch() {

    }
}
