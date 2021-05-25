package com.example.aviatravel;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviatravel.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlankFragment extends Fragment {

    String NameF;
    public static BlankFragment newInstance() {
        return new BlankFragment();
    }
    FirebaseFirestore db;
    Map<String, Object> docData = new HashMap<>();
    AutoCompleteTextView name;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.blank_fragment, container, false);

        Button suc = (Button)root.findViewById(R.id.sucsses);
        EditText number = (EditText)root.findViewById(R.id.number);
        name = (AutoCompleteTextView)root.findViewById(R.id.name);
        TextView flight = (TextView)root.findViewById(R.id.flight);
        number.setText(auth.getCurrentUser().getPhoneNumber());
        flight.setText(NameF);
        db = FirebaseFirestore.getInstance();
        db.collection("Passengers").whereEqualTo("Number", auth.getCurrentUser().getPhoneNumber()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    final List<String> titleList = new ArrayList<String>();
                    for(DocumentSnapshot readData: queryDocumentSnapshots.getDocuments()){
                        String titlename = readData.get("Name").toString();
                        titleList.add(titlename);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BlankFragment.this.getActivity(), android.R.layout.simple_spinner_item, titleList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    name.setAdapter(arrayAdapter);
                }
            }
        });
        db.collection("Flight").whereEqualTo("Name",NameF).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    for(DocumentSnapshot readData: queryDocumentSnapshots.getDocuments()){
                        docData.put("Date",readData.get("Date").toString());
                        docData.put("Flight", readData.get("Name").toString());
                        docData.put("From", readData.get("From").toString());
                        docData.put("To", readData.get("To").toString());
                        docData.put("Time", readData.get("Time").toString());
                        docData.put("Number", auth.getCurrentUser().getPhoneNumber());
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BlankFragment.this.getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Androidview", e.getMessage());
            }
        });
        suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> Data = new HashMap<>();
                Data.put("Name", name.getText().toString());
                Data.put("Number", auth.getCurrentUser().getPhoneNumber());
                docData.put("Passenger", name.getText().toString());
                db.collection("Passengers").add(Data);
                db.collection("Tickets")
                        .add(docData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference aVoid) {
                                HomeFragment fragment = new HomeFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                fragmentTransaction.commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        });
        return root;
    }

}
