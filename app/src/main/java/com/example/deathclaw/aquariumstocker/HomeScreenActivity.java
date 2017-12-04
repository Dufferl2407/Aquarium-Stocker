package com.example.deathclaw.aquariumstocker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    SQLiteDatabase tanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //this.deleteDatabase("Tanks");
        // Useful for purging after tests

        tanks = openOrCreateDatabase("Tanks", MODE_PRIVATE, null);
        //check if database is there, else it catches the cursor error and creates it
        try {
            Cursor mCursor = tanks.rawQuery("SELECT * FROM  Fish", null);
            }
        catch(SQLiteException e) {
            //Queries to set up the Database
            tanks.execSQL("CREATE TABLE UserTanks (Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(25), Description VARCHAR(100), Temperature FLOAT, PH FLOAT, Size FLOAT)");

            tanks.execSQL("CREATE TABLE Fish (Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(50), tankSize FLOAT, minTemp FLOAT, maxTemp FLOAT, minPH FLOAT, maxPH FLOAT)");

            //Populated Database of Existing Fish
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Rasbora Heteromorpha', '10', '73', '82', '6', '7.5');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Zebra Danio', '10', '73', '82', '6', '7.5');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Black Skirt Tetra', '10', '72', '82', '6', '7.5');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Corydoras Sterbai', '10', '75', '82', '6', '7.6');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Clown Loach', '100', '72', '86', '6', '7.5');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Kissing Gourami', '50', '72', '82', '6.8', '8.5');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Pearl Gourami', '30', '75', '86', '6.5', '8');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Betta Splendens', '10', '75', '88', '6', '8');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Neon Tetra', '10', '68', '78', '5', '7');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Angelfish', '30', '75', '82', '5.8', '7');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Chocolate Gourami', '20', '75', '86', '5', '7.6');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Swordtail', '20', '64', '82', '7', '8.3');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Severum', '55', '73', '77', '6', '6.5');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Oscar', '55', '74', '81', '6', '8');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'German Blue Ram', '30', '72', '79', '5', '7');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Silver Arrowana', '132', '75', '82', '6', '7');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Tiger Barb', '30', '74', '79', '6', '7');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Cherry Barb', '30', '74', '79', '6', '7');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Goldfish', '30', '65', '75', '6.5', '7.5');");
            tanks.execSQL("INSERT INTO Fish (Id, Name, tankSize, minTemp, maxTemp, minPH, maxPH) VALUES (NULL, 'Rubber Plecostomus', '50', '72', '78', '6.5', '7.5');");
        }

        Cursor Key1 = tanks.rawQuery("SELECT * FROM UserTanks", new String[]{});
        Key1.moveToFirst();

        ArrayList nameArray = new ArrayList();
        ArrayList descArray = new ArrayList();


        for (int len = 0; len < Key1.getCount(); len++) {
            nameArray.add(Key1.getString(1));
            descArray.add(Key1.getString(2));
            Key1.moveToNext();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item,
                nameArray);

        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(arrayAdapter);
        final ArrayList finalArray = nameArray;

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                this,
                R.layout.list_item,
                descArray);

        ListView listView2 = (ListView) findViewById(R.id.list2);
        listView2.setAdapter(arrayAdapter2);


        LinearLayout update = (LinearLayout) findViewById(R.id.update);
        for (int len = 0; len < Key1.getCount(); len++) {
            Button newButton = new Button(this);
            newButton.setHeight(50);
            newButton.setWidth(50);
            newButton.setText("Params");
            newButton.setTag(len);
            newButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), add_tank.class);
                    String s = (String)finalArray.get(Integer.parseInt(v.getTag().toString()));
                    Bundle extras = new Bundle();
                    extras.putString("Name", s);
                    extras.putString("tank", "old");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
            update.addView(newButton);
        }

//
        LinearLayout monitor = (LinearLayout) findViewById(R.id.monitor);
                    for (int len = 0; len < Key1.getCount(); len++) {
                        Button newButton = new Button(this);
                        newButton.setHeight(50);
                        newButton.setWidth(50);
                        newButton.setText("Stocking");
                        newButton.setTag(len);
                        newButton.setOnClickListener(new Button.OnClickListener() {
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), ManageTankActivity.class);
                                String s = (String)finalArray.get(Integer.parseInt(v.getTag().toString()));
                                intent.putExtra("Name", s);
                                startActivity(intent);
                }
            });
            monitor.addView(newButton);
        }
    }

    public void addTank(View v)
    {
        Intent intent = new Intent(this, NameTank.class);
        intent.putExtra("tank", "new");
        startActivity(intent);
    }
}
