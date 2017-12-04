package com.example.deathclaw.aquariumstocker;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageTankActivity extends AppCompatActivity {

    String ph;
    String size;
    String temp;

    SQLiteDatabase tanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_tank);
        Intent intent =  getIntent();
        String id = intent.getStringExtra("Name");

        tanks = openOrCreateDatabase("Tanks", MODE_PRIVATE, null);

        ArrayList nameArray = new ArrayList();
        ArrayList numberArray = new ArrayList();

            Cursor Key1 = tanks.rawQuery("SELECT Name FROM " + id, new String[]{});
            Key1.moveToFirst();

            for (int len = 0; len < Key1.getCount(); len++) {
                Cursor Key4 = tanks.rawQuery("SELECT Amount FROM " + id + " WHERE Name = '" + Key1.getString(0) + "'", new String[]{});
                Key4.moveToFirst();
                nameArray.add(Key1.getString(0));
                if ((Key4 != null) && (Key4.getCount() > 0)) {
                    numberArray.add(Key4.getInt(0));
                } else numberArray.add(0);
                Key1.moveToNext();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    R.layout.list_item,
                    nameArray);

            ListView listView = (ListView) findViewById(R.id.name);
            listView.setAdapter(arrayAdapter);

            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                    this,
                    R.layout.list_item,
                    numberArray);

            ListView listView2 = (ListView) findViewById(R.id.num);
            listView2.setAdapter(arrayAdapter2);

            Cursor Key2 = tanks.rawQuery("SELECT * FROM UserTanks WHERE Name = '" + id + "'", new String[]{});
            Key2.moveToFirst();

            ph = Key2.getString(4);
            temp = Key2.getString(3);
            size = Key2.getString(5);

    }

    public void changeStock(View v)
    {
        Intent intent =  getIntent();
        String id = intent.getStringExtra("Name");
        Intent intent2 = new Intent(this, CompatibleFish.class);
        intent2.putExtra("Name", id);
        intent2.putExtra("Ph", ph);
        intent2.putExtra("Size", size);
        intent2.putExtra("Temp", temp);
        startActivity(intent2);

    }

    public void delete(View v)
    {
        Intent intent =  getIntent();
        String id = intent.getStringExtra("Name");
        tanks = openOrCreateDatabase("Tanks", MODE_PRIVATE, null);
        tanks.execSQL("DROP TABLE IF EXISTS " + id);
        tanks.execSQL("DELETE FROM UserTanks WHERE Name = '"+id+"'");
        Intent home = new Intent(this, HomeScreenActivity.class);
        startActivity(home);
    }
}
