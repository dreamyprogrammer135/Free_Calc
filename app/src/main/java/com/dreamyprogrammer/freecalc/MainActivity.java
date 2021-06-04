package com.dreamyprogrammer.freecalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {

    private TextView textViewNotModify;
    private EditText editTextModify;
    private SimpleCalculator calculator;
    private MaterialButton buttonHistory, buttonSettings;
    public static final String APP_PREFERENCES = "save_settings";
    public static final String APP_PREFERENCES_EXPRESSION = "expression";
    public static final String APP_PREFERENCES_LAST_CHARACTER_OPERATION = "lastCharacterOperation";
    public static final String APP_PREFERENCES_STATE_SEPARATOR = "stateSeparator";
    public static final String CALC_DATA = "calcData";
    private SharedPreferences mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode());
        findView();
        setPresets();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShared();

        Bundle initialBundle = getIntent().getExtras();
        if (initialBundle != null){
            textViewNotModify.setText(initialBundle.getString(CALC_DATA));
            StringBuilder s = new StringBuilder();
            s.append(initialBundle.getString(CALC_DATA));
            calculator.setExpression(s);
            calculator.getEquallyn();
        }



    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSave.edit();
        editor.putString(APP_PREFERENCES_EXPRESSION, calculator.getExpression());
        editor.putBoolean(APP_PREFERENCES_LAST_CHARACTER_OPERATION, calculator.getLastCharacterOperatin());
        editor.putBoolean(APP_PREFERENCES_STATE_SEPARATOR, calculator.getStateSeparator());
        editor.apply();
    }

    private void getShared(){
        mSave = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        StringBuilder exp = new StringBuilder();
        Boolean b1 = false;
        Boolean b2 = false;
        if(mSave.contains(APP_PREFERENCES_EXPRESSION)) {
            exp.append(mSave.getString(APP_PREFERENCES_EXPRESSION, getResources().getString(R.string._0)));
        } else {
            exp.append(getResources().getString(R.string._0));
        }
        if(mSave.contains(APP_PREFERENCES_LAST_CHARACTER_OPERATION)) {
            b1 =mSave.getBoolean(APP_PREFERENCES_LAST_CHARACTER_OPERATION, false);
        }
        if(mSave.contains(APP_PREFERENCES_EXPRESSION)) {
            b2 = mSave.getBoolean(APP_PREFERENCES_STATE_SEPARATOR, false);
        }
        if (calculator != null) {
            calculator.setExpression(exp);
            calculator.setLastCharacterOperatin(b1);
            calculator.setStateSeparator(b2);

        } else {
            calculator = new SimpleCalculator(exp,b1,b2);
        }
        editTextModify.setText(calculator.getEquallyn());
        textViewNotModify.setText(calculator.getText());
    }

    private void setPresets() {
        getSupportActionBar().hide();
        editTextModify.setInputType(InputType.TYPE_NULL);
        int[] buttonNumIds = new int[] {
                R.id.button_1,
                R.id.button_2,
                R.id.button_3,
                R.id.button_4,
                R.id.button_5,
                R.id.button_6,
                R.id.button_7,
                R.id.button_8,
                R.id.button_9,
                R.id.button_0,
                R.id.button_parenthesis_right,
                R.id.button_clean_entry,
                R.id.button_parenthesis_left,
                R.id.button_clean
        };
        int[] buttonActionsIds = new int[]{
                R.id.button_plus,
                R.id.button_minus,
                R.id.button_multiplication,
                R.id.button_division,
                R.id.button_equallyn,
                R.id.button_separator,
                R.id.button_percent,
        };


        calculator = new SimpleCalculator();
        textViewNotModify.setText(calculator.getText());
        editTextModify.setText(calculator.getEquallyn());
        View.OnClickListener buttonNumClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.onButtonNumPressed(v.getId());
                textViewNotModify.setText(calculator.getText());
                if (v.getId() == R.id.button_clean) {
                    editTextModify.setText(calculator.getEquallyn());
                    textViewNotModify.setText(calculator.getText());
                }
            }
        };
        View.OnClickListener buttonActionsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.onButtonActionPressed(v.getId());
                textViewNotModify.setText(calculator.getText());
                if (v.getId() == R.id.button_equallyn) {
                    editTextModify.setText(calculator.getEquallyn());
                }
            }
        };

        for (int i = 0; i < buttonNumIds.length; i++) {
            findViewById(buttonNumIds[i]).setOnClickListener(buttonNumClickListener);
        }
        for (int i = 0; i < buttonActionsIds.length; i++) {
            findViewById(buttonActionsIds[i]).setOnClickListener(buttonActionsClickListener);
        }

        buttonHistory.setOnClickListener(v -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO));

        buttonSettings.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            Intent runSettings = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(runSettings);
        });
    }


    private void findView() {
        editTextModify = findViewById(R.id.et_modify);
        textViewNotModify = findViewById(R.id.et_not_modify);
        buttonHistory = findViewById(R.id.button_history);
        buttonSettings = findViewById(R.id.button_settings);

    }
}