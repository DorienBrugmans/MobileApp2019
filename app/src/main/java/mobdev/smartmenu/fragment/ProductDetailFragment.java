package mobdev.smartmenu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import org.w3c.dom.Text;

import mobdev.smartmenu.activity.MasterActivity;
import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.viewholder.ProductDetailViewHolder;
import model.CartItem;
import model.Food;


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
                        if (!viewHolder.product_count.getText().toString().equals("0")){
                            if (!TextUtils.isEmpty(viewHolder.product_count.getText())){
                                if (MasterActivity.cart.stream().filter(p->p.getProduct().equals(model)).findFirst().isPresent()){
                                    Toast.makeText(getActivity(), "You already added this item to cart", Toast.LENGTH_SHORT).show();
                                }else{
                                    MasterActivity.cart.add(new CartItem(model, viewHolder.product_count.getText().toString()));
                                    Toast.makeText(getActivity(), viewHolder.product_count.getText().toString() + " x " + viewHolder.product_name.getText().toString() + " added to cart..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });

             /*   viewHolder.cartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MasterActivity.cart.add(new CartItem(model,viewHolder.product_count.getText().toString()));
                        CartFragment productDetailFragment = new CartFragment();

                        fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentPlace, productDetailFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();  }
                });*/

            }
        };

        adapter.notifyDataSetChanged();
        productDetail.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
