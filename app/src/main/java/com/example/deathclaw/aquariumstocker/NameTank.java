package com.example.deathclaw.aquariumstocker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NameTank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_tank);
    }

    public void toAdd(View v)
    {
        EditText name = (EditText) findViewById(R.id.name);
        EditText desc = (EditText) findViewById(R.id.desc);

        if(name.getText().length() > 0 && desc.getText().length() > 0) {
            Intent intent = new Intent(this, add_tank.class);

            Bundle extras = new Bundle();
            extras.putString("tank", "new");
            extras.putString("Name", String.valueOf(name.getText()));
            extras.putString("desc", String.valueOf(desc.getText()));
            intent.putExtras(extras);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();}
    }
}
