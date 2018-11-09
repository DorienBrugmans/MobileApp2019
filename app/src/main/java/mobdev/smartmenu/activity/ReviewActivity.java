package mobdev.smartmenu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mobdev.smartmenu.R;

public class ReviewActivity extends AppCompatActivity {

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox;
    EditText comment;
    Button reviewButton;
    FirebaseDatabase database;
    DatabaseReference review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        database = FirebaseDatabase.getInstance();
        //first time Review table will be created if not exists
        review = database.getReference("Review");

        comment = (EditText) findViewById(R.id.reviewComment);
        reviewButton = (Button) findViewById(R.id.reviewButton);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkbox5);
        comment = (EditText) findViewById(R.id.reviewComment);
        reviewButton = (Button) findViewById(R.id.reviewButton);

        // review button
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if a checkbox is selected and comment field is not empty
                if (checkBox != null && !comment.getText().toString().equals("")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                    //create unique id
                    String value = "Review" + review.push().getKey();

                    review.child(value).child("reviewer").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    review.child(value).child("tableId").setValue(sharedPreferences.getString("tafelID", ""));
                    review.child(value).child("rating").setValue(checkBox.getText().toString());
                    review.child(value).child("comment").setValue(comment.getText().toString());

                    Toast.makeText(ReviewActivity.this, "Thanks for your review!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReviewActivity.this, MasterActivity.class));
                } else {
                    Toast.makeText(ReviewActivity.this, "Please select a value and enter a comment!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    //method to select only one checkbox
    public void onCheckboxClicked(View view) {
        switch (view.getId()) {
            case R.id.checkbox1:
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox = checkBox1;
                break;
            case R.id.checkbox2:
                checkBox1.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox = checkBox2;
                break;
            case R.id.checkbox3:
                checkBox2.setChecked(false);
                checkBox1.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox = checkBox3;
                break;
            case R.id.checkbox4:
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox1.setChecked(false);
                checkBox5.setChecked(false);
                checkBox = checkBox4;
                break;
            case R.id.checkbox5:
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox1.setChecked(false);
                checkBox = checkBox5;
                break;
        }
    }
}

