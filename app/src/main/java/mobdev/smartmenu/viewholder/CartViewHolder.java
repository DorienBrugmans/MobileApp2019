package mobdev.smartmenu.viewholder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView cart_product_image;
    public TextView cart_product_name;
    public Button cart_count_min;
    public Button cart_count_plus;
    public TextView cart_count;
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

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
