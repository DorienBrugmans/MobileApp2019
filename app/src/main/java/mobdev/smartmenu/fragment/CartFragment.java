package mobdev.smartmenu.fragment;


import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;

import mobdev.smartmenu.CartAdapter;
import mobdev.smartmenu.R;
import mobdev.smartmenu.activity.MasterActivity;
import model.CartItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    public static List<CartItem> cart;
    View myFragment;
    RecyclerView cartRecyclerView;
    LinearLayoutManager layoutManager;

    RecyclerView productDetail;
    FirebaseDatabase database;
    DatabaseReference product;

    Button orderbtn;
    public static TextView price;

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

        CartAdapter cartAdapter = new CartAdapter();
        cartRecyclerView.setAdapter(cartAdapter);
        RecyclerView.LayoutManager lytManager = new LinearLayoutManager(getActivity());
        cartRecyclerView.setLayoutManager(lytManager);


        orderbtn = (Button) myFragment.findViewById(R.id.orderBtn);
        price = (TextView) myFragment.findViewById(R.id.orderPrice);

        price.setText("" + SumPrice(cart));


        return myFragment;
    }

    public static double SumPrice(List<CartItem> crt) {
        double total = 0;

        for (int i = 0; i < crt.size(); i++) {
            total += ((Double.parseDouble(crt.get(i).getProductCount()) * Double.parseDouble(crt.get(i).getProduct().getPrice())));

        }

        return total;
    }
}

