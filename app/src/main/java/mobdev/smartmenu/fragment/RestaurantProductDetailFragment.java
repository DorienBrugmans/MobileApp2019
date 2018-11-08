package mobdev.smartmenu.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import mobdev.smartmenu.R;
import mobdev.smartmenu.activity.RestaurantProductActivity;
import model.Food;

public class RestaurantProductDetailFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference product;
    ImageView product_image;
    EditText product_name;
    TextView product_categoryId;
    EditText product_description;
    EditText product_price;
    Button button_edit;
    Button button_delete;

    public RestaurantProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("restaurant_product_id")) {
            database = FirebaseDatabase.getInstance();
            product = database.getReference("Food");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_product_detail_content, container, false);

        product_image = (ImageView) view.findViewById(R.id.restaurantProductImage);
        product_name = (EditText) view.findViewById(R.id.restaurantProductName);
        product_categoryId = (TextView) view.findViewById(R.id.restaurantProductCatId);
        product_description = (EditText) view.findViewById(R.id.restaurantProductDesc);
        product_price = (EditText) view.findViewById(R.id.restaurantProductPrice);
        button_edit = (Button) view.findViewById(R.id.product_edit);
        button_delete = (Button) view.findViewById(R.id.product_delete);

        loadProduct();

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            startActivity(new Intent(getActivity(), RestaurantProductActivity.class));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(new Intent(getActivity(), RestaurantProductActivity.class));
        }
    }

    // load product
    private void loadProduct() {
        product.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product_name.setText(dataSnapshot.child(getArguments().getString("restaurant_product_id")).child("name").getValue(String.class));
                product_categoryId.setText(dataSnapshot.child(getArguments().getString("restaurant_product_id")).child("categoryId").getValue(String.class));
                product_description.setText(dataSnapshot.child(getArguments().getString("restaurant_product_id")).child("description").getValue(String.class));
                product_price.setText(dataSnapshot.child(getArguments().getString("restaurant_product_id")).child("price").getValue(String.class));
                Picasso.with(getActivity()).load(dataSnapshot.child(getArguments().getString("restaurant_product_id")).child("image").getValue(String.class)).into(product_image);
                button_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProduct(getArguments().getString("restaurant_product_id"), product_name.getText().toString(), product_price.getText().toString(), product_description.getText().toString(), dataSnapshot.child(getArguments().getString("restaurant_product_id")).child("image").getValue(String.class), "0", product_categoryId.getText().toString());
                    }
                });
                button_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteProduct(getArguments().getString("restaurant_product_id"));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

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

        Toast.makeText(getContext(), "Product updated successfully!", Toast.LENGTH_LONG).show();
    }

    private void deleteProduct(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food").child(id);
        databaseReference.removeValue();

        Toast.makeText(getContext(), "Product deleted successfully!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(getActivity(), RestaurantProductActivity.class));
    }
}
