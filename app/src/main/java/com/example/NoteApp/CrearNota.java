package com.example.NoteApp;

import static com.example.NoteApp.R.string.notanuevatitulo;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CrearNota extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    Toolbar toolbar;
    EditText noteTitle, detallesnota;
    Calendar c;
    String fechadehoy;
    String horactual;
    private View iv_mic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(notanuevatitulo));

        detallesnota = findViewById(R.id.noteDetails);
        noteTitle = findViewById(R.id.noteTitle);



        iv_mic = findViewById(R.id.iv_mic);
       

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast
                            .makeText(CrearNota.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    Objects.requireNonNull(getSupportActionBar()).setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Agregar fecha y hora
        c = Calendar.getInstance();
        fechadehoy = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
        Log.d("FECHA", "Fecha: "+ fechadehoy);
        horactual = pad(c.get(Calendar.HOUR_OF_DAY))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("HORA", "Hora: "+ horactual);

    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            if(noteTitle.getText().length() != 0){
                Nota nota = new Nota(noteTitle.getText().toString(), detallesnota.getText().toString(), fechadehoy, horactual);
                Basededatos sDB = new Basededatos(this);
                long id = sDB.addNote(nota);
                Nota check = sDB.getNota(id);
                Log.d("añadido", "Nota: "+ id + " -> Titulo:" + check.getTitulo()+" Fecha: "+ check.getFecha());
                onBackPressed();

                Toast.makeText(this, R.string.toastguardarnota, Toast.LENGTH_SHORT).show();
            }else {
                noteTitle.setError(getString(R.string.titulovacio));
            }

        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, R.string.toastcancelarnota, Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                detallesnota.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }
}
  
   