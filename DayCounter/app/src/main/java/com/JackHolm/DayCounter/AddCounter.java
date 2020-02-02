package com.JackHolm.DayCounter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Button;

import java.util.Calendar;

public class AddCounter extends AppCompatActivity
{
    private TextView dateDisplayText;
    private DatePicker currentDate;
    private Button createButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);
        dateDisplayText = findViewById(R.id.DateInputText);
        dateDisplayText.setShowSoftInputOnFocus(false);

        dateDisplayText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (b)
                {
                    displayDateDialog();
                }
            }
        });
        dateDisplayText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(dateDisplayText.hasFocus())
                {
                        displayDateDialog();
                }
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                Log.d("AddCounter-debug","onDateSet: " + datePicker.getYear() + "/" + (i1+1) + "/" + i2);
                dateDisplayText.setText(i1+1 + "/" + i2 + "/" + i);
                currentDate = datePicker;
            }
        };

        createButton = findViewById(R.id.CreateButton);
        final TextView nameInput = findViewById(R.id.NameInputText);

        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("AddCounter-debug", "Name: " + nameInput.getText() + " " + "Date: " + currentDate.getYear() + "/" + currentDate.getMonth());
                createCounter(nameInput.getText().toString(), currentDate);
            }
        });
    }

    public void displayDateDialog()
    {
        hideKeyboard(AddCounter.this);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(AddCounter.this, dateSetListener, year, month, day);
        dialog.show();
    }

    public static void hideKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void createCounter(String name, DatePicker date)
    {
        Counter c = new Counter(name, date.getYear(), date.getMonth()+1, date.getDayOfMonth());
        CounterList.Instance().Add(c);
        finish();
    }
}
