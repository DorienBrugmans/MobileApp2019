package mobdev.smartmenu.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.activity.MasterActivity;
import mobdev.smartmenu.viewholder.ProductDetailViewHolder;
import mobdev.smartmenu.viewholder.RestaurantProductDetailViewHolder;
import model.CartItem;
import model.Food;


public class RestaurantProductDetailFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    RecyclerView productDetail;
    FirebaseRecyclerAdapter<Food, RestaurantProductDetailViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference product;
    LinearLayoutManager layoutManager;
    View view;
    String productId = "";
    public RestaurantProductDetailFragment() {
        // Required empty public constructor
    }

    public static RestaurantProductDetailFragment newInstance() {
        RestaurantProductDetailFragment fragment = new RestaurantProductDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        product = database.getReference("Food");

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_product_detail, container, false);


        Bundle bundle = this.getArguments();

        productId = bundle.getString("productID", "0");
        productDetail = view.findViewById(R.id.restaurantProductDetail);
        productDetail.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        productDetail.setLayoutManager(layoutManager);

        loadProduct();

        return view;
    }
    private void loadProduct() {
        adapter = new FirebaseRecyclerAdapter<Food, RestaurantProductDetailViewHolder>(Food.class, R.layout.restaurant_product_detail_layout, RestaurantProductDetailViewHolder.class, product.orderByKey().equalTo(productId)) {
            @Override
            protected void populateViewHolder(final RestaurantProductDetailViewHolder viewHolder, final Food model, int position) {
                viewHolder.product_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.product_image);
                viewHolder.product_description.setText(model.getDescription());
                viewHolder.product_category.setText(model.getCategoryId());
                viewHolder.product_price.setText("€ " + model.getPrice());

                viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       updateProduct(productId,viewHolder.product_name.getText().toString(),viewHolder.product_price.getText().toString().substring(2),viewHolder.product_description.getText().toString(),model.getImage(),"0",viewHolder.product_category.getText().toString());
                    }
                });
                viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteProduct(productId);
                    }
                });
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        productDetail.setAdapter(adapter);
    }
    private boolean updateProduct(String id,String name,String price,String description,String image,String discount,String categoryId){
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
    }
}
