package mobdev.smartmenu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;

public class RestaurantProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_name;
    public ImageView product_image;
    private ItemClickListener itemClickListener;
    public ProgressBar product_progress;

    public RestaurantProductViewHolder(View itemView){
        super(itemView);
        product_image=(ImageView)itemView.findViewById(R.id.restaurantProductImage);
        product_name=(TextView)itemView.findViewById(R.id.restaurantProductName);

        product_progress = (ProgressBar) itemView.findViewById(R.id.restaurantProgress);

        product_progress.setVisibility(View.VISIBLE);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
