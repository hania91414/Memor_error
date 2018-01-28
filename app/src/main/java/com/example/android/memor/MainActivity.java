package com.example.android.memor;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.memor.data.Contract;
import com.example.android.memor.data.SQLhelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SQLhelper sqLhelper;
    private SQLiteDatabase db;
    private int LOADE_ID = 111;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ListFragment listFragment;
    private OneWordFragmetn oneWordFragmetn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app__bar_layout);

        //        initialize The DataBase
        sqLhelper = new SQLhelper(this);
        //        get access to write/read items to/from Database
        db = sqLhelper.getWritableDatabase();

        listFragment = new ListFragment();
        oneWordFragmetn = new OneWordFragmetn();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerr, new addFragmetn()).commit();


        getSupportLoaderManager().initLoader(LOADE_ID, null, this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        getSupportLoaderManager().restartLoader(LOADE_ID, null, MainActivity.this);

                        switch (item.getItemId()) {
                            case R.id.action_add:
                                fragmentTransaction.replace(R.id.containerr, new addFragmetn()).commit();
                                return true;

                            case R.id.action_my_words:
                                fragmentTransaction.replace(R.id.containerr, listFragment).commit();
                                break;
                            case R.id.scrollWords:
                                fragmentTransaction.replace(R.id.containerr, oneWordFragmetn).commit();
                                break;


                        }
                        return false;
                    }
                });


    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADE_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOADE_ID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor cursor;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (cursor != null) {
                    deliverResult(cursor);
                } else {
                    forceLoad();
                }

            }

            @Override
            public Cursor loadInBackground() {
                cursor = db.query(Contract.WordsContainer.TABLE_NAME, null, null, null, null, null, null, null);
                return cursor;
            }

            @Override
            public void deliverResult(Cursor data) {
                cursor = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //send cursor to fragment
        listFragment.takeCursor(data, this);
        //send cursors to fragment
        oneWordFragmetn.SecondCursor(data, this);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}