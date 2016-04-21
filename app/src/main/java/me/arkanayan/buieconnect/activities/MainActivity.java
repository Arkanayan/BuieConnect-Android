package me.arkanayan.buieconnect.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import io.fabric.sdk.android.services.common.Crash;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.exceptions.UserDetailsNotPresent;
import me.arkanayan.buieconnect.models.Notice;
import me.arkanayan.buieconnect.models.User;
import me.arkanayan.buieconnect.utils.Prefs;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoticesFragment.OnListFragmentInteractionListener {

/*    @Bind(R.id.text_view_header_name)
    TextView headerName;

    @Bind(R.id.text_view_header_email)
    TextView headerEmail;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Launches MainActivity if user is logged in else launches login activity
        boolean isLoggedIn = Prefs.getInstance(this).getBoolean(Prefs.Key.IS_LOGGED_IN);
        boolean isUserDetailsPresent = Prefs.getInstance().getBoolean(Prefs.Key.IS_USER_DETAILS_PRESENT);

        if (!isLoggedIn && !isUserDetailsPresent) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

       // Debug.startMethodTracing("startup");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // analytics
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Main Screen")
                .putContentType("Screen")
                .putContentId("screen-main"));

        // Add noticelist fragment
        NoticesFragment noticesFragment = NoticesFragment.newInstance(1);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, noticesFragment);
        //transaction.addToBackStack(null);
        transaction.commit();

//        ButterKnife.bind(this);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);

        TextView headerEmail = (TextView) headerview.findViewById(R.id.text_view_header_email);
        TextView headerName = (TextView) headerview.findViewById(R.id.text_view_header_name);
        try {
            User user = User.loadUserFromPreference(this);

            //Crashlytics indentifier
            Crashlytics.setUserName(user.getFirstName());
            Crashlytics.setUserIdentifier(String.valueOf(user.getId()));

            headerName.setText(String.format(getString(R.string.full_name), user.getFirstName(), user.getLastName()));
            headerEmail.setText(user.getEmail());
        } catch (UserDetailsNotPresent userDetailsNotPresent) {
            Crashlytics.logException(userDetailsNotPresent);
            userDetailsNotPresent.printStackTrace();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
     //   Debug.stopMethodTracing();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_user) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(EditUserActivity.getEditUserIntent(MainActivity.this));

                }
            }, 200);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onNoticeSelected(Notice item) {
       // Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
    }

}
