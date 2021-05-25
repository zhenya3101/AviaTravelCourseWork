package com.example.aviatravel;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aviatravel.dummy.DummyContent.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPassengerFragmentInteractionListener}
 * interface.
 */
public class PassengerFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnPassengerFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PassengerFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PassengerFragment newInstance(int columnCount) {
        PassengerFragment fragment = new PassengerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_passenger_list, container, false);

        // Set the adapter
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Passengers").whereEqualTo("Number", auth.getCurrentUser().getPhoneNumber()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    final List<Item> list = new ArrayList<Item>();
                    for(DocumentSnapshot readData: queryDocumentSnapshots.getDocuments()){
                        list.add(new Item(readData.get("Name").toString(),
                                readData.get("Number").toString()));
                    }
                    recyclerView.setAdapter(new MyPassengerRecyclerViewAdapter(list,mListener));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PassengerFragment.this.getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Androidview", e.getMessage());
            }
        });
        return recyclerView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPassengerFragmentInteractionListener) {
            mListener = (OnPassengerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPassengerFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPassengerFragmentInteraction(Item item);
    }
}
