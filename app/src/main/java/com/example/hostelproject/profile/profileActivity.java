package com.example.hostelproject.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profileActivity extends AppCompatActivity {

    private static final String TAG = "profileActivity";
    private FirebaseAuth mAuth;
    private static final String KEY_NAME = "name";
    private static final String KEY_COLLEGE = "college";
    private static final String KEY_NUMBER = "number";
    private TextView name1;
    private TextView phone1;
    private TextView college1, college2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

        name1 = findViewById(R.id.profilename);
        phone1 = findViewById(R.id.profilephone);
        college1 = findViewById(R.id.profilecollege);
        TextView editprofile1 = findViewById(R.id.updateprofile);

        editprofile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEditProfile();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNote();
    }

    private void toEditProfile() {
        startActivity(new Intent(profileActivity.this, editProfileActivity.class));
    }


    public void loadNote() {
        if (mAuth.getCurrentUser().getUid().isEmpty()){
            toEditProfile();
        }
        else {
            Task<DocumentSnapshot> noteRef = FirebaseFirestore.getInstance().collection("Users")
                    .document(mAuth.getCurrentUser().getUid()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if(documentSnapshot.exists()){
                            String name2 = documentSnapshot.getString(KEY_NAME);
                            String number2 = documentSnapshot.getString(KEY_NUMBER);
                            String college2 = documentSnapshot.getString(KEY_COLLEGE);

                            name1.setText(name2);
                            phone1.setText(number2);
                            college1.setText(college2);

                            Toast.makeText(profileActivity.this, "Refresh successful.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(profileActivity.this, "This document does not exist.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(profileActivity.this, "Refresh unsuccessful.", Toast.LENGTH_SHORT).show());
        }

    }
}