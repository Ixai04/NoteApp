package com.example.NoteApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Detalles extends AppCompatActivity {
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        

        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        Basededatos db = new Basededatos(this);
        Nota nota = db.getNota(id);
        Objects.requireNonNull(getSupportActionBar()).setTitle(nota.getTitulo());
        TextView details = findViewById(R.id.descnota);
        details.setText(nota.getContenido());
        details.setMovementMethod(new ScrollingMovementMethod());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Basededatos db1 = new Basededatos(getApplicationContext());
            db1.borrarnota(id);
            Toast.makeText(getApplicationContext(),R.string.toastborrarnota,Toast.LENGTH_SHORT).show();
            goToMain();
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit){
            Intent i = new Intent(this, Editar.class);
            i.putExtra("ID",id);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
