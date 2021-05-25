package com.example.aviatravel;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aviatravel.ItemFragment.OnListFragmentInteractionListener;
import com.example.aviatravel.dummy.Content.Item;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mValues;
    private final OnListFragmentInteractionListener mListener;
    FragmentManager fragmentManager;

    public MyItemRecyclerViewAdapter(List<Item> items, OnListFragmentInteractionListener listener, FragmentManager fragment) {
        mValues = items;
        mListener = listener;
        fragmentManager = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mCostView.setText(mValues.get(position).cost + "ГРН");
        holder.mTimeView.setText(mValues.get(position).time);
        holder.mButtonView.setContentDescription(mValues.get(position).iddb);

        holder.mButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    BlankFragment fragment = new BlankFragment();
                    fragment.NameF = holder.mItem.id;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                    fragmentTransaction.commit();
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mCostView;
        public final TextView mTimeView;
        public final Button mButtonView;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mCostView = (TextView) view.findViewById(R.id.cost);
            mTimeView = (TextView) view.findViewById(R.id.time);
            mButtonView = (Button) view.findViewById(R.id.but);
        }
    }
}
