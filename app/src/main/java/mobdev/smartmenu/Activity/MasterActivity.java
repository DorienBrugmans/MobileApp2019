package mobdev.smartmenu.Activity;


import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import mobdev.smartmenu.Fragment.CategoriesFragment;
import mobdev.smartmenu.R;

public class MasterActivity extends AppCompatActivity {

    TextView txtTafel;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        txtTafel = (TextView) findViewById(R.id.txtTable);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        txtTafel.setText("" + sharedPreferences.getString("tafelID", ""));

        CategoriesFragment categoriesFragment = new CategoriesFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlace, categoriesFragment);
        fragmentTransaction.commit();
    }
}
