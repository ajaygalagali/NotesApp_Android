package com.astro.ajnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button button;
    int NoteToDel;

    Intent goToEditNotes;

    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override*/
    /*public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.setting:
                Log.i("Item 1","You clicked Setting");
                textView.setText("Setting");

                break;


            case R.id.help:
                Log.i("Item 2","You clicked help");
                textView.setText("Help");
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        goToEditNotes = new Intent(getApplicationContext(),EditNotes.class);



        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.astro.ajnotes", Context.MODE_PRIVATE);
        arrayList.clear();
        
        try {
            arrayList = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("note",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
//            Log.i("Failed","FFFFFFFFFFFFFFFFFFFF");
        }

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToEditNotes.putExtra("clickedNote",String.valueOf(arrayList.get(position)));
                goToEditNotes.putExtra("clickedNotePosition",position);

                startActivity(goToEditNotes);


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int positio, long id) {

                NoteToDel = positio;
                /*arrayList.remove(positio);
                arrayAdapter.notifyDataSetChanged();
                try {
                    sharedPreferences.edit().putString("note", ObjectSerializer.serialize(arrayList)).apply();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("deleteNotes", "Failed");
                }*/

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Note!!")
                        .setMessage("Are you sure want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(NoteToDel);
                                arrayAdapter.notifyDataSetChanged();
                                try {
                                    sharedPreferences.edit().putString("note", ObjectSerializer.serialize(arrayList)).apply();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.i("editnpotes", "Failed");
                                }
                            }

                        })
                        .setNegativeButton("No",null).show();







                return true;
            }
        });








    }

    public void addClicked(View view){
        goToEditNotes.putExtra("clickedNote","");
        goToEditNotes.putExtra("clickedNotePosition","");
        startActivity(goToEditNotes);
    }
}
