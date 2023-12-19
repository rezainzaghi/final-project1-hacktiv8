package com.hacktiv8.finalproject1;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> noteList;

    private Context context;

    public NoteAdapter(Context context, List<Note> noteList){
        this.context = context;
        this.noteList = noteList;
    }

    public NoteAdapter(Context context, List<Note> noteList, editNoteListener editListener, deleteNoteListener deleteListener){
        this.context = context;
        this.noteList = noteList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.TaskTitleTV.setText(note.getTaskName());
        holder.DescriptionTV.setText(note.getTaskDescription());
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView DescriptionTV, TaskTitleTV;
        private ImageButton editButton;
        private ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TaskTitleTV = itemView.findViewById(R.id.task_title_tv);
            DescriptionTV = itemView.findViewById(R.id.task_description_tv );
            editButton = itemView.findViewById(R.id.button_edit);
            deleteButton = itemView.findViewById(R.id.button_delete);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editListener.onEditNote(getAdapterPosition());
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.onDeleteNote(getAdapterPosition());
                }
            });

        }
    }

    public interface editNoteListener{
        void onEditNote(int position);
    }

    public interface deleteNoteListener{
        void onDeleteNote(int position);
    }

    private editNoteListener editListener;

    private deleteNoteListener deleteListener;
}
