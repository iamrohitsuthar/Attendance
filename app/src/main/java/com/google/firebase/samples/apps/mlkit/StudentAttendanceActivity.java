package com.google.firebase.samples.apps.mlkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;

import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.samples.apps.mlkit.adapters.StudentAttendanceAdapter;
import com.google.firebase.samples.apps.mlkit.models.StudentAttendanceModel;
import com.google.firebase.samples.apps.mlkit.models.StudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectInStudentModel;
import com.google.firebase.samples.apps.mlkit.models.SubjectModel;
import com.google.firebase.samples.apps.mlkit.others.SharedPref;

import java.util.ArrayList;

public class StudentAttendanceActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentCollection = db.collection("studentCollection");
    private CollectionReference subjectCollection = db.collection("subjectCollection");

    private RecyclerView mRecyclerView;
    private StudentAttendanceAdapter studentAttendanceAdapter;
    private TextView studentNameTextView;
    private String studentName;
    private SharedPref sharedPref;
    private Context mContext;
    private String studentId;
    private ArrayList<Integer> attendedCount = new ArrayList<>();
    private ArrayList<Integer> totalCount = new ArrayList<>();
    private ArrayList<String> nameOfSubject = new ArrayList<>();
    private ArrayList<SubjectInStudentModel> subjects = new ArrayList<>();
    CardView profileCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studence_attendance);

        getSupportActionBar().setTitle("StudentModel Attendance");
        mContext = this;
        studentNameTextView = (TextView) findViewById(R.id.textView2);

        sharedPref=new SharedPref(mContext);

        studentName = sharedPref.getNAME();
        studentId = sharedPref.getID();
        studentNameTextView = findViewById(R.id.tv_student_name);
        studentNameTextView.setText(studentName);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileCard = findViewById(R.id.cv_student_info);
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAttendanceActivity.this,ProfileActivity.class));
            }
        });

        studentAttendanceAdapter = new StudentAttendanceAdapter(this, getMyList());

        mRecyclerView.setAdapter(studentAttendanceAdapter);

    }

    public ArrayList<StudentAttendanceModel> getMyList() {

        studentCollection.whereEqualTo("id",studentId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                StudentModel student = documentSnapshot.toObject(StudentModel.class);
                subjects = student.getSubjects();

            }
        });

        for(SubjectInStudentModel subject : subjects)
        {
            int subjectId = subject.getId();
            attendedCount.add(subject.getDates().size());
            subjectCollection.whereEqualTo("id",subjectId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    SubjectModel subjectModel = documentSnapshot.toObject(SubjectModel.class);
                    totalCount.add(subjectModel.getDates().size());
                    nameOfSubject.add(subjectModel.getName());
                }
            });

        }

        ArrayList<StudentAttendanceModel> studentAttendanceModels = new ArrayList<>();
        for(int i=0;i<attendedCount.size();i++)
        {
            StudentAttendanceModel studentAttendanceModel = new StudentAttendanceModel();
            studentAttendanceModel.setSubject(nameOfSubject.get(i));
            float percentage = attendedCount.get(i)/totalCount.get(i);
            percentage = percentage*100;

            studentAttendanceModel.setPercent(Float.toString(percentage));
            studentAttendanceModels.add(studentAttendanceModel);
        }

        return studentAttendanceModels;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.student_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.myprofile :

                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                return true;

            case R.id.logout:

                sharedPref.logout();
                sharedPref.setIsLoggedIn(false);
                Intent intent = new Intent(StudentAttendanceActivity.this, LoginActivity.class);
                finishAffinity();
                startActivity(intent);
                finish();

                return true;

            default:
                return false;
        }
    }

}
