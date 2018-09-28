package com.example.ashish.firebasenotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ashish.firebasenotes.Adapter.getNotesAdapter;
import com.example.ashish.firebasenotes.Helper.RecyclerTouchListener;
import com.example.ashish.firebasenotes.Model.NotesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    getNotesAdapter notesAdapter;
    List<NotesData> notesDataList;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    private static final int add_notes = 100;
    private static final int update_notes = 101;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesDataList = new ArrayList<>();
        notesAdapter = new getNotesAdapter(this,notesDataList);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.fab);
        progressBar=findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }else {
            uuid = user.getUid();
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(uuid).child("notes");
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);
        progressBar.setVisibility(View.VISIBLE);
        readData();
       /* private List<NotesData> getListData() {
            notesDataList = new ArrayList<>();
            for (int i = 1; i <= 25; i++) {
                notesDataList.add(new NotesData("TextView " + i));
            }
            return notesDataList;
        }*/
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, add_update.class).putExtra("note_id",0),add_notes);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                NotesData data = notesDataList.get(position);

                Intent intent = new Intent(MainActivity.this,add_update.class);
                intent.putExtra("title",data.getTitle());
                intent.putExtra("text",data.getNotes());
                intent.putExtra("note_id",data.getId());
                startActivityForResult(intent,update_notes);
            }

            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // Add the buttons
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        //Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        deleteNotes(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setTitle("Are you sure to delete?");
                dialog.setMessage(notesDataList.get(position).getTitle());
                dialog.show();

            }
        }));
    }

    private void deleteNotes(int position) {
        reference.child(notesDataList.get(position).getId()).removeValue();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case add_notes:
                    //notesDataList.clear();
                    String key = reference.push().getKey();
                    NotesData notesData = new NotesData(key,data.getStringExtra("notes_title"),data.getStringExtra("notes_text"));
                    reference.child(key).setValue(notesData);
                    break;
                case update_notes:

                    NotesData notesDataa = new NotesData(data.getStringExtra("notes_id"),data.getStringExtra("notes_title"),data.getStringExtra("notes_text"));
                    reference.child(data.getStringExtra("notes_id")).setValue(notesDataa);
                    //Toast.makeText(this, data.getStringExtra("notes_title"), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    private void readData() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot dataSnapshot,  String s) {
                Log.d("Log", "onChildAdded: "+dataSnapshot);
                NotesData notesmodel1 = dataSnapshot.getValue(NotesData.class);

                notesDataList.add(notesmodel1);
                notesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("Log", "onChildChanged: "+dataSnapshot);
                NotesData notesmodel1 = dataSnapshot.getValue(NotesData.class);
                int index = 0;
                //notesAdapter.notifyDataSetChanged();
                for (NotesData item:notesDataList) {
                    if (item.getId().equals(notesmodel1.getId())){
                        // Log.d("Log", "onChildChanged: "+index);
                        break;
                    }
                    index++;
                }
                //notesDataList.remove(index);
                notesDataList.set(index,notesmodel1);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Log", "onChildRemoved: "+dataSnapshot);
                NotesData notesmodel1 = dataSnapshot.getValue(NotesData.class);
                int index = 0;
                //notesAdapter.notifyDataSetChanged();
                for (NotesData item:notesDataList) {
                    if (item.getId().equals(notesmodel1.getId())){
                        // Log.d("Log", "onChildChanged: "+index);
                        break;
                    }
                    index++;
                }
                notesDataList.remove(index);
                //notesDataList.add(index,notesmodel1);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                //Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
                //onCreate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

