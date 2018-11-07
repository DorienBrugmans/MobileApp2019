package mobdev.smartmenu.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView reviewer;
    public TextView rating;
    public TextView tableId;
    public TextView reviewComment;

    private ItemClickListener itemClickListener;


    public ReviewViewHolder(View itemView) {
        super(itemView);
        reviewer = (TextView) itemView.findViewById(R.id.reviewer);
        rating = (TextView) itemView.findViewById(R.id.reviewRating);
        tableId = (TextView) itemView.findViewById(R.id.reviewTableId);
        reviewComment = (TextView) itemView.findViewById(R.id.reviewComment);


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
