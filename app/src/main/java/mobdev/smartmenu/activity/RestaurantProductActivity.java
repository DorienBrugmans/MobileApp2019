package mobdev.smartmenu.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import mobdev.smartmenu.R;
import mobdev.smartmenu.fragment.CategoriesFragment;
import mobdev.smartmenu.fragment.ProductsFragment;
import mobdev.smartmenu.fragment.RestaurantProductFragment;

public class RestaurantProductActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_product);

        RestaurantProductFragment restaurantProductFragment = new RestaurantProductFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.restaurantFragmentPlace, restaurantProductFragment);
        fragmentTransaction.commit();
    }
}
