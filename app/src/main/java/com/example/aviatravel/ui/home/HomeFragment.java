package com.example.aviatravel.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.aviatravel.ItemFragment;
import com.example.aviatravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private Spinner flitefrom;
    private Spinner fliteto;
    private Button search;
    private EditText date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
         flitefrom = root.findViewById(R.id.flightfrom);
         fliteto = root.findViewById(R.id.flightto);
         search = root.findViewById(R.id.search);
         date = root.findViewById(R.id.date);
         search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ItemFragment fragment = new ItemFragment();
                 fragment.from = flitefrom.getSelectedItem().toString();
                 fragment.to = fliteto.getSelectedItem().toString();
                 fragment.date = date.getText().toString();
                 FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                 fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                 fragmentTransaction.commit();
             }
         });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Airports").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    final List<String> titleList = new ArrayList<String>();
                    for(DocumentSnapshot readData: queryDocumentSnapshots.getDocuments()){
                        String titlename = readData.get("Name").toString();
                        titleList.add(titlename);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeFragment.this.getActivity(), android.R.layout.simple_spinner_item, titleList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    flitefrom.setAdapter(arrayAdapter);
                    fliteto.setAdapter(arrayAdapter);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeFragment.this.getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Androidview", e.getMessage());
                    }
                });
        //db.collection("Airport")

         return root;
    }

}