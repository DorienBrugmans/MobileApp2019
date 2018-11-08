package mobdev.smartmenu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

    private Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_product_list);

        database = FirebaseDatabase.getInstance();
        products = database.getReference("Food");
        homeBtn = (Button) findViewById(R.id.homeBtn);

        if (findViewById(R.id.restaurant_product_detail_container) != null) {
            mTwoPane = true;
        }

        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.restaurant_product_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        setupRecyclerView((android.support.v7.widget.RecyclerView) recyclerView);

        // hom ebutton
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantProductActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // check orientation
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            startActivity(new Intent(RestaurantProductActivity.this, RestaurantProductActivity.class));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(new Intent(RestaurantProductActivity.this, RestaurantProductActivity.class));
        }
    }

    private void setupRecyclerView(android.support.v7.widget.RecyclerView recyclerView) {
        adapter = new FirebaseRecyclerAdapter<Food, RestaurantProductViewHolder>(Food.class,
                R.layout.restaurant_product_list_content, RestaurantProductViewHolder.class, products) {
            @Override
            protected void populateViewHolder(RestaurantProductViewHolder viewHolder, Food model, int position) {
                Picasso.with(RestaurantProductActivity.this).load(model.getImage()).into(viewHolder.product_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.product_progress.setVisibility(View.GONE);
                        viewHolder.product_name.setText(model.getName());
                    }

                    @Override
                    public void onError() {

                    }
                });

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // if landscape
                        if (mTwoPane) {
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
                            // if portrait
                            Context context = view.getContext();
                            Intent intent = new Intent(context, RestaurantProductDetailActivity.class);
                            intent.putExtra("restaurant_product_id", adapter.getRef(position).getKey());
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
