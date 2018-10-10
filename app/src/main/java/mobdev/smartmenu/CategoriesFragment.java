package mobdev.smartmenu;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements AdapterView.OnItemClickListener {

    FragmentManager fragmentManager;
    android.app.FragmentTransaction fragmentTransaction;
    View rootView;

    int[] IMAGES = {
        R.drawable.pizza,  R.drawable.pizza, R.drawable.pizza, R.drawable.pizza, R.drawable.pizza, R.drawable.pizza, R.drawable.pizza
    };

    String[] NAMES = {
        "PIZZA", "PIZZA","PIZZA","PIZZA","PIZZA","PIZZA","PIZZA",
    };

    ListView listView;

    public CategoriesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < IMAGES.length; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_image", Integer.toString(IMAGES[i]));
            hm.put("listview_name", NAMES[i]);
            aList.add(hm);
        }

        String[] from = {
                "listview_image", "listview_name"
        };
        int[] to = {
                R.id.categoryImage, R.id.categoryName
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.listview_item_layout, from, to);

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        listView = (ListView) rootView.findViewById(R.id.listCategories);

        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryID", position);

        productsFragment.setArguments(bundle);
        fragmentManager = getActivity().getFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlace, productsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
