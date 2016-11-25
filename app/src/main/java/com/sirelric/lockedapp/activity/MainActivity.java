package com.sirelric.lockedapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.sirelric.lockedapp.R;


public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    ImageView image;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.activity_main);

        createDrawer();

        //showNewsFragment();


        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_home) {
                    //  Home screen being called
                    Log.d(TAG, "Home pressed from Drawer");
//                    showNewsFragment();
                    Log.d(TAG, "NewsFragment() Displayed");
                    menuItem.setChecked(true); //Set Menu item to highlighted
                }



                if (menuItem.getItemId() == R.id.nav_item_bug) {
                    //  Events screen being called
                    reportBug();

                }
                return false;
            }

        });


        toolbarCreate();
//        setToolbarTitle("News", R.drawable.pirateship2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d(TAG, "Settings Pressed");
            //  Settings menu being called
            // TODO: 5/17/2016 Figure out why screen is not clearing for the settings view.
            getFragmentManager().beginTransaction()
                    //.replace(android.R.id.content, new SettingsFragment())
                    .commit();
            Log.d(TAG, "SettingsFragment() Displayed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toolbarCreate() {

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,
                toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    public void setToolbarTitle(String Title,int imgId) {

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        assert collapsingToolbar != null;
        collapsingToolbar.setTitle(Title);
        image = (ImageView) findViewById(R.id.backdrop);
        assert image != null;
        image.setImageResource(imgId);

    }

    public void createDrawer() {
        /**
         * Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navview);
    }



    private void reportBug () {
        String urlString="https://gitreports.com/issue/SirElric/LockedApp";
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            startActivity(intent);
        }
    }
}
