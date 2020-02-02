package com.JackHolm.DayCounter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class EditCounter extends AppCompatActivity
{
    private TextView nameInput;
    private TextView dateDisplayText;
    private Counter counter;
    private Button UpdateButton;
    private Button DeleteButton;
    private DatePicker newDate;
    private boolean dateChanged;
    private int listPosition;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);
        Bundle extras = getIntent().getExtras();
        nameInput = findViewById(R.id.NameInputText);
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
                dateDisplayText.setText(i1+1 + "/" + i2 + "/" + i);
                newDate = datePicker;
                dateChanged = true;
            }
        };
        UpdateButton = findViewById(R.id.UpdateButton);
        UpdateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (dateChanged)
                {
                    CounterList.Instance().Update(listPosition, nameInput.getText().toString(), newDate.getYear(), newDate.getMonth()+1, newDate.getDayOfMonth());
                }
                finish();
            }
        });
        DeleteButton = findViewById(R.id.DeleteButton);
        DeleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CounterList.Instance().Delete(listPosition);
                finish();
            }
        });
        if (extras != null)
        {
            listPosition = extras.getInt("com.JackHolm.DayCounter.position");
            counter = CounterList.Instance().get(listPosition);
            //Log.d("EditCounter-debug", value);
            nameInput.setText(counter.Name);
            dateDisplayText.setText(counter.DateString());
        }


    }

    public void displayDateDialog()
    {
        hideKeyboard(EditCounter.this);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(EditCounter.this, dateSetListener, year, month, day);
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
}
