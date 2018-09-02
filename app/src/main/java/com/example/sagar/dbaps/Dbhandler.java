package com.example.sagar.dbaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Dbhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactManager";
    private static final String TABLE_CONTACT = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";



    //, String name, SQLiteDatabase.CursorFactory factory, int version
    public Dbhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE "+ TABLE_CONTACT+
                "(" +KEY_ID + " INTEGER PRIMARY KEY , "+
                        KEY_NAME+" TEXT ,"+
                        KEY_PH_NO + " TEXT );"
                ;
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACT);
        onCreate(db);
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME , contact.getName());
        values.put(KEY_PH_NO , contact.getPhone());
//        db.rawQuery("insert into contacts values("+contact.getName()+","+contact.getPhone()+")",null);

        db.insert(TABLE_CONTACT , null,values);
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CONTACT,
                new String[]{KEY_ID,KEY_NAME,KEY_PH_NO },
                KEY_ID +"=?" ,
                new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Contact contact = new Contact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2)
        );

        cursor.close();
        db.close();
        return  contact;
    }

    public List<Contact> getAll(){
        List<Contact> contactsList = new ArrayList<Contact>();
        SQLiteDatabase db = this.getReadableDatabase();
        String que = "SELECT * FROM "+ TABLE_CONTACT;
        Cursor cursor = db.rawQuery(que, null);

        if (cursor.moveToFirst()){
            do{
                Contact contact = new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );

                contactsList.add(contact);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactsList;
    }

    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhone());

        // updating row
        return db.update(TABLE_CONTACT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }

    // Deleting single contact
    public int deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int k = db.delete(TABLE_CONTACT, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
        return k;
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int val = cursor.getCount();

        cursor.close();
        db.close();

        // return count
        return val;
    }

    public int removeall(){
        SQLiteDatabase db = this.getWritableDatabase();
        int val = db.delete(TABLE_CONTACT , String.valueOf(1),null);
        db.close();
        return val;
    }

}
