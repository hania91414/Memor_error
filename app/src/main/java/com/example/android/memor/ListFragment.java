package com.example.android.memor;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.memor.data.Contract;
import com.example.android.memor.data.SQLhelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    private RecyclerView recyclerView;
    private mRecyler recyler;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SQLhelper sqLhelper;
    private int count = 1;


    public ListFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyler);
        sqLhelper = new SQLhelper(getActivity());
        db = sqLhelper.getWritableDatabase();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int tag = (int) viewHolder.itemView.getTag();
                String arra = Integer.toString(tag);
                String[] id = new String[]{arra};
                int position = db.delete(Contract.WordsContainer.TABLE_NAME, Contract.WordsContainer._ID + "=?", id);
                if (position > 0) {
                    recyler.notifyItemChanged(viewHolder.getAdapterPosition());

                }


                // MainActivity.scroolRecycler(recyclerView);

            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    public void takeCursor(Cursor cursor, Context context) {
        recyler = new mRecyler(context);
        recyler.swapCursor(cursor);
    }



}
