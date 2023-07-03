package com.example.NoteApp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adaptador adaptador;
    TextView noItemText;
    Basededatos basededatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        noItemText = findViewById(R.id.noItemText);
         basededatos = new Basededatos(this);
        List<Nota> todaslasNotas = basededatos.gettodaslasNotas();
        recyclerView = findViewById(R.id.allNotesList);

        if(todaslasNotas.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(todaslasNotas);
        }

    }

    private void displayList(List<Nota> todaslasNotas) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new Adaptador(this, todaslasNotas);
        recyclerView.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Toast.makeText(this, R.string.toastnotanueva, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, CrearNota.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Nota> gettodaslasNotas = basededatos.gettodaslasNotas();
        if(gettodaslasNotas.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(gettodaslasNotas);
        }


    }

}
