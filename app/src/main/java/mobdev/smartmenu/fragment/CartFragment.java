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

import mobdev.smartmenu.CartAdapter;
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
        cartRecyclerView.setAdapter(new CartAdapter(☻));

        return myFragment;
    }


}

