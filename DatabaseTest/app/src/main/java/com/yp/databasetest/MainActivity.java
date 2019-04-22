package com.yp.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createDataBtn, addBtn, queryBtn, updateBtn, deleteBtn;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this,"BookStore.db",null,1);

        createDataBtn = (Button) findViewById(R.id.btn_create);
        addBtn = findViewById(R.id.btn_add);
        queryBtn = findViewById(R.id.btn_query);
        updateBtn = findViewById(R.id.btn_update);
        deleteBtn = findViewById(R.id.btn_delete);

        createDataBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db;
        ContentValues contentValues;

        switch (v.getId()){
            case R.id.btn_create:
                dbHelper.getWritableDatabase();
                break;
            case R.id.btn_add:
                db = dbHelper.getWritableDatabase();
                contentValues = new ContentValues();
                contentValues.put("name","The Da Vinci Code");
                contentValues.put("author","Dan Brown");
                contentValues.put("pages",454);
                contentValues.put("price",16.96);
                db.insert("Book",null,contentValues);
                contentValues.clear();
                contentValues.put("name","The Lost Symbol");
                contentValues.put("author","Dan Brown");
                contentValues.put("pages",510);
                contentValues.put("price",19.95);
                db.insert("Book",null,contentValues);
                contentValues.clear();
                contentValues.put("name","The Last Dinne");
                contentValues.put("author","Big Boss");
                contentValues.put("pages",560);
                contentValues.put("price",25.67);
                db.insert("Book",null,contentValues);

                break;
            case R.id.btn_query:
                db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if(cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","book name is"+name);
                        Log.d("MainActivity","book author is"+author);
                        Log.d("MainActivity","book pages is"+pages);
                        Log.d("MainActivity","book price is"+price);
                    }
                    cursor.close();
                }
                break;
            case R.id.btn_update:
                db = dbHelper.getWritableDatabase();
                contentValues = new ContentValues();
                contentValues.put("pages",500);
                db.update("Book", contentValues,"name = ?",new String[]{"The Lost Symbol"});
                break;
            case R.id.btn_delete:
                db = dbHelper.getWritableDatabase();
                db.delete("Book","name = ?",new String[]{"The Last Dinne"});
                break;

            default:
        }
    }
}
