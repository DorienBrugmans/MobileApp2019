package mobdev.smartmenu.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import mobdev.smartmenu.R;
import mobdev.smartmenu.fragment.RestaurantProductDetailFragment;
import mobdev.smartmenu.viewholder.RestaurantProductDetailViewHolder;
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // check orientation
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            startActivity(new Intent(RestaurantProductDetailActivity.this, RestaurantProductActivity.class));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(new Intent(RestaurantProductDetailActivity.this, RestaurantProductActivity.class));
        }
    }

    private void setupRecyclerView(android.support.v7.widget.RecyclerView recyclerView) {
        adapter = new FirebaseRecyclerAdapter<Food, RestaurantProductDetailViewHolder>(Food.class,
                R.layout.restaurant_product_detail_content, RestaurantProductDetailViewHolder.class, product.orderByKey().equalTo(getIntent().getStringExtra("restaurant_product_id"))) {
            @Override
            protected void populateViewHolder(RestaurantProductDetailViewHolder viewHolder, Food model, int position) {
                Picasso.with(RestaurantProductDetailActivity.this).load(model.getImage()).into(viewHolder.product_image);
                viewHolder.product_name.setText(model.getName());
                viewHolder.product_categoryId.setText(model.getCategoryId());
                viewHolder.product_description.setText(model.getDescription());
                viewHolder.product_price.setText(model.getPrice());

                // edit button
                viewHolder.button_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProduct(getIntent().getStringExtra("restaurant_product_id"), viewHolder.product_name.getText().toString(), viewHolder.product_price.getText().toString(), viewHolder.product_description.getText().toString(), model.getImage(), "0", viewHolder.product_categoryId.getText().toString());
                    }
                });

                // delete button
                viewHolder.button_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteProduct(getIntent().getStringExtra("restaurant_product_id"));
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    // update method
    private void updateProduct(String id, String name, String price, String description, String image, String discount, String categoryId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food").child(id);
        Food food = new Food();

        food.setName(name);
        food.setImage(image);
        food.setPrice(price);
        food.setDescription(description);
        food.setCategoryId(categoryId);
        food.setImage(image);
        food.setDiscount(discount);

        databaseReference.setValue(food);

        Toast.makeText(RestaurantProductDetailActivity.this, "Product updated successfully!", Toast.LENGTH_LONG).show();
    }

    // delete method
    private void deleteProduct(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food").child(id);
        databaseReference.removeValue();

        Toast.makeText(RestaurantProductDetailActivity.this, "Product deleted successfully!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(RestaurantProductDetailActivity.this, RestaurantProductActivity.class));
    }

}
