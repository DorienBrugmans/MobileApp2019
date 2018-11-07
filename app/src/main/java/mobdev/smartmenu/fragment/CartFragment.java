package mobdev.smartmenu.fragment;


import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;

import mobdev.smartmenu.CartAdapter;
import mobdev.smartmenu.R;
import mobdev.smartmenu.activity.MainActivity;
import mobdev.smartmenu.activity.MasterActivity;
import mobdev.smartmenu.activity.ReviewActivity;
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
    private final int NOTIFICATION_ID = 001;
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
        myFragment = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = (RecyclerView) myFragment.findViewById(R.id.layoutCart);

        CartAdapter cartAdapter = new CartAdapter();
        cartRecyclerView.setAdapter(cartAdapter);
        RecyclerView.LayoutManager lytManager = new LinearLayoutManager(getActivity());
        cartRecyclerView.setLayoutManager(lytManager);

        orderbtn = (Button) myFragment.findViewById(R.id.orderBtn);
        price = (TextView) myFragment.findViewById(R.id.orderPrice);

        orderbtn.setOnClickListener(v -> {

            if (cart.size() == 0) {
                Toast.makeText(getActivity(), "You have nothing in your shopping cart to place an order, please place an order!", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", MODE_PRIVATE);

                String value = "Order" + products.push().getKey();
                products.child(value).child("item").setValue(MasterActivity.cart);

                products.child(value).child("tableId").setValue(sharedPreferences.getString("tafelID", ""));

                MasterActivity.cart.clear();

                SumPrice();
                //send a push notification to thank
                notification("Thanks for your order!", "Your order is being prepared!");
                //send a push notification when order has been prepared
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notification("Your order has been prepared!", "Enjoy your meal!");
                    }
                }, 10000);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("SmartMenu");
                alertDialogBuilder.setIcon(R.drawable.logo);
                alertDialogBuilder.setMessage("Thanks for your order, would you write a review?");
                alertDialogBuilder.setCancelable(true);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        startActivity(new Intent(getActivity(), ReviewActivity.class));

                    }
                });

                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoriesFragment categoriesFragment = new CategoriesFragment();
                        fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentPlace, categoriesFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return myFragment;
    }

    public void SumPrice() {
        double total = 0;

        for (int i = 0; i < cart.size(); i++) {
            total += ((Double.parseDouble(cart.get(i).getProductCount()) * Double.parseDouble(cart.get(i).getProduct().getPrice())));
        }

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(Float.parseFloat(new DecimalFormat("##.##").format(total)));
        valueAnimator.setDuration(500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CartFragment.price.setText("€ " + new DecimalFormat("##.##").format(valueAnimator.getAnimatedValue()).toString());
            }
        });

        valueAnimator.start();
    }

    public void notification(String title, String description) {
        final String NOTIFICATION_CHANNEL_ID = "4565";
        String channelName = "personal channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}

