package com.admin.claire.materialdesignpatterns_navigationdrawer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView contentTextView;
    EditText editText;
    Button btnOk;

    //在Toolbar上加上「三」圖示
    ActionBarDrawerToggle drawerToggle;

    private static final String NAV_ITEM_ID = "nav_index";
    //用來儲存點擊了的 menuItem id,必免旋轉螢幕後 menu 和 contentTextView 會被重設
    private int navItemId;

    //Intent DisplayMessageActivity 附加資訊
    public static final String EXTRA_MESSAGE = "com.admin.claire.materialdesignpatterns_navigationdrawer.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initHandler();

        if (null != savedInstanceState){
            navItemId = savedInstanceState.getInt(NAV_ITEM_ID, R.id.navigation_item_1);
        } else {
            navItemId = R.id.navigation_item_1;
        }

        navigateTo(navigationView.getMenu().findItem(navItemId));
    }

    private void initView() {
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // The main content view
        contentTextView = (TextView)findViewById(R.id.content_TextView);
        editText = (EditText)findViewById(R.id.content_Edit);
        btnOk = (Button)findViewById(R.id.btnOk);
        //The navigation drawer
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
    }

    private void initHandler() {
        /**
         * 因為不是使用 onCreateOptionsMenu() 來載入導覽菜單，
         * 不能用 onOptionsItemSelected() 來設定反應，
         * 要加上 OnNavigationItemSelectedListener 來操作。
         */
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(MainActivity.this, menuItem.getTitle() + "pressed",
                        Toast.LENGTH_SHORT).show();

                //Snack bar 跟 Toast 不同的是，Snackbar 是以 view 作參數，而不是以 context
                Snackbar.make(contentTextView , menuItem.getTitle() + "pressed",
                        Snackbar.LENGTH_SHORT).show();

                navigateTo(menuItem);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        //在Toolbar上加上「三」圖示
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open,  R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    /**
     * Configuration Change
     *
     */
    private void navigateTo(MenuItem menuItem) {
        contentTextView.setText(menuItem.getTitle());
        navItemId = menuItem.getItemId();
        menuItem.setCheckable(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, navItemId);
    }

    public void sendMessage(View view){
        /** Called when the user taps the Send button */
        Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

    }
}
