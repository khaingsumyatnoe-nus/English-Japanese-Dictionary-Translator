package com.example.user.myapplication3;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MenuItem menuSetting;
    DatabaseAssets databaseAssets;
    DictionaryFragment dictionaryFragment;
    Japan_Fragment japan_fragment;
    MyJpAdapter jpadapter;
    SetenceAnalyzerFragment setenceAnalyzerFragment;
    DailyJapaneseFragment dailyJapaneseFragment;
    SearchView search;
    AboutFragment aboutFragment; ArrayList<Eng_To_Jp> eng_to_jp;
    //DictionaryFragment2 dictionaryFragment2;

    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Databases
        databaseAssets = DatabaseAssets.getInstance(this);
        databaseAssets.open();

         eng_to_jp=databaseAssets.getAll();

       // databaseAssets.close();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        search=findViewById(R.id.edit_search);
      //adapter = new MyAdapter(this, eng_to_jp);
//
//
//
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                dictionaryFragment.filterValue(query.toString());
//                //FILTER AS YOU TYPE
//                return false;
//
//
//
//            }
//        });
//        jpadapter=new MyJpAdapter(this,eng_to_jp);
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                japan_fragment.filterValue(s.toString());
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });

        dictionaryFragment = new DictionaryFragment();
        setenceAnalyzerFragment =new SetenceAnalyzerFragment();
        dailyJapaneseFragment=new DailyJapaneseFragment();
        aboutFragment=new AboutFragment();
        japan_fragment=new Japan_Fragment();
        //dictionaryFragment2=new DictionaryFragment2();
        //detailFragment=new DetailFragment();
        gotoFragment(dictionaryFragment);

        dictionaryFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
                //gotoFragment(detailFragment);
            }
        });
//       detailFragment.setOnFragmentListener(new FragmentListener() {
//           @Override
//           public void onItemClick(String value) {
//               gotoFragment(dictionaryFragment);
//           }
//       });

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
        menuSetting=menu.findItem(R.id.action_settings);
        String id= Global.getState(this,"dic_type");
        if(id!=null)
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Global.saveState(this,"dic_type",String.valueOf(id));

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eng_jp) {
            menuSetting.setIcon(getResources().getDrawable(R.drawable.jpflag));
            gotoFragment(dictionaryFragment);
            Toast.makeText(this,"Dictionary Eng Fragment",Toast.LENGTH_SHORT).show();
            //SearchView search=findViewById(R.id.edit_search);
            //adapter = new MyAdapter(this, eng_to_jp);



            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    dictionaryFragment.filterValue(query.toString());
                    Log.d("ONQ---------",query.toString());
                    //FILTER AS YOU TYPE
                    return false;



                }
            });


        }
        else if(id== R.id.action_jp_eng){
            menuSetting.setIcon(getResources().getDrawable(R.drawable.engflag));
            gotoFragment(japan_fragment);
            Toast.makeText(this,"Dictionary Japan Fragment",Toast.LENGTH_SHORT).show();
            //jpadapter=new MyJpAdapter(this,eng_to_jp);
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    japan_fragment.filterValue(s.toString());
                    return false;
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id==R.id.nav_dic){


            gotoFragment(dictionaryFragment);


        }

        else if(id==R.id.nav_translator){
            gotoFragment(setenceAnalyzerFragment);
        }
//        else if(id==R.id.nav_japanese){
//            gotoFragment(dailyJapaneseFragment);
//        }
//        else if(id==R.id.nav_about){
//            gotoFragment(aboutFragment);
//        }
        else
        {
            gotoFragment(dictionaryFragment);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void gotoFragment(Fragment fragment){

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        if(!isTop)
//            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
