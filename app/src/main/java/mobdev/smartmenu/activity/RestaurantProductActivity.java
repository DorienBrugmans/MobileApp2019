package mobdev.smartmenu.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.fragment.RestaurantProductDetailFragment;
import mobdev.smartmenu.viewholder.RestaurantProductViewHolder;
import model.Food;

public class RestaurantProductActivity extends AppCompatActivity {

    private boolean mTwoPane;

    public FirebaseRecyclerAdapter<Food, RestaurantProductViewHolder> adapter;
    public FirebaseDatabase database;
    public DatabaseReference products;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    public android.support.v7.widget.RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_product_list);

        database = FirebaseDatabase.getInstance();
        products = database.getReference("Food");

        if(findViewById(R.id.restaurant_product_detail_container) != null) {
            mTwoPane = true;
        }

        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.restaurant_product_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        setupRecyclerView((android.support.v7.widget.RecyclerView) recyclerView);
    }

    private void setupRecyclerView(android.support.v7.widget.RecyclerView recyclerView) {
        adapter = new FirebaseRecyclerAdapter<Food, RestaurantProductViewHolder>(Food.class,
                R.layout.restaurant_product_list_content, RestaurantProductViewHolder.class, products) {
            @Override
            protected void populateViewHolder(RestaurantProductViewHolder viewHolder, Food model, int position) {
                viewHolder.product_name.setText(model.getName());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        if(mTwoPane) {
                            Bundle arg = new Bundle();
                            arg.putString("restaurant_product_id", adapter.getRef(position).getKey());
                            RestaurantProductDetailFragment fragment = new RestaurantProductDetailFragment();
                            fragment.setArguments(arg);

                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.restaurant_product_detail_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, RestaurantProductDetailActivity.class);
                            intent.putExtra("restaurant_product_id" , adapter.getRef(position).getKey());
                            context.startActivity(intent);
                        }
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
