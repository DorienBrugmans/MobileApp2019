package mobdev.smartmenu.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;

public class ProductDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_name;
    public TextView product_description;
    public TextView product_price;
    public EditText product_count;
    public ImageView product_image;
    private ItemClickListener itemClickListener;

    public ProductDetailViewHolder(View itemView){
        super(itemView);
        product_image=(ImageView)itemView.findViewById(R.id.productDetailImage);
        product_name=(TextView)itemView.findViewById(R.id.productDetailName);
        product_description=(TextView)itemView.findViewById(R.id.productDetailDescription);
        product_price=(TextView)itemView.findViewById(R.id.productDetailPrice);
        product_count=(EditText) itemView.findViewById(R.id.productDetailCount);

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
