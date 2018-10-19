package mobdev.smartmenu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mobdev.smartmenu.activity.MasterActivity;
import mobdev.smartmenu.fragment.CartFragment;
import model.CartItem;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItem> cart;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cart_layout,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CartViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return MasterActivity.cart.size();
    }

    private class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView cart_product_image;
        public TextView cart_product_name;
        public Button cart_count_min;
        public Button cart_count_plus;
        public TextView cart_count;

        public Button btnOrder;
        public TextView price;

        private ItemClickListener itemClickListener;

        public CartViewHolder(View itemView) {
            super(itemView);
            cart_product_image = (ImageView) itemView.findViewById(R.id.productCartImage);
            cart_product_name = (TextView) itemView.findViewById(R.id.productCartName);
            cart_count_min = (Button) itemView.findViewById(R.id.minCount);
            cart_count_plus = (Button) itemView.findViewById(R.id.minPlus);
            cart_count = (TextView) itemView.findViewById(R.id.productCartCount);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        public void bindView(final int position){
            cart_count.setText(MasterActivity.cart.get(position).getProductCount());
            cart_product_name.setText(MasterActivity.cart.get(position).getProduct().getName());
            Picasso.with(itemView.getContext()).load(MasterActivity.cart.get(position).getProduct().getImage()).into(cart_product_image);

            cart_count_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count=Integer.parseInt(MasterActivity.cart.get(position).getProductCount())+1;

                    MasterActivity.cart.get(position).setProductCount(count+"");
                    cart_count.setText(MasterActivity.cart.get(position).getProductCount());
                    CartFragment.price.setText(""+CartFragment.SumPrice(MasterActivity.cart));

                }
            });

            cart_count_min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count=Integer.parseInt(MasterActivity.cart.get(position).getProductCount());
                    if (count>0){
                        --count;
                    }
                    MasterActivity.cart.get(position).setProductCount(count+"");
                    cart_count.setText(MasterActivity.cart.get(position).getProductCount());
                    CartFragment.price.setText(""+CartFragment.SumPrice(MasterActivity.cart));
                }
            });
            //cart_product_image.setImageResource(Integer.parseInt(MasterActivity.cart.get(position).getProduct().getImage()));
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

    }

}
