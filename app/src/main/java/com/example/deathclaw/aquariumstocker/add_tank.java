package com.example.deathclaw.aquariumstocker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_tank extends AppCompatActivity {

    SQLiteDatabase tanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tank);
        tanks = openOrCreateDatabase("Tanks", MODE_PRIVATE, null);
    }

    public void toStock(View v) {
        EditText ph = (EditText) findViewById(R.id.PH);
        EditText temp = (EditText) findViewById(R.id.Temp);
        EditText size = (EditText) findViewById(R.id.Gallons);
        Intent tank = getIntent();
        final String name = tank.getStringExtra("Name");

        if (ph.getText().length() > 0 && temp.getText().length() > 0 && size.getText().length() > 0) {

            if(tank.getStringExtra("tank").equals("new")) {
                tanks.execSQL("CREATE TABLE " +name+ " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(50), Amount INT)");
                tanks.execSQL("INSERT INTO UserTanks (Id, Name, Description, Temperature, PH, Size) VALUES (NULL, '"+name+"', '"+tank.getStringExtra("desc")+"', " + String.valueOf(temp.getText()) + ", "+String.valueOf(ph.getText())+", "+String.valueOf(size.getText())+")");

                Intent intent = new Intent(getApplicationContext(), CompatibleFish.class);
                Bundle extras = new Bundle();
                extras.putString("Name", name);
                extras.putString("Ph", String.valueOf(ph.getText()));
                extras.putString("Temp", String.valueOf(temp.getText()));
                extras.putString("Size", String.valueOf(size.getText()));
                intent.putExtras(extras);
                startActivity(intent);
            }
            else
            {
                ContentValues data=new ContentValues();
                data.put("PH", String.valueOf(ph.getText()));
                data.put("Temperature" , String.valueOf(temp.getText()));
                data.put("Size", String.valueOf(size.getText()));
                tanks.update("UserTanks", data, "Name = ?", new String[]{name});

                Cursor Key = tanks.rawQuery("SELECT Name FROM "+ name, new String[]{});
                Key.moveToFirst();

                for(int len2 = 0; len2 < Key.getCount(); len2++) {
                    Cursor Key1 = tanks.rawQuery("SELECT Name FROM Fish WHERE Name=? AND minPH>?", new String[]{Key.getString(0), String.valueOf(ph.getText())});
                    Cursor Key2 = tanks.rawQuery("SELECT Name FROM Fish WHERE Name=? AND maxPH<?", new String[]{Key.getString(0), String.valueOf(ph.getText())});
                    Cursor Key3 = tanks.rawQuery("SELECT Name FROM Fish WHERE Name=? AND minTemp>?", new String[]{Key.getString(0), String.valueOf(temp.getText())});
                    Cursor Key4 = tanks.rawQuery("SELECT Name FROM Fish WHERE Name=? AND maxTemp<?", new String[]{Key.getString(0), String.valueOf(temp.getText())});
                    Cursor Key5 = tanks.rawQuery("SELECT Name FROM Fish WHERE Name=? AND tankSize>?", new String[]{Key.getString(0), String.valueOf(size.getText())});

                    Key1.moveToFirst();
                    Key2.moveToFirst();
                    Key3.moveToFirst();
                    Key4.moveToFirst();
                    Key5.moveToFirst();

                    for (int len = 0; len < Key1.getCount(); len++) {
                        Toast.makeText(getApplicationContext(), "PH too low for " + Key1.getString(0), Toast.LENGTH_SHORT).show();
                        Key1.moveToNext();
                    }
                    for (int len = 0; len < Key2.getCount(); len++) {
                        Toast.makeText(getApplicationContext(), "PH too high for " + Key2.getString(0), Toast.LENGTH_SHORT).show();
                        Key2.moveToNext();
                    }
                    for (int len = 0; len < Key3.getCount(); len++) {
                        Toast.makeText(getApplicationContext(), "Temp too low for " + Key3.getString(0), Toast.LENGTH_SHORT).show();
                        Key3.moveToNext();
                    }
                    for (int len = 0; len < Key4.getCount(); len++) {
                        Toast.makeText(getApplicationContext(), "Temp too high for " + Key4.getString(0), Toast.LENGTH_SHORT).show();
                        Key4.moveToNext();
                    }
                    for (int len = 0; len < Key5.getCount(); len++) {
                        Toast.makeText(getApplicationContext(), "Tank too small for " + Key5.getString(0), Toast.LENGTH_SHORT).show();
                        Key5.moveToNext();
                    }
                    Key.moveToNext();
                }


                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(intent);
            }
        }
        else
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
