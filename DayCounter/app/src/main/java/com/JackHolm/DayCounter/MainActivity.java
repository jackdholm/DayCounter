package com.JackHolm.DayCounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements CounterListAdapter.OnItemListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, AddCounter.class));
            }
        });

        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.MenuDeleteAll)
        {
            CounterList.Instance().DeleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initList()
    {
        RecyclerView rView = findViewById(R.id.CounterList);
        CounterList list = new CounterList(getApplicationContext(), rView);

        CounterListAdapter adapter = new CounterListAdapter(list.Names, list.Dates, list.DayCounts, this, this);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position)
    {
        Intent intent = new Intent(MainActivity.this, EditCounter.class);
        //Counter c = CounterList.Instance().get(position);
        intent.putExtra("com.JackHolm.DayCounter.position", position);
        //CounterList.Instance().Delete(position);
        startActivity(intent);
    }
}
