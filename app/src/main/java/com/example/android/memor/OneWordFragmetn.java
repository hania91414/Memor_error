package com.example.android.memor;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneWordFragmetn extends Fragment {

    private RecyclerView recyclerView;
    private secondRecycler secondRecycler;

    public OneWordFragmetn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_one_word_fragmetn, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewOneWordpogf);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(secondRecycler);
        return view;
    }

    public void SecondCursor(Cursor cursor, Context context) {
        secondRecycler = new secondRecycler(context);
        secondRecycler.swapCursro(cursor);
    }


}

