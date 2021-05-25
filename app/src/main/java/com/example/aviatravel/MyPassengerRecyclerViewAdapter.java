package com.example.aviatravel;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aviatravel.PassengerFragment.OnPassengerFragmentInteractionListener;
import com.example.aviatravel.dummy.DummyContent.Item;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item} and makes a call to the
 * specified {@link OnPassengerFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPassengerRecyclerViewAdapter extends RecyclerView.Adapter<MyPassengerRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mValues;
    private final OnPassengerFragmentInteractionListener mListener;

    public MyPassengerRecyclerViewAdapter(List<Item> items, OnPassengerFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_passenger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
