package mobdev.smartmenu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.LinkedList;
import java.util.List;

import mobdev.smartmenu.viewholder.CartViewHolder;
import model.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<CartItem> cart;

    public CartAdapter(List<CartItem>carts) {
        cart=carts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new RecyclerView(parent.getContext());
        CartViewHolder viewHolder = new CartViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.cart_count.setText(cart.get(position).getProductCount());
        Picasso.with(holder.cart_product_image.getContext()).load(cart.get(position).getProduct().getImage()).fit().centerInside().into((Target) holder.cart_product_image);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
