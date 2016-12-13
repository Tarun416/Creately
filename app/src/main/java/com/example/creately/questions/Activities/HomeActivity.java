package com.example.creately.questions.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.creately.R;
import com.example.creately.questions.Adapter.QuestionsAdapter;
import com.example.creately.questions.Adapter.SearchAdapter;
import com.example.creately.questions.ApiInterface.StackExchange;
import com.example.creately.questions.Interface.OnItemClickListener;
import com.example.creately.questions.Model.Tag.Tags;
import com.example.creately.questions.Model.UnansweredQues.Items;
import com.example.creately.questions.Model.UnansweredQues.Questions;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.creately.questions.ApiGenerator.createService;

/**
 * Created by rahul on 08/12/16.
 */

public class HomeActivity extends AppCompatActivity  {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rotateloading)
    RotateLoading rotateloading;
    private QuestionsAdapter questionsAdapter;
    private ArrayList<Items> questionItems;
    private ArrayList<com.example.creately.questions.Model.Tag.Items> tagItems;
    MenuItem searchViewItem;
    private StackExchange stackExchangeApi;
    private static String SITE = "stackoverflow.com";
    private SearchAdapter searchAdapter;
    private Dialog toolbarSearchDialog;
    private RecyclerView listSearch;
    private  ArrayList<com.example.creately.questions.Model.Tag.Items> filterList;

    @Override
    public void onBackPressed() {

        if(toolbarSearchDialog!=null)
            toolbarSearchDialog.dismiss();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);

        questionItems = new ArrayList<Items>();
        setToolbar();
        setRecyclerView();
        hitApi("android");
    }
    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbarTitle.setText("StackOverflow");
        }
    }

    private void hitApi(String name) {

        rotateloading.start();
        stackExchangeApi = createService(StackExchange.class);
        stackExchangeApi.getAndroidUnansweredQueastion(name, SITE, new Callback<Questions>() {
            @Override
            public void success(Questions questions, Response response) {
                rotateloading.stop();
                questionItems = questions.getItems();
                questionsAdapter = new QuestionsAdapter(HomeActivity.this, questionItems);
                recyclerView.setAdapter(questionsAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                rotateloading.stop();
            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManger = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManger);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:
                loadToolbarSearch();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadToolbarSearch() {
        getTags();
        inflateSearchView();
    }

    private void inflateSearchView() {
        View view = HomeActivity.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        ImageView imgCancel = (ImageView) view.findViewById(R.id.ic_close);


        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);

        listSearch = (RecyclerView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);


        toolbarSearchDialog = new Dialog(HomeActivity.this, R.style.MaterialSearch);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();
        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });
        filterList=tagItems;
        tagItems = (tagItems != null && tagItems.size() > 0) ? tagItems : new ArrayList<com.example.creately.questions.Model.Tag.Items>();
        searchAdapter = new SearchAdapter(HomeActivity.this, tagItems, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                toolbarSearchDialog.dismiss();
                rotateloading.start();
                if(filterList!=null && filterList.size()>0)
                {
                    hitApi(filterList.get(position).getName());
                }


            }
        });
        listSearch.setVisibility(View.VISIBLE);
        listSearch.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        listSearch.setLayoutManager(linearLayoutManager);
        listSearch.setAdapter(searchAdapter);


        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(tagItems, true);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filterList = new ArrayList<com.example.creately.questions.Model.Tag.Items>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < tagItems.size(); i++) {

                        if (tagItems.get(i).getName().toLowerCase().startsWith(s.toString().trim().toLowerCase())) {
                            filterList.add(tagItems.get(i));
                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList,true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                    else
                    {
                        txtEmpty.setVisibility(View.GONE);
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }







    private void getTags() {
        stackExchangeApi.getTags(SITE, new Callback<Tags>() {
            @Override
            public void success(Tags tagModel, Response response) {
                tagItems = tagModel.getItems();
            }
            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}