package mobdev.smartmenu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.viewholder.ProductViewHolder;
import mobdev.smartmenu.viewholder.RestaurantProductViewHolder;
import model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantProductFragment extends Fragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    RecyclerView listProducts;
    FirebaseRecyclerAdapter<Food, RestaurantProductViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference products;
    LinearLayoutManager layoutManager;
    View view;
    public RestaurantProductFragment() {
        // Required empty public constructor
    }
    public static RestaurantProductFragment newInstance() {
        RestaurantProductFragment fragment = new RestaurantProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        products = database.getReference("Food");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_product, container, false);   Bundle bundle = this.getArguments();

        listProducts = view.findViewById(R.id.restaurantProducts);
        listProducts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listProducts.setLayoutManager(layoutManager);
        loadProducts();

        return view;

    }
    private void loadProducts() {
        adapter = new FirebaseRecyclerAdapter<Food, RestaurantProductViewHolder>(Food.class, R.layout.restaurant_product_layout, RestaurantProductViewHolder.class, products) {
            @Override
            protected void populateViewHolder(RestaurantProductViewHolder viewHolder, final Food model, int position) {
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.product_image, new Callback() {
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
                        RestaurantProductDetailFragment restaurantProductDetailFragment = new RestaurantProductDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("productID", adapter.getRef(position).getKey());

                        restaurantProductDetailFragment.setArguments(bundle);
                        fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.restaurantFragmentPlace2, restaurantProductDetailFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        listProducts.setAdapter(adapter);
    }
}
