package com.example.vypocetpalivovesmesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText mnozstviBenzinu;
    Spinner pomerSmesi;
    EditText mnozstviOleje;
    double benzin, olej;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pomerSmesi = findViewById(R.id.pomerSmesi);
        mnozstviOleje = findViewById(R.id.mnozstviOleje);
        mnozstviBenzinu = findViewById(R.id.mnozstviBenzinu);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pomery, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pomerSmesi.setAdapter(adapter);
        pomerSmesi.setOnItemSelectedListener(this);

        // benzin = (olej * pomer) / 1000
        // olej = (benzin * 1000) / pomer

            mnozstviBenzinu.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (mnozstviBenzinu.hasFocus()) {
                        if (TextUtils.isEmpty(mnozstviBenzinu.getText())) {
                            return;
                        }
                        benzin = Double.parseDouble(mnozstviBenzinu.getText().toString());
                        olej = vypocet(benzin, Double.parseDouble(pomerSmesi.getSelectedItem().toString().replace("1:", "")), true);
                        mnozstviOleje.setText(String.format("%.1f", olej));
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
            });

            mnozstviOleje.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (mnozstviOleje.hasFocus()) {
                        if (TextUtils.isEmpty(mnozstviOleje.getText())) {
                            return;
                        }
                        olej = Double.parseDouble(mnozstviOleje.getText().toString());
                        benzin = vypocet(olej, Double.parseDouble(pomerSmesi.getSelectedItem().toString().replace("1:", "")), false);
                        mnozstviBenzinu.setText(String.format("%.1f", benzin));
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
            });

    }

    private double vypocet(double vstup, double pomer, boolean volba){
            //vstupem je benzín
            if (volba == true){
                return (vstup * 1000) / pomer;
            }
            //vstupem je olej
            else{
                return (vstup * pomer) / 1000;
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(mnozstviBenzinu.hasFocus()){
            olej = vypocet(benzin, Double.parseDouble(pomerSmesi.getSelectedItem().toString().replace("1:", "")),true);
            mnozstviOleje.setText(String.format("%.1f", olej));
        }
        else if(mnozstviOleje.hasFocus()){
            benzin = vypocet(olej, Double.parseDouble(pomerSmesi.getSelectedItem().toString().replace("1:", "")),false);
            mnozstviBenzinu.setText(String.format("%.1f", benzin));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}