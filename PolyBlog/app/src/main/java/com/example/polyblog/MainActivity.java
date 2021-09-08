package com.example.polyblog;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.polyblog.fragment.RecentPostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()){
                case R.id.button_comment:
                    fragment = new RecentPostFragment();
                    break;

                case R.id.button_add_comment:
                    fragment = new AddComment();
                    break;
                case R.id.bottom_event:
                    fragment = new Event();
                    break;
                case R.id.bottom_account:
                    fragment = new EditProfile();
                    break;
            }
            return LoadFragment(fragment);
        }
    };

    private boolean LoadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadFragment(new Comment());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);

    }
}