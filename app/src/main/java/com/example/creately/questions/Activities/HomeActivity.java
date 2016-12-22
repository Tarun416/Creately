package com.example.creately.questions.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.creately.R;
import com.example.creately.questions.Fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 08/12/16.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        setToolbar();
        attachHomeFragment(savedInstanceState);
    }

    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbarTitle.setText("QuesWiki");
        }
    }

    private void attachHomeFragment(Bundle savedInstanceState) {
        if(savedInstanceState==null) {
            HomeFragment homeFragment = HomeFragment.getInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.container, homeFragment, "homefragment").commit();
        }
        else
        {
            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homefragment");
        }
    }

}