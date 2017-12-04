package com.example.deathclaw.aquariumstocker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompatibleFish extends AppCompatActivity {

    SQLiteDatabase tanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatible_fish);
        tanks = openOrCreateDatabase("Tanks", MODE_PRIVATE, null);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("Name");
        final String ph = intent.getStringExtra("Ph");
        final String temp = intent.getStringExtra("Temp");
        final String size = intent.getStringExtra("Size");


        ArrayList nameArray = new ArrayList();
        ArrayList numberArray = new ArrayList();

        Cursor Key1 = tanks.rawQuery("SELECT * FROM Fish WHERE minPH<=? AND maxPH>=? AND minTemp<=? AND maxTemp>=? AND tankSize<=?", new String[]{ph, ph, temp, temp, size});
        Key1.moveToFirst();



        for (int len = 0; len < Key1.getCount(); len++) {
            Cursor Key4 = tanks.rawQuery("SELECT Amount FROM "+name+" WHERE Name = '"+Key1.getString(1)+"'", new String[]{});
            Key4.moveToFirst();
            nameArray.add(Key1.getString(1));
            if((Key4 != null) && (Key4.getCount() > 0))
            {numberArray.add(Key4.getString(0));}
            else numberArray.add(0);
            Key1.moveToNext();
        }
        final ArrayList finalArray = nameArray;

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item,
                nameArray);

        ListView listView = (ListView) findViewById(R.id.list3);
        listView.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                this,
                R.layout.list_item,
                numberArray);

        ListView listView2 = (ListView) findViewById(R.id.list4);
        listView2.setAdapter(arrayAdapter2);

        LinearLayout plus = (LinearLayout) findViewById(R.id.plus);
        for (int len = 0; len < Key1.getCount(); len++) {
            Button newButton = new Button(this);
            newButton.setHeight(50);
            newButton.setWidth(50);
            newButton.setText("Add");
            newButton.setTag(len);
            newButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    String s = (String)finalArray.get(Integer.parseInt(v.getTag().toString()));
                    Cursor Key2 = tanks.rawQuery("SELECT * FROM "+name+" WHERE Name = ?", new String[]{s});
                    Key2.moveToFirst();
                    if((Key2 != null) && (Key2.getCount() > 0))
                    {
                        tanks.execSQL("UPDATE "+name+" SET Amount = "+(Key2.getInt(2)+1)+" WHERE Name = '"+s+"'");
                    }
                    else
                    {
                        tanks.execSQL("INSERT INTO "+name+" (Id, Name, Amount) VALUES (NULL, '"+s+"', 1)");
                    }


                    Cursor Key1 = tanks.rawQuery("SELECT * FROM Fish WHERE minPH<=? AND maxPH>=? AND minTemp<=? AND maxTemp>=? AND tankSize<=?", new String[]{ph, ph, temp, temp, size});
                    Key1.moveToFirst();
                    ArrayList numberArray = new ArrayList();

                    ListView listView = (ListView) findViewById(R.id.list4);

                    for (int len = 0; len < Key1.getCount(); len++) {
                        Cursor Key4 = tanks.rawQuery("SELECT Amount FROM "+name+" WHERE Name = '"+Key1.getString(1)+"'", new String[]{});
                        Key4.moveToFirst();
                        if((Key4 != null) && (Key4.getCount() > 0))
                        {numberArray.add(Key4.getString(0));}
                        else{numberArray.add(0);}
                        Key1.moveToNext();
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(),
                            R.layout.list_item,
                            numberArray);

                    listView.setAdapter(arrayAdapter);
                }
            });
            plus.addView(newButton);
        }



        LinearLayout minus = (LinearLayout) findViewById(R.id.minus);
        for (int len = 0; len < Key1.getCount(); len++) {
            Button newButton = new Button(this);
            newButton.setHeight(50);
            newButton.setWidth(50);
            newButton.setText("Remove");
            newButton.setTag(len);
            newButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    String s = (String)finalArray.get(Integer.parseInt(v.getTag().toString()));
                    Cursor Key3 = tanks.rawQuery("SELECT * FROM "+name+" WHERE Name = ?", new String[]{s});
                    if((Key3 != null) && (Key3.getCount() > 0))
                    {
                        Key3.moveToFirst();
                        if(Key3.getInt(2) == 1)
                        {
                            tanks.execSQL("DELETE FROM "+name+" WHERE Name = '"+s+"'");
                        }
                        else
                        {
                            tanks.execSQL("UPDATE "+name+" SET Amount = "+(Key3.getInt(2)-1)+" WHERE Name = '"+s+"'");
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "You do not have any of that species to remove", Toast.LENGTH_SHORT).show();
                    }
                    Cursor Key1 = tanks.rawQuery("SELECT * FROM Fish WHERE minPH<=? AND maxPH>=? AND minTemp<=? AND maxTemp>=? AND tankSize<=?", new String[]{ph, ph, temp, temp, size});
                    Key1.moveToFirst();
                    ArrayList numberArray = new ArrayList();

                    ListView listView = (ListView) findViewById(R.id.list4);

                    for (int len = 0; len < Key1.getCount(); len++) {
                        Cursor Key4 = tanks.rawQuery("SELECT Amount FROM "+name+" WHERE Name = '"+Key1.getString(1)+"'", new String[]{});
                        Key4.moveToFirst();
                        if((Key4 != null) && (Key4.getCount() > 0))
                        {numberArray.add(Key4.getString(0));}
                        else{numberArray.add(0);}
                        Key1.moveToNext();
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(),
                            R.layout.list_item,
                            numberArray);

                    listView.setAdapter(arrayAdapter);
                }
            });
            minus.addView(newButton);
        }
    }

    public void returnHome(View v)
    {
        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(intent);
    }
}
