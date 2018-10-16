package mobdev.smartmenu.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import mobdev.smartmenu.Activity.MasterActivity;
import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.ViewHolder.ProductDetailViewHolder;
import model.Food;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment implements AdapterView.OnItemClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button btnAddToCart;
    // View myFragment;
    RecyclerView productDetail;
    FirebaseRecyclerAdapter<Food, ProductDetailViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference product;
    LinearLayoutManager layoutManager;
    View view;
    String productId = "";
    EditText count;

    public static ProductDetailFragment newInstance() {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        return productDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        product = database.getReference("Food");
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        Bundle bundle = this.getArguments();

        productId = bundle.getString("productID", "0");
        productDetail = view.findViewById(R.id.layoutProductDetail);
        productDetail.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        productDetail.setLayoutManager(layoutManager);

        loadProduct();

        return view;
    }

    private void loadProduct() {
        adapter = new FirebaseRecyclerAdapter<Food, ProductDetailViewHolder>(Food.class, R.layout.product_detail_layout, ProductDetailViewHolder.class, product.orderByKey().equalTo(productId)) {
            @Override
            protected void populateViewHolder(final ProductDetailViewHolder viewHolder, final Food model, int position) {
                viewHolder.product_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.product_image);
                viewHolder.product_description.setText(model.getDescription());
                viewHolder.product_price.setText(model.getPrice());
                viewHolder.cartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MasterActivity.cart.put(productId,viewHolder.product_count.getText().toString());
                        Toast.makeText(getActivity(), MasterActivity.cart.get(productId), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
