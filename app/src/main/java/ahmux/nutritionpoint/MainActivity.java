package ahmux.nutritionpoint;

/* ######################################## */
/*  Nutrition Point App developed by Ahmux  */
/* ##### Ahmux.freelander@gmail.com ######  */
/* ######################################## */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements FragmentsCommunicator, NavigationView.OnNavigationItemSelectedListener {

    String fontPath = "fonts/Questv1-Bold.otf";
    Toast doubleBackToast;
    TextView  tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv1 = (TextView) findViewById(R.id.textView1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadLocale();
        saveData();
        waterReminder();

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        tv1.setTypeface(tf);

        doubleBackToast = Toast.makeText(this,
                R.string.doubleBackToast, Toast.LENGTH_LONG);
    }

    /* ######### Communicate with Fragments ######################################################## */
    @Override
    //data1 is key, data2 is value
    public void respond(String data1, int data2) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (data1){
            case("male"):
                editor.putString("gender", getString(R.string.Male));
                loadFragment(new Fragment2(), R.id.fullparentFramelayout);
                break;
            case("female"):
                editor.putString("gender", getString(R.string.Female));
                loadFragment(new Fragment2(),R.id.fullparentFramelayout);
                break;
            case("no activity"):
                editor.putString("physical_activity", "no activity");
                loadFragment(new Fragment3(), R.id.fullparentFramelayout);
                break;
            case("walking"):
                editor.putString("physical_activity", "walking");
                loadFragment(new Fragment3(), R.id.fullparentFramelayout);
                break;
            case("exercize 1-2 days"):
                loadFragment(new Fragment3(),R.id.fullparentFramelayout);
                break;
            case("exercize 3-5 days"):
                loadFragment(new Fragment3(), R.id.fullparentFramelayout);
                break;
            case("everyday"):
                loadFragment(new Fragment3(), R.id.fullparentFramelayout);
                break;
            case("age"):
                editor.putString("age", String.valueOf(data2));
                break;
            case("weight"):
                editor.putString("weight", String.valueOf(data2));
                break;
            case("height"):
                editor.putString("height", String.valueOf(data2));
                break;
            case("analyze"):
                loadFragment(new Fragment4(),R.id.fullparentFramelayout);
                break;
            case("ok"):
                Intent endIntent = new Intent(this, MainActivity .class);
                //set flags to clear back stack
                endIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(endIntent);
                break;
            case("clear"):
                Toast.makeText(this, "DATA CLEARED", Toast.LENGTH_SHORT).show();
                //clear data from shared preferences
                editor.clear();
                Intent clearIntent = new Intent(this, MainActivity .class);
                //clear back stack
                clearIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(clearIntent);
                break;
            case("show"):
                loadFragment(new Fragment4(),R.id.fullparentFramelayout);
                break;
        }

        editor.commit();
    }
    //Fragments loading method
    private void loadFragment(Fragment f, int layoutId) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

        ft.replace(layoutId, f);
        ft.addToBackStack(null);  // press back to go to previous fragment
        ft.commit();
    }
    /* ############################################################################################# */


    /* #############Saving data in Shared Preferences############################################### */
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String gender = sharedPreferences.getString("gender", "N/A");
        String activity = sharedPreferences.getString("physical_activity", "N/A");
        String age = sharedPreferences.getString("age", "N/A");
        String weight = sharedPreferences.getString("weight", "N/A");
        String height = sharedPreferences.getString("height", "N/A");
        //choose start fraagment or End!
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        if (age.matches("N/A")) {
            ft.replace(R.id.mainFrameLayout, new Fragment1());
            ft.commit();
        } else {
            ft.replace(R.id.mainFrameLayout, new Fragment5());
            ft.commit();
        }
    }
    //Send data to Fragments
    public String getMyData(String s) {
        String myString = s;
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        switch (s){
            case("gender"):
                myString = sharedPreferences.getString("gender", null);
                break;
            case("age"):
                myString = sharedPreferences.getString("age", null);
                break;
            case("weight"):
                myString = sharedPreferences.getString("weight", null);
                break;
            case("height"):
                myString = sharedPreferences.getString("height", null);
                break;
        }

        return myString;
    }
    /* ############################################################################################# */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_about) {
            loadFragment(new AboutFragment(), R.id.fullparentFramelayout);
            return true;
        }
        else if (id == R.id.action_language){
            String langPref = "Language";
            SharedPreferences sharedPreferences = getSharedPreferences("SettingsData", Activity.MODE_PRIVATE);
            String language = sharedPreferences.getString(langPref, "en");
            if(language.matches("ar")){
                changeLang("en");
            }else{
                changeLang("ar");
            }
            Intent refresh = new Intent(this, MainActivity.class);
            refresh.setFlags(refresh.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(refresh);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_reminder) {
            Intent RemindersIntent = new Intent(this, RemindersActivity.class);
            startActivity(RemindersIntent);

        } else if (id == R.id.nav_Recipes) {

        } else if (id == R.id.nav_calculate) {
            Intent ApiIntent = new Intent(this, ApiActivity.class);
            startActivity(ApiIntent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_facebook) {
            try {
                Intent fbIntent = new Intent(Intent.ACTION_VIEW);
                fbIntent.setData(Uri.parse("https://www.facebook.com/supportnutritionpoit"));
                startActivity(fbIntent);
            } catch (Exception e) {
            }
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    /* ###### Water Reminder ######################################################################3 */
    public void waterReminder(){
        SharedPreferences sharedPreferences = getSharedPreferences("SettingsData", Activity.MODE_PRIVATE);
        int t = sharedPreferences.getInt("water_delay", 1000);
        if(sharedPreferences.getString("water_reminder", "false").matches("true")){
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),t*60*1000, pendingIntent);
        }
    }
    /* ############################################################################################ */


    /* #########Change Language and restart Main Activity########################################### */
    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences sharedPreferences = getSharedPreferences("SettingsData", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString(langPref, "");
        changeLang(language);
    }
    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang, "MR");
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }
    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences sharedPreferences = getSharedPreferences("SettingsData", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }
    /* ############################################################################################ */


    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        /* ##### press back twice to exit method #################################// */
        else if (backStackEntryCount == 0) {
            if (doubleBackToast.getView().isShown()) {
                if (doubleBackToast != null) {
                    doubleBackToast.cancel();
                    super.onBackPressed();
                }
            }
            doubleBackToast.show();
        }else {
            super.onBackPressed();
        }
        /* ######################################################################// */
    }
}
