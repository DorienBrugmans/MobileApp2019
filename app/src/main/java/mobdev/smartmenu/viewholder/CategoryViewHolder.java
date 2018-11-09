package mobdev.smartmenu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
//binding category layout
public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView category_name;
    public ImageView category_image;
    private ItemClickListener itemClickListener;
    public ProgressBar category_progress;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        category_image = (ImageView) itemView.findViewById(R.id.categoryImage);
        category_name = (TextView) itemView.findViewById(R.id.categoryName);
        category_progress = (ProgressBar) itemView.findViewById(R.id.categoryProgress);
        category_progress.setVisibility(View.VISIBLE);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
