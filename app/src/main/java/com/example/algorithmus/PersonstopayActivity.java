package com.example.algorithmus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class PersonstopayActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox[] checkboxes;
    private int nops = 0;
    private float sum = 0;
    private String[] names;
    private float[] moneys;
    private float[] topay;
    private EditText[] personstopay;
    private EditText[] namesview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personstopay);

        Intent intent = getIntent();
        names = intent.getStringArrayExtra("names");
        moneys = intent.getFloatArrayExtra("moneys");
        nops = names.length;
        topay = new float[nops];

        for(float m : moneys)
            sum += m;
        for(int i = 0; i < nops; ++i)
            topay[i] = sum / nops;

        GridLayout gridlayout = (GridLayout) findViewById(R.id.activity_personstopay);

        Button backbutton = (Button) findViewById(R.id.buttonback);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button okbutton = (Button) findViewById(R.id.buttonok);
        okbutton.setOnClickListener(this);

        namesview = new EditText[nops];
        personstopay = new EditText[nops];

        for(int i = 0; i < nops; ++i) {
            LinearLayout linlayout = new LinearLayout(this);
            linlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linlayout.setOrientation(LinearLayout.HORIZONTAL);

            namesview[i] = new EditText(this);
            namesview[i].setFocusable(false);
            namesview[i].setHint("Person "+Integer.toString(i+1));
            namesview[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            namesview[i].setMaxWidth(namesview[i].getWidth());
            namesview[i].setMaxLines(1);
            namesview[i].setText(names[i]);

            personstopay[i] = new EditText(this);
            personstopay[i].setHint(/*Float.toString(sum) + " / " + Integer.toString(nops) + " = " + */Float.toString(topay[i]));
            personstopay[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            personstopay[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            personstopay[i].setMaxWidth(personstopay[i].getWidth());
            personstopay[i].setMaxLines(1);

            linlayout.addView(namesview[i]);
            linlayout.addView(personstopay[i]);
            gridlayout.addView(linlayout);
        }
    }

    @Override
    public void onClick(View v) {
        for(int i = 0; i < nops; ++i) {
            String value = personstopay[i].getText().toString();
            if(!value.isEmpty()) {
                try {
                    topay[i] = Float.parseFloat(value);
                } catch (NumberFormatException ex) {
                    topay[i] = sum / nops;
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

        Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra("names", names);
        intent.putExtra("moneys", moneys);
        intent.putExtra("topay", topay);
        startActivity(intent);
    }
}
