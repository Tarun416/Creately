package com.example.creately.questions.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.creately.R;
import com.example.creately.questions.Adapter.QuestionsAdapter;
import com.example.creately.questions.Adapter.SearchAdapter;
import com.example.creately.questions.ApiInterface.StackExchange;
import com.example.creately.questions.Fragment.HomeFragment;
import com.example.creately.questions.Model.UnansweredQues.Items;
import com.example.creately.questions.Utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 08/12/16.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private QuestionsAdapter questionsAdapter;
    private ArrayList<Items> questionItems = new ArrayList<Items>();
    private ArrayList<com.example.creately.questions.Model.Tag.Items> tagItems;
    MenuItem searchViewItem;
    private StackExchange stackExchangeApi;
    private static String SITE = "stackoverflow.com";
    private SearchAdapter searchAdapter;
    private Dialog toolbarSearchDialog;
    private RecyclerView listSearch;
    private ArrayList<com.example.creately.questions.Model.Tag.Items> filterList;
    private View view;
    private WindowManager windowManager;
    private String[] items = {"Activity", "Creation", "Votes", "Relevance"};
    private String tag;
    private String sortname;
    private LinearLayoutManager linearLayoutManger;
    private EndlessRecyclerOnScrollListener endlessScrollListener;
    private Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        setToolbar();
        attachFragment();
    }

    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbarTitle.setText("QuesWiki");
        }
    }


    private void attachFragment() {

        HomeFragment homeFragment = HomeFragment.getInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, homeFragment, "homefragment").commit();

    }

    @Override
    public void onClick(View view) {

    }

   /* private void setRecylerView(Bundle savedInstanceState) {
        linearLayoutManger = new LinearLayoutManager(this);
        linearLayoutManger.setOrientation(LinearLayoutManager.VERTICAL);
        endlessScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManger) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("endlessscroll", current_page + "");
                hitApi(sortname, tag, current_page);
            }
        };
        recyclerView.setLayoutManager(linearLayoutManger);
        recyclerView.addOnScrollListener(endlessScrollListener);
        questionsAdapter = new QuestionsAdapter(HomeActivity.this, questionItems, recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openQuesInBrowser(position);
            }

            @Override
            public void share(int position) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    String sAux = questionItems.get(position).getLink();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        recyclerView.setAdapter(questionsAdapter);

       *//* questionsAdapter.setOnMoreLoadListener(new QuestionsAdapter.OnMoreLoadListener() {
            @Override
            public void onLoadMore() {
                //add progress item
                questionItems.add(null);
                questionsAdapter.notifyItemInserted(questionItems.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //remove progress item
                        questionItems.remove(questionItems.size() - 1);
                        questionsAdapter.notifyItemRemoved(questionItems.size());
                        //add items one by one
                        for (int i = 0; i < 15; i++) {
                            questionItems.add(questionItems.get(questionItems.size()+1));
                            questionsAdapter.notifyItemInserted(questionItems.size());
                        }
                        questionsAdapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });*//*


    }

    private void openQuesInBrowser(int position) {
        Log.d("position", position + "");
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(questionItems.get(position).getLink()));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ITEMS", questionItems);
    }

    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbarTitle.setText("QuesWiki");
        }
    }

    private void hitApi(String sort, String name, final int page) {

        tag = name;
        sortname = sort;
        if (page == 1)
            rotateloading.start();
        stackExchangeApi = createService(StackExchange.class);
        stackExchangeApi.getAndroidUnansweredQueastion(sort, "desc", page, name, SITE, new Callback<Questions>() {
            @Override
            public void success(Questions questions, Response response) {
                rotateloading.stop();
                recyclerView.setVisibility(View.VISIBLE);
                if (page == 1)
                    questionItems.clear();
                questionItems.addAll(questions.getItems());
                questionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                rotateloading.stop();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    public void removeInflatedView() {
        if (view != null && windowManager != null) {
            toolbarSearchDialog.dismiss();
            windowManager.removeView(view);
        } else {
            super.onBackPressed();
        }
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

        view = HomeActivity.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
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
        filterList = tagItems;
        tagItems = (tagItems != null && tagItems.size() > 0) ? tagItems : new ArrayList<com.example.creately.questions.Model.Tag.Items>();
        searchAdapter = new SearchAdapter(HomeActivity.this, tagItems, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                toolbarSearchDialog.dismiss();
                //  rotateloading.start();
                if (filterList != null && filterList.size() > 0) {
                    recyclerView.setVisibility(View.GONE);
                    endlessScrollListener.reset(0, true);
                    hitApi(sortname, filterList.get(position).getName(), 1);
                }


            }

            @Override
            public void share(int position) {

            }

        });
        listSearch.setVisibility(View.VISIBLE);
        listSearch.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    } else {
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
        if (stackExchangeApi == null)
            stackExchangeApi = createService(StackExchange.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingbutton:
                invokeDialog();
        }

    }

    private void invokeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Select the sorting");


        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListView lw = ((AlertDialog) dialogInterface).getListView();
                if (lw.getCheckedItemPosition() != -1) {
                    Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                    String sortname = (String.valueOf(checkedItem));
                    recyclerView.setVisibility(View.GONE);
                    endlessScrollListener.reset(0, true);
                    hitApi(sortname, tag, 1);
                }
                dialogInterface.dismiss();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        AlertDialog alert1 = builder.create();
        alert1.show();

    }*/
}