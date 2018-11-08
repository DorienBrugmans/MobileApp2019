package mobdev.smartmenu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_name;
    public ImageView product_image;
    private ItemClickListener itemClickListener;
    public ProgressBar product_progress;

    public ProductViewHolder(View itemView){
        super(itemView);
        product_image=(ImageView)itemView.findViewById(R.id.productImage);
        product_name=(TextView)itemView.findViewById(R.id.productName);
        product_progress = (ProgressBar) itemView.findViewById(R.id.categoryProgress);
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
