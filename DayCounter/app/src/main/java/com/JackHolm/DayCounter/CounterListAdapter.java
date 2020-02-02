package com.JackHolm.DayCounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CounterListAdapter extends RecyclerView.Adapter<CounterListAdapter.ViewHolder>
{
    private ArrayList<String> counterNames = new ArrayList<>();
    private ArrayList<String> counterDates = new ArrayList<>();
    private ArrayList<String> counterNumbers = new ArrayList<>();
    private Context mContext;
    private OnItemListener onItemListener;

    public CounterListAdapter(ArrayList<String> names, ArrayList<String> dates, ArrayList<String> numbers, Context context, OnItemListener listener)
    {
        counterNames = names;
        counterDates = dates;
        counterNumbers = numbers;
        mContext = context;
        onItemListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        ViewHolder holder = new ViewHolder(view, onItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.counterName.setText(counterNames.get(position));
        holder.counterDate.setText(counterDates.get(position));
        holder.counterNumber.setText(counterNumbers.get(position));
    }

    @Override
    public int getItemCount()
    {
        return counterNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView counterName;
        TextView counterDate;
        TextView counterNumber;
        OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener listener)
        {
            super(itemView);
            counterName = itemView.findViewById(R.id.CounterName);
            counterDate = itemView.findViewById(R.id.CounterDate);
            counterNumber = itemView.findViewById(R.id.CounterNumber);
            this.onItemListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener
    {
        void onItemClick(int position);
    }
    public void addCounter(String name, String date, String number)
    {
        //counterNames.add(name);
        //counterDates.add(date);
        //counterNumbers.add(number);
        Log.d("AddCounter-debug",Integer.toString(counterNames.size()));
        this.notifyItemInserted(counterNames.size()-1);
    }

    public void updateCounter(int i, String name, String date, String number)
    {
        counterNames.set(i, name);
        counterDates.set(i, date);
        counterNumbers.set(i, number);
        this.notifyItemChanged(i);
    }
}
