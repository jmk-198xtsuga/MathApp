package self.kearse.mathapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NumericalRepresentationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumericalRepresentationFragment extends TopicFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NumericalRepresentationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumericalRepresentationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumericalRepresentationFragment newInstance(String param1, String param2) {
        NumericalRepresentationFragment fragment = new NumericalRepresentationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static NumericalRepresentationFragment newInstance(Bundle bundle)
        throws IllegalArgumentException {
        if (bundle == null) throw new IllegalArgumentException("null savedInstanceState");
        else if (bundle.getString(ARG_PARAM1) == null || bundle.getString(ARG_PARAM2) == null)
            throw new IllegalArgumentException("savedInstanceState missing expected tokens");
        else {
            NumericalRepresentationFragment fragment = new NumericalRepresentationFragment();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        updateState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_numerical_representation, container, false);
        TextView textCartesian = view.findViewById(R.id.textCartesianNumber);
        TextView textPolar = view.findViewById(R.id.textPolarNumber);
        ComplexDoubleCartesian mixCartesian = new ComplexDoubleCartesian(1d, 1d);
        ComplexDoublePolar mixPolar = new ComplexDoublePolar(Math.PI / 4d, Math.sqrt(2));
        textCartesian.setText(mixCartesian.toSpannedString(), TextView.BufferType.SPANNABLE);
        textPolar.setText(mixPolar.toSpannedString(), TextView.BufferType.SPANNABLE);
        return view;
    }

    @Override
    public void updateState(Bundle SavedInstanceState) {
        // do nothing for now
        return;
    }
}