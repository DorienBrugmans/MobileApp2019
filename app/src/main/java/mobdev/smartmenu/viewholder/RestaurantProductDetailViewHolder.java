package mobdev.smartmenu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;

public class RestaurantProductDetailViewHolder extends RecyclerView.ViewHolder {
    public TextView product_name;
    private ItemClickListener itemClickListener;

    public RestaurantProductDetailViewHolder(View itemView){
        super(itemView);
        product_name=(TextView)itemView.findViewById(R.id.restaurant_product_detail_content);
    }
}
