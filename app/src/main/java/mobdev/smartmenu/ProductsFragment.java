package mobdev.smartmenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    View view;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_products, container, false);
        TextView txt = (TextView) view.findViewById(R.id.categoryID);

        Bundle bundle = this.getArguments();

        txt.setText( "category : " + bundle.getInt("categoryID", 0));

        return view;
    }

}
