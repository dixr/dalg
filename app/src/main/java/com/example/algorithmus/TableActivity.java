package com.example.algorithmus;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;
// TODO: use doubles and round to 3 digits
public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent = getIntent();
        String[] names = intent.getStringArrayExtra("names");
        float[] moneys = intent.getFloatArrayExtra("moneys");
        float[] topay = intent.getFloatArrayExtra("topay");

        float sum = 0;
        for(float t : topay)
            sum += t;

        float[] results = new float[names.length];
        for(int i = 0; i < names.length; ++i)
            results[i] = moneys[i] - topay[i];

        TableLayout table = new TableLayout(this);
        table.setStretchAllColumns(true);

        {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GREEN);
            row.setPadding(5, 5, 5, 5);
            row.setGravity(Gravity.CENTER);
            TextView column1 = new TextView(this);
            column1.setTextSize(18);
            column1.setText("Name");
            column1.setPadding(4, 4, 4, 4);
            column1.setGravity(Gravity.CENTER);
            row.addView(column1);
            TextView column2 = new TextView(this);
            column2.setTextSize(18);
            column2.setText("Aktion");
            column2.setPadding(4, 4, 4, 4);
            column2.setGravity(Gravity.CENTER);
            row.addView(column2);
            table.addView(row);
        }

        for(int i = 0; i < names.length; ++i) {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GREEN);
            row.setPadding(5,5,5,5);
            row.setGravity(Gravity.CENTER);
            TextView column1 = new TextView(this);
            column1.setText(names[i]);
            column1.setPadding(4,4,4,4);
            column1.setGravity(Gravity.CENTER);
            row.addView(column1);
            TextView column2 = new TextView(this);
            if(results[i] > 1e-10)
                column2.setText("bekommt "+Float.toString(results[i]));
            else if(results[i] < -1e-10)
                column2.setText("zahlt "+Float.toString(-results[i]));
            else
                column2.setText("macht nichts");
            column2.setPadding(4,4,4,4);
            column2.setGravity(Gravity.CENTER);
            row.addView(column2);
            table.addView(row);
        }

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_table);
        layout.addView(table,0);
    }
}
