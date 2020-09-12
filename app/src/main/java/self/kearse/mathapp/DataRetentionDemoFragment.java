package self.kearse.mathapp;

import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A Fragment to showcase and try out our sample ViewModel class.  The fragment presents two text
 * buttons to grow the data content, a clear button to clear the data, and a text field to show
 * the current contents of the data.
 */
public class DataRetentionDemoFragment extends TopicFragment
        implements View.OnClickListener {

    /** A reference to the sample ViewModel class. */
    private DataRetentionDemoViewModel mViewModel;

    /** Prepares the fragment view by applying the XML layout and adding button listeners. */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.data_retention_demo_fragment, container, false);
        view.findViewById(R.id.RetentionTacoButton).setOnClickListener(this);
        view.findViewById(R.id.RetentionBurgerButton).setOnClickListener(this);
        view.findViewById(R.id.RetentionClearButton).setOnClickListener(this);
        return view;
    }

    /** After the fragment view is ready, acquires a reference to the sample ViewModel and adds
     * a listener to the data which updates the text field as necessary.
     * @param view The fragment view which was created.
     * @param savedInstanceState The saved state retained by the system.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DataRetentionDemoViewModel.class);
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView textView = requireActivity().findViewById(R.id.RetentionDemoText);
                if (textView != null) {
                    textView.setText(s);
                }
            }
        });
    }

    /**
     * A click handler for the fragment.  Calls appropriate ViewModel methods for the three buttons.
     * @param view The view element that was clicked.
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RetentionTacoButton:
                mViewModel.append("Taco");
                break;
            case R.id.RetentionBurgerButton:
                mViewModel.append("Burger");
                break;
            case R.id.RetentionClearButton:
                mViewModel.clearData();
                break;
        }
    }
}