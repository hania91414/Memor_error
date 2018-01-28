package com.example.android.memor;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Krzys on 14.01.2018.
 */

public class secondRecycler extends RecyclerView.Adapter<secondRecycler.ViewHolderr> {
    private Context ct;
    private Cursor cursor;
    private fetchDataCursor fetchDataCursor;

    public secondRecycler(Context context) {
        ct = context;
        fetchDataCursor = new fetchDataCursor();

    }

    @Override
    public ViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.main_layout_horizontal, parent, false);

        return new ViewHolderr(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderr holder, int position) {

        String word = fetchDataCursor.getWord(position,cursor);
        String translateed = fetchDataCursor.getTranslated(position,cursor);
        holder.BindToSecond(word, translateed);


    }
    public Cursor swapCursro(Cursor c)
    {
        if(cursor == c)
        {
            return null;
        }
        Cursor temp = c;
        cursor = c;
        if(c !=null)
        {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    @Override
    public int getItemCount() {

        if(cursor == null)
        {
            return 0;
        }

        return cursor.getCount();
    }

    public class ViewHolderr extends RecyclerView.ViewHolder {
        private TextView secondView;
        private String translated;
        private String word;
        private boolean check;

        public ViewHolderr(View itemView) {
            super(itemView);
            secondView = (TextView) itemView.findViewById(R.id.secondRecyclerTextView);
            secondView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (check) {
                        check = false;
                        secondView.setText(word);

                    } else {
                        check =true ;
                        secondView.setText(translated);
                    }
                }
            });

        }

        void BindToSecond(String word, String translated) {
            secondView.setText(word);
            this.word = word;
            this.translated = translated;
        }
    }
}
