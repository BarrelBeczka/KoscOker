package com.example.koscoker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BazaDanych extends SQLiteOpenHelper {

    // Deklaracja wersji, nazwy bazy danych oraz tabeli
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "koscoker_db";
    private static final String TABLE_WYNIKI = "wyniki";
    
    // Deklaracja kolumn tabeli
    private static final String KEY_ID = "id";
    private static final String KEY_PUNKTY_G1 = "punkty_g1";
    private static final String KEY_PUNKTY_G2 = "punkty_g2";
    private static final String KEY_DATA = "data";


    public BazaDanych(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tworzenie tabel bazy danych. Wywoływana tylko raz, gdy bazy jeszcze nie ma
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WYNIKI_TABLE = "CREATE TABLE " + TABLE_WYNIKI + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PUNKTY_G1 + " INTEGER,"
                + KEY_PUNKTY_G2 + " INTEGER,"
                + KEY_DATA + " TEXT" + ")";
        db.execSQL(CREATE_WYNIKI_TABLE);
    }

    // Wywoływana przy zmianie wersji bazy (DATABASE_VERSION)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WYNIKI);
        onCreate(db);
    }

    // Metoda dodająca nowy wynik do bazy
    public void dodajWynik(int punktyG1, int punktyG2, String data) {
        SQLiteDatabase db = this.getWritableDatabase(); // Pobierz bazę do zapisu
        
        ContentValues values = new ContentValues(); // Kontener na dane
        values.put(KEY_PUNKTY_G1, punktyG1);
        values.put(KEY_PUNKTY_G2, punktyG2);
        values.put(KEY_DATA, data);
        
        // Wstawienie wiersza do tabeli
        db.insert(TABLE_WYNIKI, null, values);
        db.close();
    }

    // Pobiera historię wszystkich gier
    public List<Wynik> pobierzWszystkieWyniki() {
        List<Wynik> listaWynikow = new ArrayList<>();
        // Zapytanie SQL: Wybierz wszystko, posortuj malejąco po ID
        String selectQuery = "SELECT * FROM " + TABLE_WYNIKI + " ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase(); // Pobierz bazę do odczytu
        Cursor cursor = db.rawQuery(selectQuery, null); // Kursor to wskaźnik na wyniki zapytania

        // Przechodzimy przez wszystkie wiersze wyniku
        if (cursor.moveToFirst()) {
            do {
                // Odczyt kolumn wg indeksów (0=id, 1=g1, 2=g2, 3=data)
                int id = cursor.getInt(0);
                int p1 = cursor.getInt(1);
                int p2 = cursor.getInt(2);
                String data = cursor.getString(3);
                
                // Dodajemy obiekt do listy
                listaWynikow.add(new Wynik(id, p1, p2, data));
            } while (cursor.moveToNext());
        }
        
        cursor.close(); // Zamykamy kursor
        db.close();
        return listaWynikow;
    }
}
