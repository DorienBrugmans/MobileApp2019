package mobdev.smartmenu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import mobdev.smartmenu.ItemClickListener;
import mobdev.smartmenu.R;
import mobdev.smartmenu.viewholder.CategoryViewHolder;
import model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    View myFragment;
    RecyclerView listCategory;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;
    LinearLayoutManager layoutManager;

    public static CategoriesFragment newInstance(){
        CategoriesFragment categoryFragment=new CategoriesFragment();
        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        categories=database.getReference("Category");
    }

    public CategoriesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_categories,container,false);
        listCategory=(RecyclerView)myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        loadCategories();

        return myFragment;
    }

    private void loadCategories() {
        adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class,R.layout.listview_item_layout,CategoryViewHolder.class,categories) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.category_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.category_progress.setVisibility(View.GONE);
                        viewHolder.category_name.setText(model.getName());
                    }

                    @Override
                    public void onError() {

                    }
                });

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        ProductsFragment productsFragment = new ProductsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("categoryID", adapter.getRef(position).getKey());

                        productsFragment.setArguments(bundle);
                        fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentPlace, productsFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}
