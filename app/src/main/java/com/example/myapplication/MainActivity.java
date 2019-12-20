package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.news.FragmentNews;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentMovie a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=findViewById(R.id.tablayout_id);
        viewPager=findViewById(R.id.viewpager_id);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());//viewpager adapater

        //adding adapter

        viewPagerAdapter.AddFragment(new FragmentNews(),"news");
        viewPagerAdapter.AddFragment(new FragmentMovie(),"movie");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);//set view pager for tablayout

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        searchView.setQueryHint(getResources().getString(R.string.hint_search));
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(true);
        searchView.clearFocus();

        return true;
    }
}