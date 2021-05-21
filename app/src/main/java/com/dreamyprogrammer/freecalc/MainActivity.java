package com.dreamyprogrammer.freecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextModify;
    private EditText editTextNotModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setfindView();
        setPresets();
    }

    private void setPresets() {
        getSupportActionBar().hide();
        editTextModify.setInputType(InputType.TYPE_NULL);
        editTextNotModify.setInputType(InputType.TYPE_NULL);
    }

    private void setfindView() {
        editTextModify = findViewById(R.id.et_modify);
        editTextNotModify = findViewById(R.id.et_not_modify);
    }
}