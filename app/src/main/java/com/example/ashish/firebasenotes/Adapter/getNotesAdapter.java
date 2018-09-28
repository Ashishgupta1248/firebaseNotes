package com.example.ashish.firebasenotes.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashish.firebasenotes.Model.NotesData;
import com.example.ashish.firebasenotes.R;

import java.util.ArrayList;
import java.util.List;

public class getNotesAdapter extends RecyclerView.Adapter<getNotesAdapter.MyViewHolder> {
    private Context mContext;
    private List<NotesData> notelist;
    private List<Integer> selectid=new ArrayList<>();


    public getNotesAdapter(Context mContext, List<NotesData> noticeLists) {
        this.mContext = mContext;
        this.notelist = noticeLists;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final NotesData notesData = notelist.get(position);
        holder.note_title.setText(notesData.getTitle());
        holder.note_text.setText(notesData.getNotes());
        holder.note_date.setText(notesData.getDATE());
       /* holder.item.setBackgroundColor(notesData.isSelected() ? Color.CYAN : Color.WHITE);
       holder.selectitem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               notesData.setSelected(!notesData.isSelected());
               holder.item.setBackgroundColor(notesData.isSelected() ? Color.CYAN : Color.WHITE);
           }
       });*/
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView note_title,
                note_text,
                note_date,selectitem;
        CardView note_cardview;
        View item;

        public MyViewHolder(View view) {
            super(view);
            item=view;
            note_title = view.findViewById(R.id.note_title);
            note_text = view.findViewById(R.id.note_text);
            note_date = view.findViewById(R.id.note_date);
            note_cardview = view.findViewById(R.id.note_card);
            //selectitem=view.findViewById(R.id.text_view);
        }

    }


    @Override
    public int getItemCount() {
        return notelist.size();
    }
}
