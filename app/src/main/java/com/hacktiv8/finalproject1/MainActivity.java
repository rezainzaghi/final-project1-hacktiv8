package com.hacktiv8.finalproject1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.editNoteListener, NoteAdapter.deleteNoteListener {

    private ImageButton addButton;
    private RecyclerView ListNoteRv;
    SQLiteDatabaseHandler db;
    private List<Note> noteList;
    private NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.add_data);
        ListNoteRv = findViewById(R.id.list_task);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForm(null, false);
            }
        });

        db = new SQLiteDatabaseHandler(this);

        loadDataNote();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ListNoteRv.setLayoutManager(layoutManager);

    }

    private void loadDataNote(){
        noteList = db.getAllCountry();
        noteAdapter = new NoteAdapter(this, noteList, this, this);
        ListNoteRv.setAdapter(noteAdapter);
    }

    private void showForm(Note noteEdit, boolean isEdit) {
        AlertDialog.Builder formBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.form_note, null);
        formBuilder.setView(view);

        AlertDialog popup = formBuilder.create();
        popup.show();

        EditText TaskName= view.findViewById(R.id.task_title_et);
        EditText DescriptionInput = view.findViewById(R.id.task_description_et);
        Button saveButton = view.findViewById(R.id.save_data);

        if (isEdit) {
            TaskName.setText(noteEdit.getTaskName());
            DescriptionInput.setText(noteEdit.getTaskDescription());
            saveButton.setText("UPDATE DATA");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TaskTitle = TaskName.getText().toString();
                String TaskDescription = DescriptionInput.getText().toString();

                if (!isEdit) {
                    // TAMBAHKAN DATA
                    Note note = new Note(TaskTitle, TaskDescription);
                    db.addNote(note);
                } else {
                    // PERBARUI DATA
                    noteEdit.setTaskName(TaskTitle);
                    noteEdit.setTaskDescription(TaskDescription);
                    db.updateNote(noteEdit);
                }

                loadDataNote();
                popup.dismiss();
            }
        });

    }


    @Override
    public void onEditNote(int position) {
        Note selectedNoteEdit = noteList.get(position);
        showForm(selectedNoteEdit, true);
    }

    @Override
    public void onDeleteNote(int position) {
        Note selectedNoteDelete = noteList.get(position);
        db.deleteNote(selectedNoteDelete);
        loadDataNote();
    }
}