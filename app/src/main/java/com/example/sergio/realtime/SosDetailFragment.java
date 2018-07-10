package com.example.sergio.realtime;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergio.realtime.dummy.DummyContent;

/**
 * A fragment representing a single Sos detail screen.
 * This fragment is either contained in a {@link SosListActivity}
 * in two-pane mode (on tablets) or a {@link SosDetailActivity}
 * on handsets.
 */
public class SosDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy nombre this fragment is presenting.
     */
    private DummyContent.Sos mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SosDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy nombre specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load nombre from a nombre provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getNombre());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sos_detail, container, false);

        // Show the dummy nombre as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.sos_detail)).setText(mItem.getFrente());
        }

        return rootView;
    }
}
