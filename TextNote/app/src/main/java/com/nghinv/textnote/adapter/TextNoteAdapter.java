package com.nghinv.textnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nghinv.textnote.R;
import com.nghinv.textnote.data.TextNote;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by NghiNV on 12/04/2017.
 */

public class TextNoteAdapter extends ArrayAdapter<TextNote> {

    Context context;
    List<TextNote> noteList;
    ArrayAdapter<TextNote> noteAdapter;
    int layoutId;
    int textViewResourceId;


    static class ViewHolder {
        TextNote txtTitle;
        TextView txtNote;
    }

    public TextNoteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public TextNoteAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public TextNoteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull TextNote[] objects) {
        super(context, resource, objects);
    }

    public TextNoteAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull TextNote[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public TextNoteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TextNote> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutId = resource;
        this.noteList = objects;
    }

    public TextNoteAdapter(@NonNull Context context, @NonNull List<TextNote> notes) {
        super(context,0,  notes);
        this.context = context;
        this.noteList = notes;
    }

    public TextNoteAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<TextNote> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.layoutId = resource;
        this.textViewResourceId = textViewResourceId;
        this.noteList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        TextNote note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_note, parent, false);
        }
            TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            TextView txtNote = (TextView) convertView.findViewById(R.id.txtNote);
            txtNote.setText(note.getNote());
            txtTitle.setText(note.getTitle());

        return convertView;
    }
}
