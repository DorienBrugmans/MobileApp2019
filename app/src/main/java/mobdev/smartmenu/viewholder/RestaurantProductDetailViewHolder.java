package mobdev.smartmenu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import mobdev.smartmenu.R;

public class RestaurantProductDetailViewHolder extends RecyclerView.ViewHolder {
    public ImageView product_image;
    public EditText product_name;
    public TextView product_categoryId;
    public EditText product_description;
    public EditText product_price;
    public Button button_edit;
    public Button button_delete;

    public RestaurantProductDetailViewHolder(View itemView){
        super(itemView);

        product_image=(ImageView) itemView.findViewById(R.id.restaurantProductImage);
        product_name=(EditText) itemView.findViewById(R.id.restaurantProductName);
        product_categoryId=(TextView) itemView.findViewById(R.id.restaurantProductCatId);
        product_description=(EditText) itemView.findViewById(R.id.restaurantProductDesc);
        product_price=(EditText) itemView.findViewById(R.id.restaurantProductPrice);
        button_edit=(Button) itemView.findViewById(R.id.product_edit);
        button_delete=(Button) itemView.findViewById(R.id.product_delete);
    }
}
