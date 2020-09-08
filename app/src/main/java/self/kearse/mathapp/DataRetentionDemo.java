package self.kearse.mathapp;

import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataRetentionDemo extends TopicFragment
        implements View.OnClickListener {

    private DataRetentionDemoViewModel mViewModel;

    public static DataRetentionDemo newInstance() {
        return new DataRetentionDemo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_retention_demo_fragment, container, false);
        view.findViewById(R.id.RetentionTacoButton).setOnClickListener(this);
        view.findViewById(R.id.RetentionBurgerButton).setOnClickListener(this);
        view.findViewById(R.id.RetentionClearButton).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(DataRetentionDemoViewModel.class);
        mViewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView textView = requireActivity().findViewById(R.id.RetentionDemoText);
                if (textView != null) {
                    textView.setText(mViewModel.getData().getValue());
                }
            }
        });
    }

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