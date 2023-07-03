package com.example.NoteApp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Basededatos extends SQLiteOpenHelper {

    // Declaracion de valores
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "BBDD";
    private static final String TABLE_NAME = "Tabla";

    public Basededatos(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // Declaracion de nombres de las columnas
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "titulo";
    private static final String KEY_CONTENT = "contenido";
    private static final String KEY_DATE = "fecha";
    private static final String KEY_TIME = "hora";





    // Creacion de tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
         String createDb = "CREATE TABLE "+TABLE_NAME+" ("+
                 KEY_ID+" INTEGER PRIMARY KEY,"+
                 KEY_TITLE+" TEXT,"+
                 KEY_CONTENT+" TEXT,"+
                 KEY_DATE+" TEXT,"+
                 KEY_TIME+" TEXT"
                 +" )";
         db.execSQL(createDb);
    }

    // Checkear version de la BBDD

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addNote(Nota nota){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_TITLE, nota.getTitulo());
        v.put(KEY_CONTENT, nota.getContenido());
        v.put(KEY_DATE, nota.getFecha());
        v.put(KEY_TIME, nota.getHora());

        // Insertar datos en la BBDD

        return db.insert(TABLE_NAME,null,v);
    }

    public Nota getNota(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
       @SuppressLint("Recycle") Cursor cursor=  db.query(TABLE_NAME,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return new Nota(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    public List<Nota> gettodaslasNotas(){
        List<Nota> todaslasNotas = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Nota nota = new Nota();
                nota.setId(Long.parseLong(cursor.getString(0)));
                nota.setTitulo(cursor.getString(1));
                nota.setContenido(cursor.getString(2));
                nota.setFecha(cursor.getString(3));
                nota.setHora(cursor.getString(4));
                todaslasNotas.add(nota);
            }while (cursor.moveToNext());
        }

        return todaslasNotas;

    }

    public int editarNota(Nota nota){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Editado", "Titulo Editado: -> "+ nota.getTitulo() + "\n ID -> "+ nota.getId());
        c.put(KEY_TITLE, nota.getTitulo());
        c.put(KEY_CONTENT, nota.getContenido());
        c.put(KEY_DATE, nota.getFecha());
        c.put(KEY_TIME, nota.getHora());
        return db.update(TABLE_NAME,c,KEY_ID+"=?",new String[]{String.valueOf(nota.getId())});
    }



    void borrarnota(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }





}
