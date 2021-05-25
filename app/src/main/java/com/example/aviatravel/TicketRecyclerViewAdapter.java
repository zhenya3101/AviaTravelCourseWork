package com.example.aviatravel;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aviatravel.TicketFragment.OnListFragmentInteractionListener;
import com.example.aviatravel.dummy.TicketContent.DummyItem;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TicketRecyclerViewAdapter extends RecyclerView.Adapter<TicketRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TicketRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).flight);
        holder.mFromView.setText(mValues.get(position).from);
        //holder.mTimeoutView.setContentDescription(mValues.get(position).time);
        //holder.mDateView.setContentDescription(mValues.get(position).date);
        holder.mToView.setText(mValues.get(position).to);
        holder.mPasView.setText(mValues.get(position).pas);


        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
        final int hours = Integer.parseInt(mValues.get(position).time.substring(0,2));
        final int min = Integer.parseInt(mValues.get(position).time.substring(3));
        final int day = Integer.parseInt(mValues.get(position).date.substring(0,2));
        final int mon = Integer.parseInt(mValues.get(position).date.substring(3,5));
        final int year = Integer.parseInt(mValues.get(position).date.substring(6));

        Calendar c = Calendar.getInstance();
        long ms = c.getTimeInMillis();
        c.set(year, mon, day, hours, min, 0);
        long msf = c.getTimeInMillis();
        int daysLeft = (int)((msf-ms)/8.64e+7);
        String hoursLeft = Integer.toString((int)(((msf-ms)%8.64e+7)/3.6e+6));
        String minutesLeft = Integer.toString((int)((((msf-ms)%8.64e+7)%3.6e+6)/60000));

        if (hoursLeft.length() < 2) {
            hoursLeft = "0" + hoursLeft;
        }

        if (minutesLeft.length() < 2) {
            minutesLeft = "0" + minutesLeft;
        }

        if (Integer.parseInt(minutesLeft)<=0)
            holder.mStatusView.setText("Завершено");
        else{
            holder.mDateView.setText(daysLeft + " днів ");
            holder.mTimeoutView.setText(hoursLeft + " : " + minutesLeft);
            holder.mStatusView.setText("Очікування");
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mFromView;
        public final TextView mToView;
        public final TextView mPasView;
        public final TextView mTimeoutView;
        public final TextView mDateView;
        public final TextView mStatusView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mFromView = (TextView) view.findViewById(R.id.from);
            mToView = (TextView) view.findViewById(R.id.to);
            mPasView = (TextView) view.findViewById(R.id.passenger);
            mTimeoutView = (TextView) view.findViewById(R.id.timeout);
            mDateView = (TextView) view.findViewById(R.id.date);
            mStatusView = (TextView) view.findViewById(R.id.status);
        }
    }
}
