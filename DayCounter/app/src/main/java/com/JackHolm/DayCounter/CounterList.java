package com.JackHolm.DayCounter;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

public class CounterList
{
    private static final String FILENAME = "counters.dat";
    private String filepath;
    private static CounterList instance;
    private File saveFile;
    private ArrayList<Counter> counters;
    Context context;
    private RecyclerView view;
    public ArrayList<String> Names;
    public ArrayList<String> Dates;
    public ArrayList<String> DayCounts;

    public static CounterList Instance()
    {
        return instance;
    }
    public CounterList(Context c, RecyclerView v)
    {
        instance = this;
        AppCompatActivity a;
        counters = new ArrayList<>();
        Names = new ArrayList<>();
        Dates = new ArrayList<>();
        DayCounts = new ArrayList<>();
        view = v;
        context = c;
        filepath = context.getFilesDir().getPath();
        open(context);
    }

    public Counter get(int i)
    {
        return counters.get(i);
    }
    private void open(Context context)
    {
        String line;
        try
        {
            //InputStream is = context.getAssets().open(FILENAME);
            File file = new File(filepath + FILENAME);
            file.createNewFile();
            if (file.exists())
                Log.d("CounterList-debug", "exists");
            FileReader is = new FileReader(file);
            BufferedReader reader = new BufferedReader(is);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            Log.i("CounterList-debug", "list: " + Arrays.toString(context.getAssets().list("")));
            while ((line =  reader.readLine()) != null)
            {
                Log.d("CounterList-debug","reading " + filepath);
                Log.d("CounterList-debug",line);
                String [] tokens = line.split(" ");

                Counter c = new Counter(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                Names.add(c.Name);
                Dates.add(c.DateString());
                DayCounts.add(Integer.toString(c.Count()));
                counters.add(c);
                for (String i: tokens)
                {
                    Log.d("CounterList-debug", i);
                }
            }
            reader.close();
            is.close();
        }
        catch (IOException ex)
        {
            Log.d("CounterList-debug", "dddd");
            Log.getStackTraceString(ex);
        }
    }

    private void append(String s)
    {
        boolean append = false;
        try
        {
            File file = new File(filepath + FILENAME);
            file.createNewFile();
            if (file.length() > 0)
            {
                append = true;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(s);
            writer.close();
            Log.d("CounterList-debug", "appending " + s);
        }
        catch (IOException ex)
        {
            Log.d("CounterList-debug", "append error: " + ex.getMessage());
            Log.getStackTraceString(ex);
        }
    }

    public void Add(Counter c)
    {
        Names.add(c.Name);
        Dates.add(c.DateString());
        String countString = Integer.toString(c.Count());
        DayCounts.add(countString);
        counters.add(c);
        append(c.Name + " " + c.DateSaveString());

        view.getAdapter().notifyItemInserted(Names.size()-1);
    }

    public void DeleteAll()
    {
        Log.d("CounterList-debug", "DeleteAll");
        counters.clear();
        Names.clear();
        Dates.clear();
        DayCounts.clear();

        try
        {
            File file = new File(filepath + FILENAME);
            file.createNewFile();
            if (file.length() > 0)
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                writer.close();
            }
        }
        catch (IOException ex)
        {
            Log.d("CounterList-debug", "Delete error: " + ex.getMessage());
            Log.getStackTraceString(ex);
        }

        view.getAdapter().notifyDataSetChanged();
    }

    public void Delete(int position)
    {
        counters.remove(position);
        Names.remove(position);
        Dates.remove(position);
        DayCounts.remove(position);
        save();
        view.getAdapter().notifyDataSetChanged();
    }

    private void save()
    {
        try
        {
            File file = new File(filepath + FILENAME);
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            for (Counter c: counters)
            {
                writer.write(c.Name + " " + c.DateSaveString());
            }
            writer.close();
            Log.d("CounterList-debug", "saving");
        }
        catch (IOException ex)
        {
            Log.d("CounterList-debug", "save error: " + ex.getMessage());
            Log.getStackTraceString(ex);
        }
    }

    public void Update(int position, String name, int year, int month, int day)
    {
        Counter c = new Counter(name, year, month, day);
        counters.set(position, c);
        Names.set(position, c.Name);
        Dates.set(position, c.DateString());
        DayCounts.set(position, Integer.toString(c.Count()));
        save();
        view.getAdapter().notifyItemChanged(position);
    }
}
