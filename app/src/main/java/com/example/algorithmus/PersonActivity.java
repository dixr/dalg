package com.example.algorithmus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    }

    public void confirm(View view) {
        Intent intent = new Intent(this, NamingActivity.class);
        EditText edittext = (EditText) findViewById(R.id.number_of_persons);
        String nops = edittext.getText().toString();
        int nopsint = 4;
        intent.putExtra("number_of_persons", nopsint);
        if(!nops.isEmpty()) {
            try {
                nopsint = Integer.parseInt(nops);
                if(nopsint < 1 || nopsint > 200)
                    throw new NumberFormatException("");
            } catch (NumberFormatException ex) {
                nopsint = 4;
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Ungültige Personenanzahl; bitte zwischen 1 und 200 Personen angeben");
                dlgAlert.setTitle("Achtung");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dlgAlert.create().show();
                return;
            }
        }

        intent.putExtra("number_of_persons", nopsint);
        startActivity(intent);
    }

}
