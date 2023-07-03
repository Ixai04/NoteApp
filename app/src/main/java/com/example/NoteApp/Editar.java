package com.example.NoteApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class Editar extends AppCompatActivity {
    Toolbar toolbar;
    EditText nTitulo, nContenido;
    Calendar c;
    String fechadehoy;
    String horactual;
    long nId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        nId = i.getLongExtra("ID",0);
        Basededatos db = new Basededatos(this);
        Nota nota = db.getNota(nId);

        final String titulo = nota.getTitulo();
        String content = nota.getContenido();
        nTitulo = findViewById(R.id.noteTitle);
        nContenido = findViewById(R.id.noteDetails);
        nTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(titulo);
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


        nTitulo.setText(titulo);
        nContenido.setText(content);

        // Fecha y hora actual
        c = Calendar.getInstance();
        fechadehoy = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.d("FECHA", "Fecha: "+ fechadehoy);
        horactual = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("HORA", "Hora: "+ horactual);
    }


    private String pad(int tiempo) {
        if(tiempo < 10)
            return "0"+tiempo;
        return String.valueOf(tiempo);

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
            Nota nota = new Nota(nId, nTitulo.getText().toString(), nContenido.getText().toString(), fechadehoy, horactual);
            Log.d("EDITADO", "editado: antes de guardar la id -> " + nota.getId());
            Basededatos sDB = new Basededatos(getApplicationContext());
            long id = sDB.editarNota(nota);
            Log.d("EDITADO", "EDITAR: id " + id);
            goToMain();
            Toast.makeText(this,R.string.toastnotaeditada, Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this,R.string.toastcancelarnota, Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }


}
