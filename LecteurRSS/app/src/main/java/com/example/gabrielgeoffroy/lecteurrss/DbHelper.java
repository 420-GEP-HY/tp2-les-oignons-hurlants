package com.example.gabrielgeoffroy.lecteurrss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.RowId;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper instance;
    private Context contexte;

    public DbHelper(Context context) {
        super(context, "DATA",null, 1);
        this.contexte = context;
    }

    public static synchronized DbHelper getInstance(Context context){
        if (instance == null){
            instance = new DbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CHAINES(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITRE TEXT NOT NULL, DESCRIPTION TEXT, LIEN TEXT, URLIMAGE TEXT)");
        db.execSQL("CREATE TABLE ARTICLES(ID INTEGER PRIMARY KEY AUTOINCREMENT, IDCHAINE INTEGER NOT NULL, TITRE TEXT NOT NULL, DESCRIPTION TEXT, MEDIALINK TEXT, MEDIATYPE TEXT, GUID TEXT, FOREIGN KEY(IDCHAINE) REFERENCES CHAINES(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void addData(String Titre, String Description, String Lien, String urlImage, List<Article> articles){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues values = new ContentValues();
            values.put("TITRE",Titre);
            values.put("DESCRIPTION",Description);
            values.put("LIEN",Lien);
            values.put("URLIMAGE", urlImage);
            db.insertOrThrow("CHAINES",null,values);
            Cursor c = db.rawQuery("SELECT * FROM ARTICLES",null);
            int idChaine = c.getCount();
            for (Article a : articles){
                ContentValues valuesArticles = new ContentValues();
                valuesArticles.put("IDCHAINE",idChaine);
                valuesArticles.put("TITRE",a.titre);
                valuesArticles.put("DESCRIPTION",a.description);
                valuesArticles.put("MEDIALINK",a.mediaLink);
                valuesArticles.put("MEDIATYPE",a.mediaType);
                valuesArticles.put("GUID",a.guid);
                db.insertOrThrow("ARTICLES",null,valuesArticles);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }catch(Exception e){
            db.endTransaction();
        }
    }

    public List<Chaine> getAllData(){
        List<Chaine> list = new ArrayList<Chaine>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cChaines = db.rawQuery("SELECT * FROM CHAINES", null);
        Cursor cArticles = db.rawQuery("SELECT * FROM ARTICLES",null);

        while(cChaines.moveToNext()){
            String titre =  cChaines.getString(1);
            String description = cChaines.getString(2);
            String lien = cChaines.getString(3);
            String urlImage = cChaines.getString(4);
            int id = cChaines.getInt(5);
            List<Article> listArticle = new ArrayList<Article>();
            while(cArticles.moveToNext()){
                if (cArticles.getString(1) == Integer.toString(id)){
                    String titreA = cArticles.getString(2);
                    String descriptionA = cArticles.getString(3);
                    String mediaLink = cArticles.getString(4);
                    String mediaType = cArticles.getString(5);
                    String guid = cArticles.getString(6);
                    Article a = new Article(titreA,descriptionA,mediaLink,mediaType,guid);
                    listArticle.add(a);
                }
            }
            Chaine c = new Chaine(titre,description,lien,urlImage,listArticle);
            list.add(c);
        }
        return list;
    }
}
