package mobdev.smartmenu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;

public class RestaurantProductDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public EditText product_name;
    public EditText product_description;
    public EditText product_price;
    public TextView product_category;
    public ImageView product_image;
    public Button editBtn,deleteBtn;
    private ItemClickListener itemClickListener;

    public RestaurantProductDetailViewHolder(View itemView){
        super(itemView);
        product_image=(ImageView)itemView.findViewById(R.id.restaurantProductDetailImage);
        product_name=(EditText) itemView.findViewById(R.id.restaurantProductDetailName);
        product_description=(EditText) itemView.findViewById(R.id.restaurantProductDetailDescription);
        product_category=(TextView) itemView.findViewById(R.id.restaurantProductDetailCatId);
        product_price=(EditText) itemView.findViewById(R.id.restaurantProductDetailPrice);

        editBtn=(Button) itemView.findViewById(R.id.btnEdit);
        deleteBtn=(Button) itemView.findViewById(R.id.btnDelete);

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
