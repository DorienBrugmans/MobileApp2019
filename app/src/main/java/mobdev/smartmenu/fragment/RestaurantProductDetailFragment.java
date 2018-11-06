package mobdev.smartmenu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mobdev.smartmenu.R;

public class RestaurantProductDetailFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference product;

    TextView product_name;

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
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_product_detail_content, container, false);

        product_name = (TextView) view.findViewById(R.id.restaurant_product_detail_content);

        loadProduct();

        return view;
    }

    private void loadProduct() {
        product.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product_name.setText(dataSnapshot.child(getArguments().getString("restaurant_product_id")).child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*private boolean updateProduct(String id,String name,String price,String description,String image,String discount,String categoryId){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Food").child(id);
        Food food=new Food();
        food.setName(name);
        food.setImage(image);
        food.setPrice(price);
        food.setDescription(description);
        food.setCategoryId(categoryId);
        food.setImage(image);
        food.setDiscount(discount);
        databaseReference.setValue(food);
        Toast.makeText(getContext(),"Product updated successfully",Toast.LENGTH_LONG).show();
        return true;
    }
    private void deleteProduct(String id){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Food").child(id);
        databaseReference.removeValue();
        Toast.makeText(getContext(),"Product deleted successfully",Toast.LENGTH_LONG).show();

        RestaurantProductFragment restaurantProductFragment = new RestaurantProductFragment();
        fragmentManager = getActivity().getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.restaurantFragmentPlace, restaurantProductFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });*/
}
