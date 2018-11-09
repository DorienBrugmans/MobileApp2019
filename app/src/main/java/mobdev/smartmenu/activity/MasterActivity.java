package mobdev.smartmenu.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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
    Button btnCart, btnCategorie, btnLogOut;
    ImageView homeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        txtTafel = (TextView) findViewById(R.id.txtTable);
        btnCart = (Button) findViewById(R.id.btnCart);
        btnCategorie = (Button) findViewById(R.id.btnCategorie);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        homeImage = (ImageView) findViewById(R.id.imageView2);

        // home button
        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasterActivity.this, MainActivity.class));
            }
        });

        // cart button
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


        // category button
        btnCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesFragment categoriesFragment = new CategoriesFragment();

                fragmentManager = getSupportFragmentManager();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentPlace, categoriesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // log out button
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MasterActivity.this);
                alertDialogBuilder.setTitle("SmartMenu");
                alertDialogBuilder.setIcon(R.drawable.logo);
                alertDialogBuilder.setMessage("Are you sure you want to log out?");
                alertDialogBuilder.setCancelable(true);

                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MasterActivity.this, AccountSignUpActivity.class));
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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
