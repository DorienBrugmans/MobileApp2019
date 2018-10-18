package mobdev.smartmenu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.activity.MasterActivity;
import mobdev.smartmenu.viewholder.CartViewHolder;
import mobdev.smartmenu.viewholder.ProductViewHolder;
import model.CartItem;
import model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    public static List<CartItem> cart;
    View myFragment;
    RecyclerView cartRecyclerView;
    LinearLayoutManager layoutManager;
    RecyclerView.Adapter<CartItem> adapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cart = MasterActivity.cart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = (RecyclerView) myFragment.findViewById(R.id.layoutCart);
        cartRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        cartRecyclerView.setLayoutManager(layoutManager);

        loadProducts();

        return myFragment;
    }

    private void loadProducts() {
        adapter=new RecyclerView.Adapter<CartItem>(CartItem.class,R.layout.product_cart_layout,CartViewHolder.class,cart) {
            @Override
            protected void populateViewHolder(CartViewHolder viewHolder, final CartItem model, int position) {
                viewHolder.cart_count.setText(model.getProductCount());
                viewHolder.cart_product_name.setText(model.getProduct().getName());

               /* viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("productID", adapter.getRef(position).getKey());

                        productDetailFragment.setArguments(bundle);
                        fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentPlace, productDetailFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    }
                });*/

            }
        };

        adapter.notifyDataSetChanged();
        cartRecyclerView.setAdapter(adapter);
    }

}

