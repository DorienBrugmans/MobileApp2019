package mobdev.smartmenu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobdev.smartmenu.viewholder.CartViewHolder;
import model.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<CartItem> cart;

    public CartAdapter() {
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
        holder.itemView.set(cart[position]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
