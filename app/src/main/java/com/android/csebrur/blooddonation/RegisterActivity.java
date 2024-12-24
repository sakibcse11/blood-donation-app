package com.android.csebrur.blooddonation;

import static android.content.ContentValues.TAG;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText mFullName, mPhone, mEmail, mBirthDate, mLastDate, mPassword;
    AutoCompleteTextView mBloodGroup,mAddress;
    Button mRegisterBtn;
    TextView mLoginNow;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    int bYear,bMonth,bDay,dYear,dMonth,dDay;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");

        mFullName = findViewById(R.id.PersonName);
        mPhone = findViewById(R.id.Phone);
        mAddress = findViewById(R.id.district);
        mEmail = findViewById(R.id.userEmail);
        mBirthDate = findViewById(R.id.BirthDate);
        mLastDate = findViewById(R.id.LastDate);
        mPassword = findViewById(R.id.userPassword);
        mBloodGroup = findViewById(R.id.BloodGroup);
        mRegisterBtn = findViewById(R.id.buttonRegister);
        mLoginNow = findViewById(R.id.loginHere);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);


        //City Drop Down
        String[] city = {"Bagerhat","Bandarban","Barguna","Barishal","Bhola","Bogura","Brahmanbaria","Chandpur",
                "Chapai Nawabganj","Chittagong","Chuadanga","Cox's Bazar","CumillaDhaka","Dinajpur",
                "Faridpur","Feni","Gaibandha","Gazipur","Gopalganj","Habiganj","Jamalpur","Jashore",
                "Jhalakathi","Jhenaidah", "Joypurhat","Khagrachhari","Khulna","Kishoreganj","Kurigram",
                "Kushtia","Lakshmipur","Lalmonirhat","Madaripur","Magura","Manikganj","Meherpur","Moulvibazar",
                "Munshiganj","Mymensingh","Naogaon","Narail","Narayanganj","Narsingdi","Natore","Netrakona",
                "Nilphamari","Noakhali","Pabna","Panchagarh","Patuakhali","Pirojpur","Rajbari","Rajshahi",
                "Rangamati","Rangpur ","Satkhira","Shariatpur","Sherpur","Sirajganj","Sunamganj","Sylhet",
                "Tangail","Thakurgaon"};

        ArrayAdapter<String> adapterCity;
        adapterCity = new ArrayAdapter<String>(this,R.layout.show_list,city);
        mAddress.setAdapter(adapterCity);
        mAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String city= parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "District: "+city, Toast.LENGTH_SHORT).show();
            }
        });
        //Blood Group Drop Down
        String[] bGroup = {"A+","A-", "B+","B-","AB+","AB-","O+","O-"};
        ArrayAdapter<String> adapterGroup;
        adapterGroup = new ArrayAdapter<String>(this,R.layout.show_list,bGroup);
        mBloodGroup.setAdapter(adapterGroup);
        mBloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String blood= parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Blood: "+blood, Toast.LENGTH_SHORT).show();
            }
        });

        //Date Picker

        Calendar cal = Calendar.getInstance();
        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               bYear = cal.get(Calendar.YEAR);
               bMonth = cal.get(Calendar.MONTH);
               bDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(RegisterActivity.this,R.style.datePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    month= month+1;
                    String date= dayOfMonth+"/"+month+"/"+year;
                    mBirthDate.setText(date);
                    }
                },bYear,bMonth,bDay);
                datePicker.show();
            }
        });
        //last Donation
        mLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dYear = cal.get(Calendar.YEAR);
                dMonth = cal.get(Calendar.MONTH);
                dDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(RegisterActivity.this,R.style.datePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month= month+1;
                        String date= dayOfMonth+"/"+month+"/"+year;
                        mLastDate.setText(date);
                    }
                },dYear,dMonth,dDay);
                datePicker.show();
            }
        });


        mRegisterBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String name = mFullName.getText().toString();
            String phone = mPhone.getText().toString();
            String address = mAddress.getText().toString();
            String DoB = mBirthDate.getText().toString();
            String LdD = mLastDate.getText().toString();
            String blood = mBloodGroup.getText().toString();

            if (TextUtils.isEmpty(name)) {
                mFullName.setError("Full Name is required");
            }
            if (phone.length()<11) {
                mPhone.setError("Enter Valid Phone Number");
            }
            if (TextUtils.isEmpty(address)) {
                mFullName.setError("Address is required");
            }
            if (TextUtils.isEmpty(DoB)) {
                mFullName.setError("Date of Birth is required");
            }
            if (TextUtils.isEmpty(blood)) {
                mFullName.setError("Blood Group is required");
            }
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is required");
            }
            if (password.length() < 6) {
                mPassword.setError("Password must have 6 characters long");
            }
            progressBar.setVisibility(View.VISIBLE);

            //register user
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //Store Data in Firestore
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference =fStore.collection("users").document(userID);
                    Map<String,Object> user= new HashMap<>();
                    user.put("Name",name);
                    user.put("Phone",phone);
                    user.put("Email",email);
                    user.put("Address",address);
                    user.put("DateOfBirth",DoB);
                    user.put("BloodGroup",blood);
                    user.put("LastDonationDate",LdD);
                    user.put("Password",password);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"on Success: All data stored in "+userID);
                        }
                    });


                            //send verification email
                    FirebaseUser fUser = fAuth.getCurrentUser();
                    fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this,"Verification Email Has Been Sent.",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent"+e.getMessage());

                        }
                    });

                            //user created
                    Toast.makeText(RegisterActivity.this, "Verify your Email Address", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "ERROR! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        });
    mLoginNow.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
    }
}