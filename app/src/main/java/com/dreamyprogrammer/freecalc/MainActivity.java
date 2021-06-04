package com.dreamyprogrammer.freecalc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "save_settings";
    public static final String APP_PREFERENCES_EXPRESSION = "expression";
    public static final String APP_PREFERENCES_LAST_CHARACTER_OPERATION = "lastCharacterOperatin";
    public static final String APP_PREFERENCES_STATE_SEPARATOR = "stateSeparator";
    private TextView textViewNotModify;
    private EditText editTextModify;
    private SimpleCalculator calculator;
    private Button buttonHistory, buttonSettings;
    private SharedPreferences sharedSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setPresets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShared();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedSave.edit();
        editor.putString(APP_PREFERENCES_EXPRESSION, calculator.getExpression());
        editor.putBoolean(APP_PREFERENCES_LAST_CHARACTER_OPERATION, calculator.getLastCharacterOperatin());
        editor.putBoolean(APP_PREFERENCES_STATE_SEPARATOR, calculator.getStateSeparator());
        editor.apply();
    }

    private void getShared() {
        sharedSave = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        StringBuilder exp = new StringBuilder();
        Boolean b1 = false;
        Boolean b2 = false;
        if (sharedSave.contains(APP_PREFERENCES_EXPRESSION)) {
            exp.append(sharedSave.getString(APP_PREFERENCES_EXPRESSION, "0"));
        } else {
            exp.append("0");
        }
        if (sharedSave.contains(APP_PREFERENCES_LAST_CHARACTER_OPERATION)) {
            b1 = sharedSave.getBoolean(APP_PREFERENCES_LAST_CHARACTER_OPERATION, false);
        }
        if (sharedSave.contains(APP_PREFERENCES_EXPRESSION)) {
            b2 = sharedSave.getBoolean(APP_PREFERENCES_STATE_SEPARATOR, false);
        }
        if (calculator != null) {
            calculator.setExpression(exp);
            calculator.setLastCharacterOperatin(b1);
            calculator.setStateSeparator(b2);

        } else {
            calculator = new SimpleCalculator(exp, b1, b2);
        }
        editTextModify.setText(calculator.getEquallyn());
        textViewNotModify.setText(calculator.getText());
    }

    private void setPresets() {
        getSupportActionBar().hide();
        editTextModify.setInputType(InputType.TYPE_NULL);
        int[] buttonNumIds = new int[]{
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

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Пока кнопка истории не работает", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Пока кнопка настроек не работает", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    private void findView() {
        editTextModify = findViewById(R.id.et_modify);
        textViewNotModify = findViewById(R.id.et_not_modify);
        buttonHistory = findViewById(R.id.button_history);
        buttonSettings = findViewById(R.id.button_settings);
    }

}