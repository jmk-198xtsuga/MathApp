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
import java.util.ArrayList;
import java.util.List;

public class ComplexRootsFragment extends TopicFragment {
    private ComplexRootsFragmentViewModel mViewModel;

    public static class RootsListAdapter<T extends Complex<? extends Number>> extends ListAdapter<T, RootsListAdapter.ViewHolder> {
        //TODO: here's the error, we're using an internal roots list that technically ain't changing
        @NonNull
        private List<? extends T> roots;

        protected RootsListAdapter(@NonNull ItemCallback<T> diffCallback) {
            this(new ArrayList<T>(), diffCallback);
        }

        protected <List_T extends List<? extends T>> RootsListAdapter(@NonNull List_T roots, @NonNull ItemCallback<T> diffCallback) {
            super(diffCallback);
            this.roots = roots;
        }

        protected <List_T extends List<? extends T>> RootsListAdapter(@NonNull List_T roots) {
            this(roots, Complex.<T>getDiffCallback());
        }

        @Override
        @NonNull
        public RootsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).
                    inflate(android.R.layout.simple_list_item_1, parent, false);
            RootsListAdapter.ViewHolder vh = new RootsListAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RootsListAdapter.ViewHolder holder, int position) {
            holder.textView.setText(roots.get(position).toSpannedString(), TextView.BufferType.SPANNABLE);
        }

        @Override
        public int getItemCount() { return roots.size(); }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            protected TextView textView;

            public ViewHolder(TextView v) {
                super(v);
                this.textView = v;
            }
        }
    }

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
        rootsAdapter = new RootsListAdapter<>(mViewModel.getRootList().getValue());
        mViewModel.getRootList().observe(getViewLifecycleOwner(), new Observer<List<Complex<? extends Number>>>() {
            @Override
            public void onChanged(List<Complex<? extends Number>> list) {
                rootsAdapter.submitList(list);
            }
        });
        rootsList.setAdapter(rootsAdapter);
    }

    class UpdateClickListener implements View.OnClickListener {
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

    class InputValidationListener implements TextWatcher {
        private final TextView textView;

        InputValidationListener(@NonNull TextView textView) {
            this.textView = textView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ("".equals(s.toString())) {
                textView.setError("Value Required");
            }
        }

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

    public static class ComplexRootsFragmentViewModel extends androidx.lifecycle.ViewModel {
        @NonNull private final MutableLiveData<Complex<? extends Number>> ofNumber;
        private final MutableLiveData<Integer> focusedRoot;
        @NonNull private final MutableLiveData<List<Complex<? extends Number>>> rootList;

        public ComplexRootsFragmentViewModel() {
            ofNumber = new MutableLiveData<Complex<? extends Number>>(new ComplexDoubleCartesian(1d, 0d));
            focusedRoot = new MutableLiveData<>(0);
            rootList = new MutableLiveData<>(Complex.roots(ofNumber.getValue(), 2));
        }

        @NonNull
        LiveData<? extends Complex<? extends Number>> getNumber() {
            return ofNumber;
        }
        @NonNull
        LiveData<Integer> getFocusedRoot() {
            return focusedRoot;
        }
        @NonNull
        LiveData<List<Complex<? extends Number>>> getRootList() {
            return rootList;
        }

        void updateData(@NonNull Complex<? extends Number> newNumber, @NonNull Integer root) {
            ofNumber.setValue(newNumber);
            focusedRoot.setValue(0);
            rootList.postValue(Complex.roots(newNumber, root));
        }
    }
}

//TODO: Review everything to see that the use of Complex<T> versus Complex<? extends Number> is correct

//TODO: Review everything to see that the use of List<? extends Complex<>> versus List<Complex<>> is correct