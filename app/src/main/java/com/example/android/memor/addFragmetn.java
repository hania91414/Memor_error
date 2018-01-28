package com.example.android.memor;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.memor.data.Contract;
import com.example.android.memor.data.SQLhelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class addFragmetn extends Fragment {


    private EditText word, translate;
    private SQLiteDatabase db;
    private SQLhelper sqLhelper;
    private Button button;

    public addFragmetn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_fragmetn, container, false);
        word = (EditText)view.findViewById(R.id.word);
        translate = (EditText)view.findViewById(R.id.translate);
        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonForTake();
            }
        });
        sqLhelper = new SQLhelper(getActivity());
        db = sqLhelper.getWritableDatabase();



        return view;
    }
    public void ButtonForTake() {
        String Word = word.getText().toString();
        String translated = translate.getText().toString();
        if (!TextUtils.isEmpty(Word) && !TextUtils.isEmpty(translated)) {

            long id = insert(Word, translated);
            if (id == -1) {
                Toast.makeText(getActivity(), "Occur Some Problem with Inserting", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "For now it's all ok" + id, Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getActivity(), "all the fields should be used", Toast.LENGTH_SHORT).show();
        }
        word.getText().clear();
        translate.getText().clear();
    }

    private long insert(String word, String translate) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.WordsContainer._WORD, word);
        contentValues.put(Contract.WordsContainer._TRANSLATED, translate);
        return db.insert(Contract.WordsContainer.TABLE_NAME, null, contentValues);
    }

}

