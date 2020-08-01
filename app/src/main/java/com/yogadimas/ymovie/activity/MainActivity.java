package com.yogadimas.ymovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.yogadimas.ymovie.R;
import com.yogadimas.ymovie.fragment.FavoriteMovie;
import com.yogadimas.ymovie.fragment.FavoriteTv;
import com.yogadimas.ymovie.fragment.MovieFragment;
import com.yogadimas.ymovie.fragment.TvShowFragment;
import com.yogadimas.ymovie.fragment.home.HomeFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView profileCircleIv;
    String profileImageUrl = "https://i.postimg.cc/fyhm4nMs/yoga-dimas.jpg";
    String KEY_FRAGMENT;
    String KEY_TITLE;
    long backPressedTime = 0;
    private Fragment pageContent = new HomeFragment();
    private String title = "Home";
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        final DrawerLayout drawerLayout = findViewById(R.id.main_drawer);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.action_change_settings) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
            return true;
        });

        NavigationView navigationView = findViewById(R.id.main_navigation);
        profileCircleIv = navigationView.getHeaderView(0).findViewById(R.id.imageProfile);
        Picasso.get()
                .load(profileImageUrl)
                .into(profileCircleIv);
        navigationView.setNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    pageContent = new HomeFragment();
                    title = "Home";
                    break;
                case R.id.menu_movie:
                    pageContent = new MovieFragment();
                    title = "Movie Fragment";
                    break;
                case R.id.menu_tv:
                    pageContent = new TvShowFragment();
                    title = "Tv Fragment";
                    break;
                case R.id.menu_movieFavorite:
                    pageContent = new FavoriteMovie();
                    title = " Movie Favorite Fragment";
                    break;
                case R.id.menu_tvFavorite:
                    pageContent = new FavoriteTv();
                    title = "Tv Favorite Fragment";
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
            toolbar.setTitle(title);
            drawerLayout.closeDrawers();
            return true;
        });
        if (savedInstanceState != null) {
            pageContent = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            title = savedInstanceState.getString(KEY_TITLE);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
        toolbar.setTitle(title);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_TITLE, title);
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, pageContent);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), getString(R.string.exit), Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}
