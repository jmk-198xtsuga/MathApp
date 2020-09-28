package self.kearse.mathapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

/** Android fragment for displaying information about Complex roots. */
public class ComplexRootsFragment extends TopicFragment {
    /** ViewModel for the complex number and roots list. */
    private ComplexRootsFragmentViewModel mViewModel;

    /** Prepares the fragment view by applying the XML layout and adding button listeners. */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.complex_roots_fragment, container, false);
        view.findViewById(R.id.btnUpdateComplexRoots).setOnClickListener(new UpdateClickListener());
        EditText inputReal = view.findViewById(R.id.inputReal);
        EditText inputComplex = view.findViewById(R.id.inputComplex);
        EditText inputDegree = view.findViewById(R.id.inputDegree);
        Button updateButton = view.findViewById(R.id.btnUpdateComplexRoots);
        inputReal.addTextChangedListener(new InputValidationListener(inputReal));
        inputComplex.addTextChangedListener(new InputValidationListener(inputComplex));
        inputDegree.addTextChangedListener(new InputValidationListener(inputDegree));
        updateButton.setOnClickListener(new UpdateClickListener());
        return view;
    }

    /** After the fragment view is ready, acquires a reference to the sample ViewModel and adds
     * a listener to the data which updates the text field as necessary.
     * @param view The fragment view which was created.
     * @param savedInstanceState The saved state retained by the system.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rootsList;
        final RootsListAdapter<Complex<? extends Number>> rootsAdapter;
        RecyclerView.LayoutManager layoutManager;

        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ComplexRootsFragmentViewModel.class);
        rootsList = view.findViewById(R.id.rootsRecycler);
        rootsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rootsList.setLayoutManager(layoutManager);
        rootsAdapter = new RootsListAdapter<>();
        mViewModel.getRootList().observe(getViewLifecycleOwner(), new Observer<List<Complex<? extends Number>>>() {
            @Override
            public void onChanged(List<Complex<? extends Number>> list) {
                rootsAdapter.submitList(list);
            }
        });
        rootsList.setAdapter(rootsAdapter);
        EditText inputReal = view.findViewById(R.id.inputReal);
        EditText inputComplex = view.findViewById(R.id.inputComplex);
        EditText inputDegree = view.findViewById(R.id.inputDegree);
        Complex<? extends Number> root = mViewModel.getNumber().getValue();
        List<Complex<? extends Number>> roots = mViewModel.getRootList().getValue();
        if (root != null && roots != null) {
            inputReal.setText(root.real().toString());
            inputComplex.setText(root.imaginary().toString());
            inputDegree.setText(String.format(Locale.getDefault(),"%d", roots.size()));
        }
    }

    /** Listener class for the Update button that computes the roots of a given Complex number. */
    class UpdateClickListener implements View.OnClickListener {
        /**
         * Computes the roots of a given Complex number when the button is clicked.
         * @param v The view for which a click was detected.
         */
        @Override
        public void onClick(View v) {
            Log.d(ComplexRootsFragment.class.getSimpleName(), "Update Click Received");
            if (v.getId() == R.id.btnUpdateComplexRoots) {
                View frag = getView();
                if (frag != null) {
                    EditText real = frag.findViewById(R.id.inputReal);
                    EditText imaginary = frag.findViewById(R.id.inputComplex);
                    EditText degree = frag.findViewById(R.id.inputDegree);
                    if (real != null && imaginary != null && degree != null) {
                        Double dblReal = Double.parseDouble(real.getText().toString());
                        Double dblImaginary = Double.parseDouble(imaginary.getText().toString());
                        Integer intDegree = Integer.parseInt(degree.getText().toString());
                        mViewModel.updateData(new ComplexDoubleCartesian(dblReal, dblImaginary), intDegree);
                    }
                }
            }
        }
    }

    //TODO: Change the way input validation is handled, the single-input method is sufficient,
    // and perhaps an alternate listener could be added to the text fields specifically for the
    // Update button clickability.
    /** Listener class for the input fields that rates whether appropriate values are given. */
    class InputValidationListener implements TextWatcher {
        /** The TextView input to be watched. */
        private final TextView textView;

        /** Prepares a new Listener by recording a reference to the given TextView.
         * @param textView The TextView input to be watched.
         */
        InputValidationListener(@NonNull TextView textView) {
            this.textView = textView;
        }

        /**
         * Validation method to run before text is changed.  Does nothing, currently.
         * @param s The supplied input.
         * @param start The beginning position of changed text.
         * @param count The number of consecutive positions changed.
         * @param after The length of the upcoming change.
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        /**
         * Validation method to run once text is changed.  Warns user if input is empty.
         * @param s The supplied input.
         * @param start The beginning position of changed text.
         * @param before The length of the replaced original text.
         * @param count The length of the new text.
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ("".equals(s.toString())) {
                textView.setError("Value Required");
            }
        }

        /**
         * Validation method to run after text is changed.  Sets the Update button to be clickable
         * if all the inputs are valid, otherwise sets it as unclickable.
         * @param s The supplied input.
         */
        @Override
        public void afterTextChanged(Editable s) {
            Log.d(ComplexRootsFragment.class.getSimpleName(), "Text Change Observed");
            View view = getView();
            if (view != null) {
                Button btnUpdate = view.findViewById(R.id.btnUpdateComplexRoots);
                TextView inputReal = view.findViewById(R.id.inputReal);
                TextView inputComplex = view.findViewById(R.id.inputComplex);
                TextView inputDegree = view.findViewById(R.id.inputDegree);
                if (btnUpdate != null && inputReal != null && inputComplex != null && inputDegree != null) {
                    btnUpdate.setClickable(inputReal.getError() == null &&
                            inputComplex.getError() == null && inputDegree.getError() == null);
                    Log.d(ComplexRootsFragment.class.getSimpleName(),
                            String.format("Button clickable: %s", btnUpdate.isClickable()));
                }
            }
        }
    }

    /** A ViewModel class for ComplexRootsFragment. */
    public static class ComplexRootsFragmentViewModel extends androidx.lifecycle.ViewModel {
        /** The number of which roots are computed. */
        @NonNull private static final MutableLiveData<Complex<? extends Number>> ofNumber =
                new MutableLiveData<Complex<? extends Number>>(new ComplexDoubleCartesian(1d, 0d));
        /** The root which is currently focused on in the RecyclerView, to conserve scroll position. */
        private static final MutableLiveData<Integer> focusedRoot = new MutableLiveData<>(0);
        /** The list of roots to display. */
        @NonNull private static final MutableLiveData<List<Complex<? extends Number>>> rootList =
                new MutableLiveData<>(Complex.roots(ofNumber.getValue(), 2));

        /** The number of which roots are computed. */
        @NonNull
        LiveData<? extends Complex<? extends Number>> getNumber() {
            return ofNumber;
        }
        /** The root which is currently focused on in the RecyclerView. */
        @NonNull
        LiveData<Integer> getFocusedRoot() {
            return focusedRoot;
        }
        /** The list of roots to display. */
        @NonNull
        LiveData<List<Complex<? extends Number>>> getRootList() {
            return rootList;
        }

        /**
         * Updates the information in the ViewModel with a new Complex number and new list of roots.
         * @param newNumber The new number of which roots will be computed.
         * @param degree The degree (or quantity) of roots to generate.
         */
        void updateData(@NonNull Complex<? extends Number> newNumber, @NonNull Integer degree) {
            ofNumber.setValue(newNumber);
            focusedRoot.setValue(0);
            rootList.postValue(Complex.roots(newNumber, degree));
        }
    }

    /**
     * Display adapter for RecyclerView that presents the list of Complex numbers.
     * @param <T> Complex or any extended type thereof, for the numbers in the list.
     */
    public static class RootsListAdapter<T extends Complex<? extends Number>> extends ListAdapter<T, RootsListAdapter.ViewHolder> {
        /**
         * Prepares a RootsListAdapter with a specified callback.
         * @param diffCallback A DiffUtil callback to see when items change.
         */
        protected RootsListAdapter(@NonNull ItemCallback<T> diffCallback) {
            super(diffCallback);
        }

        /** Prepares a RootsListAdapter with the default callback from Complex. */
        protected RootsListAdapter() {
            this(Complex.<T>getDiffCallback());
        }

        /** Generates a new ViewHolder using a basic TextView. */
        @Override
        @NonNull
        public RootsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).
                    inflate(android.R.layout.simple_list_item_1, parent, false);
            RootsListAdapter.ViewHolder vh = new RootsListAdapter.ViewHolder(v);
            return vh;
        }

        /** Sets the ViewHolder's TextView to the SpannedString formatted Complex number. */
        @Override
        public void onBindViewHolder(@NonNull RootsListAdapter.ViewHolder holder, int position) {
            holder.textView.setText(getItem(position).toSpannedString(), TextView.BufferType.SPANNABLE);
        }

        /** A ViewHolder using a basic TextView. */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            /** The View's primary TextView. */
            protected TextView textView;

            /** Initializes a new ViewHolder and records the associated TextView. */
            public ViewHolder(TextView v) {
                super(v);
                this.textView = v;
            }
        }
    }
}

//TODO: Review everything to see that the use of Complex<T> versus Complex<? extends Number> is correct

//TODO: Review everything to see that the use of List<? extends Complex<>> versus List<Complex<>> is correct

//TODO: layout improvement for when a small screen is landscape, potentially with a pop-up RecyclerView.