package mobdev.smartmenu;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlace, categoriesFragment);
        fragmentTransaction.commit();
    }
}
