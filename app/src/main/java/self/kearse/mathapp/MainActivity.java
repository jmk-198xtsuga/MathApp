package self.kearse.mathapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnSelectTopicListener {
    private RecyclerView activityList;
    private RecyclerView.Adapter activityAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Reference: https://developer.android.com/training/basics/fragments/fragment-ui */
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            } else {
                Fragment initial = new IntroductionFragment();
                initial.setArguments(getIntent().getExtras());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, initial);
                transaction.commit();
            }
        }
        this.activityList = findViewById(R.id.activityMenu);
        this.activityList.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.activityList.setLayoutManager(this.layoutManager);
        this.activityAdapter = new ActivityListAdapter(
                getResources().getStringArray(R.array.activities_list), this);
        this.activityList.setAdapter(this.activityAdapter);
    }

    public void onSelectTopic(int topicPosition) {
        Fragment fragment = null;
        switch (topicPosition) {
            case 0:
                fragment = new IntroductionFragment();
                break;
            case 1:
                fragment = new NumericalRepresentationFragment();
                break;
            case 2:
                //fragment = new ComplexPlaneFragment();
                break;
            case 3:
                //fragment = new BasicOperationsFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            //TODO: Get this to crank out a new fragment
        }
    }

    public static class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
        private String[] activities;
        private OnSelectTopicListener onSelectTopicListener;

        public ActivityListAdapter(String[] activityList, OnSelectTopicListener listener) {
            this.activities = activityList;
            this.onSelectTopicListener = listener;
        }

        @Override
        @NonNull
        public ActivityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).
                    inflate(android.R.layout.simple_list_item_1, parent, false);
            ActivityListAdapter.ViewHolder vh = new ActivityListAdapter.ViewHolder(v, onSelectTopicListener);
            return vh;
        }

        @Override
        public void onBindViewHolder(ActivityListAdapter.ViewHolder holder, int position) {
            holder.textView.setText(activities[position]);
        }

//        @Override
//        public void onClick(View view) {
//            if (view instanceof TextView) {
//                Toast message = Toast.makeText(view.getContext(),
//                        ((TextView) view).getText(),
//                        Toast.LENGTH_SHORT);
//                message.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                message.show();
//            }
//        }

        @Override
        public int getItemCount() {
            return activities.length;
        }

        /* Referencing https://github.com/android/views-widgets-samples/blob/master/RecyclerView/Application/src/main/java/com/example/android/recyclerview/CustomAdapter.java
         * and https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.ViewHolder#getBindingAdapterPosition() */
        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView textView;
            private OnSelectTopicListener onSelectTopicListener;

            public ViewHolder(TextView v, OnSelectTopicListener listener) {
                super(v);
                v.setOnClickListener(this);
                this.textView = v;
                this.onSelectTopicListener = listener;
            }

            public void onClick(View v) {
                if (v instanceof TextView) {
                    onSelectTopicListener.onSelectTopic(getAdapterPosition());
                }
            }
        }
    }
}

/**
 * Providing an interface for topic selection so that I can update that from a static
 * RecyclerView.ViewHolder
 */
interface OnSelectTopicListener {
    /* Referencing https://developer.android.com/guide/components/fragments
     * and https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9 */

    /**
     * Respond to the user selecting the topic listed at the indicated position
     * @param position the position of the topic in a list
     */
    void onSelectTopic(int position);
}