package mobdev.smartmenu.Activity;


import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import mobdev.smartmenu.Fragment.CategoriesFragment;
import mobdev.smartmenu.R;
import model.Food;

public class MasterActivity extends AppCompatActivity {

    TextView txtTafel;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static HashMap<String,String> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        txtTafel = (TextView) findViewById(R.id.txtTable);
        cart = new HashMap<>();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        txtTafel.setText("" + sharedPreferences.getString("tafelID", ""));

        CategoriesFragment categoriesFragment = new CategoriesFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlace, categoriesFragment);
        fragmentTransaction.commit();
    }
}
