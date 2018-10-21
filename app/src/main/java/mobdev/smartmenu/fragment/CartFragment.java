package mobdev.smartmenu.fragment;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    public static List<CartItem> cart;
    View myFragment;
    RecyclerView cartRecyclerView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    FirebaseDatabase database;
    DatabaseReference products;

    Button orderbtn;
    private String CHANNEL_ID="personal_notification";
    private final int NOTIFICATION_ID=001;

    public static TextView price;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        products = database.getReference("Order");

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

        orderbtn.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", MODE_PRIVATE);

            String value = "Order" + products.push().getKey();
            products.child(value).child("item").setValue(MasterActivity.cart);

            products.child(value).child("tableId").setValue(sharedPreferences.getString("tafelID", ""));

            MasterActivity.cart.clear();


            Toast.makeText(getActivity(), "Order is placed", Toast.LENGTH_LONG).show();
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            fragmentManager = getActivity().getSupportFragmentManager();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentPlace, categoriesFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            notification("Thanks","Order is preparing");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   notification("hakan","calisiyo");
                }
            },10000);

        });



        return myFragment;
    }

    public static double SumPrice(List<CartItem> crt) {
        double total = 0;

        for (int i = 0; i < crt.size(); i++) {
            total += ((Double.parseDouble(crt.get(i).getProductCount()) * Double.parseDouble(crt.get(i).getProduct().getPrice())));

        }

        return total;
    }
    public void notification(String title,String description){

        final String NOTIFICATION_CHANNEL_ID = "4565";
        //Notification Channel
        String channelName = "personal channel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});


        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getActivity(), NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getActivity());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
}

