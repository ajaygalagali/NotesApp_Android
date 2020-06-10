package com.astro.ajnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditNotes extends AppCompatActivity {
    EditText editNotes;
    Intent fromList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        editNotes = findViewById(R.id.editNotes);
        fromList = getIntent();
        editNotes.setText("");
        if(fromList.getStringExtra("clickedNote") != null){
            String clickedNote = fromList.getStringExtra("clickedNote");
            editNotes.setText(clickedNote);
            clickedNote=null;
        }
    }

    public void saveClicked(View view){
        String notes = String.valueOf(editNotes.getText());
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.astro.ajnotes", Context.MODE_PRIVATE);
        int position = fromList.getIntExtra("clickedNotePosition",-1);
        if(position > -1){
            MainActivity.arrayList.remove(position);
            MainActivity.arrayList.add(position,notes);
        }else {
            MainActivity.arrayList.add(notes);
        }
            try {
                sharedPreferences.edit().putString("note", ObjectSerializer.serialize(MainActivity.arrayList)).apply();

            } catch (Exception e) {
                e.printStackTrace();
//                Log.i("editnpotes", "Failed");
            }


            MainActivity.arrayAdapter.notifyDataSetChanged();

//            Intent goToHome = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(goToHome);
            finish();




    }
}
