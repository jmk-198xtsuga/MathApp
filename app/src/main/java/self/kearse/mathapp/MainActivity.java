package self.kearse.mathapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import self.kearse.mathapp.Topic.OnSelectTopicListener;

public class MainActivity extends AppCompatActivity implements OnSelectTopicListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView activityList;
        RecyclerView.Adapter<MainActivity.ActivityListAdapter.ViewHolder> activityAdapter;
        RecyclerView.LayoutManager layoutManager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Reference: https://developer.android.com/training/basics/fragments/fragment-ui */
        if (findViewById(R.id.fragment_container) != null) {
            Fragment initial = new IntroductionFragment();
            initial.setArguments(getIntent().getExtras());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, initial);
            transaction.commit();
        }
        activityList = findViewById(R.id.activityMenu);
        activityList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        activityList.setLayoutManager(layoutManager);
        activityAdapter = new ActivityListAdapter(
                Topic.getTopicList(), this);
        activityList.setAdapter(activityAdapter);
    }

    public void onSelectTopic(int topicPosition) {
        if (findViewById(R.id.fragment_container) != null) {
            Topic<? extends TopicFragment> topic = Topic.getTopicList().get(topicPosition);
            TopicFragment fragment = null;
            try {
                fragment = topic.getFragmentClass().newInstance();
            } catch (Exception e) {
                Log.e("TopicFragment", "unable to instantiate topic fragment: " +
                        e.getMessage());
            }
            if (fragment != null) {
                fragment.setArguments(getIntent().getExtras());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        } else {
            Intent intent = new Intent(this, TopicFragment.TopicFragmentActivity.class);
            intent.putExtra(TopicFragment.TOPIC_ID, topicPosition);
            startActivity(intent);
        }
    }

    public static class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
        private List<Topic<? extends TopicFragment>> activities;
        private OnSelectTopicListener onSelectTopicListener;

        public ActivityListAdapter(List<Topic<? extends TopicFragment>> activityList,
                                   OnSelectTopicListener listener) {
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
            holder.textView.setText(activities.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return activities.size();
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