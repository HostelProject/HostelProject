package com.example.hostelproject.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hostelproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editProfileActivity extends AppCompatActivity {

    private EditText name, phone, college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        college = findViewById(R.id.collegename);
        Button save = findViewById(R.id.profilesave);

        save.setOnClickListener(v -> upload());
    }

    private void upload() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth kAuth = FirebaseAuth.getInstance();
        String names = name.getText().toString().trim();
        String numbers = phone.getText().toString().trim();
        String colleges = college.getText().toString().trim();
        String userID = kAuth.getCurrentUser().getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("name", names);
        user.put("number", numbers);
        user.put("college", colleges);
        db.collection("Users").document(userID).set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText(editProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show());

        startActivity(new Intent(this, profileActivity.class));
    }
}