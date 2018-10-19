package mobdev.smartmenu.activity;


import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import mobdev.smartmenu.fragment.CartFragment;
import mobdev.smartmenu.fragment.CategoriesFragment;
import mobdev.smartmenu.R;
import model.CartItem;

public class MasterActivity extends AppCompatActivity {

    TextView txtTafel;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static List<CartItem> cart;
    Button btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        txtTafel = (TextView) findViewById(R.id.txtTable);
        btnCart = (Button) findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment cartFragment = new CartFragment();

                fragmentManager = getSupportFragmentManager();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentPlace, cartFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cart = new LinkedList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        txtTafel.setText("" + sharedPreferences.getString("tafelID", ""));

        CategoriesFragment categoriesFragment = new CategoriesFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlace, categoriesFragment);
        fragmentTransaction.commit();
    }
}
