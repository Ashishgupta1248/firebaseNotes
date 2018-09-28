package com.example.ashish.firebasenotes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class add_update extends AppCompatActivity {

    EditText notes_text;
    EditText notesTitle;
    String id;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        notes_text = findViewById(R.id.notes_text);
        notesTitle = findViewById(R.id.notes_title);
        firebaseAuth=FirebaseAuth.getInstance();
        id = getIntent().getStringExtra("note_id");
        //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        notes_text.setText(getIntent().getStringExtra("text"));
        notesTitle.setText(getIntent().getStringExtra("title"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.save:
                Intent i = new Intent();
                i.putExtra("notes_id",id);
                i.putExtra("notes_text",notes_text.getText().toString());
                i.putExtra("notes_title",notesTitle.getText().toString());
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

