package com.example.algorithmus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class NamingActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText[] editnames;
    private EditText[] editmoneys;
    private int nops = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naming);

        Intent intent = getIntent();
        nops = intent.getIntExtra("number_of_persons", 4);

        GridLayout gridlayout = (GridLayout) findViewById(R.id.activity_naming);

        Button backbutton = (Button) findViewById(R.id.buttonback);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button okbutton = (Button) findViewById(R.id.buttonok);
        okbutton.setOnClickListener(this);

        editnames = new EditText[nops];
        editmoneys = new EditText[nops];

        for(int i = 0; i < nops; ++i) {
            LinearLayout linlayout = new LinearLayout(this);
            linlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linlayout.setOrientation(LinearLayout.HORIZONTAL);

            editnames[i] = new EditText(this);
            editnames[i].setHint("Person "+Integer.toString(i+1));
            editnames[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            editnames[i].setMaxWidth(editnames[i].getWidth());
            editnames[i].setMaxLines(1);

            editmoneys[i] = new EditText(this);
            editmoneys[i].setHint("hat wieviel gezahlt?");
            editmoneys[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editmoneys[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            editmoneys[i].setMaxWidth(editmoneys[i].getWidth());
            editmoneys[i].setMaxLines(1);

            linlayout.addView(editnames[i]);
            linlayout.addView(editmoneys[i]);
            gridlayout.addView(linlayout);
        }
    }

    @Override
    public void onClick(View v) {
        int personstopay = nops;
        String[] names = new String[nops];
        float[] moneys = new float[nops];

        for(int i = 0; i < nops; ++i) {

            names[i] = editnames[i].getText().toString();
            if(names[i].isEmpty())
                names[i] = "Person "+Integer.toString(i+1);

            String value = editmoneys[i].getText().toString();
            if(!value.isEmpty()) {
                try {
                    moneys[i] = Float.parseFloat(value);
                } catch (NumberFormatException ex) {
                    moneys[i] = 0;
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Ich kann den Eintrag '" + value + "' von " + names[i] + " nicht verstehen.");
                    dlgAlert.setTitle("Achtung");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    dlgAlert.create().show();
                }
            }
        }

        Intent intent = new Intent(this, PersonstopayActivity.class);
        intent.putExtra("names", names);
        intent.putExtra("moneys", moneys);
        startActivity(intent);
    }
}
