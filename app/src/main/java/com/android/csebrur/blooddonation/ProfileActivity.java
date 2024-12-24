package com.android.csebrur.blooddonation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {
    TextInputEditText fullName, address, phone, email, DOB, LDD, blood;
    TextView nameHeadline;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("My Profile");

        fullName = findViewById(R.id.editName);
        address = findViewById(R.id.editAddress);
        phone = findViewById(R.id.editPhone);
        email = findViewById(R.id.editEmail);
        DOB = findViewById(R.id.editDOB);
        LDD = findViewById(R.id.editLDD);
        blood = findViewById(R.id.editBlood);
        nameHeadline = findViewById(R.id.hName);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                nameHeadline.setText(documentSnapshot.getString("Name"));
                fullName.setText(documentSnapshot.getString("Name"));
                address.setText(documentSnapshot.getString("Address"));
                phone.setText(documentSnapshot.getString("Phone"));
                email.setText(documentSnapshot.getString("Email"));
                DOB.setText(documentSnapshot.getString("DateOfBirth"));
                LDD.setText(documentSnapshot.getString("LastDonationDate"));
                blood.setText(documentSnapshot.getString("BloodGroup"));
            }
        });
    }
}