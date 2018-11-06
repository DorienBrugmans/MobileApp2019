package mobdev.smartmenu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.fragment.RestaurantProductDetailFragment;
import mobdev.smartmenu.viewholder.RestaurantProductDetailViewHolder;
import mobdev.smartmenu.viewholder.RestaurantProductViewHolder;
import model.Food;

public class RestaurantProductDetailActivity extends AppCompatActivity {

    public FirebaseRecyclerAdapter<Food, RestaurantProductDetailViewHolder> adapter;
    public FirebaseDatabase database;
    public DatabaseReference product;
    public android.support.v7.widget.RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_product_detail);

        database = FirebaseDatabase.getInstance();
        product = database.getReference("Food");

        Bundle arg = new Bundle();
        arg.putString("restaurant_product_id", getIntent().getStringExtra("restaurant_product_id"));

        if (savedInstanceState != null) {
            Bundle arguments = new Bundle();
            arguments.putString("restaurant_product_id",
                    getIntent().getStringExtra("restaurant_product_id"));
            RestaurantProductDetailFragment fragment = new RestaurantProductDetailFragment();
            fragment.setArguments(arg);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.restaurant_product_detail_container, fragment)
                    .commit();
        }

        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.restaurant_product_detail_container);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        setupRecyclerView((android.support.v7.widget.RecyclerView) recyclerView);
    }

    private void setupRecyclerView(android.support.v7.widget.RecyclerView recyclerView) {
        adapter = new FirebaseRecyclerAdapter<Food, RestaurantProductDetailViewHolder>(Food.class,
                R.layout.restaurant_product_detail_content, RestaurantProductDetailViewHolder.class, product.orderByKey().equalTo(getIntent().getStringExtra("restaurant_product_id"))) {
            @Override
            protected void populateViewHolder(RestaurantProductDetailViewHolder viewHolder, Food model, int position) {
                viewHolder.product_name.setText(model.getName());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
