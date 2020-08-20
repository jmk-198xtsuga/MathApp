package self.kearse.mathapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public abstract class TopicFragment extends Fragment {

    public static String TOPIC_ID = "Topic ID";

    public static class TopicFragmentActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_topic_fragment);
            Bundle extras = getIntent().getExtras();
            Topic<? extends TopicFragment> topic;
            TopicFragment fragment = null;
            Bundle topicState = null;
            if (extras != null) {
                topic = Topic.getTopicList().get(extras.getInt(TOPIC_ID));
                topicState = topic.getSavedInstanceState();
                try {
                    fragment = topic.getFragmentClass().newInstance();
                } catch (Exception e) {
                    Log.e("TopicFragment", "unable to instantiate topic fragment: " +
                            e.getMessage());
                    fragment = null;
                }
            }
            if (fragment != null) {
                if (topicState != null) fragment.updateState(topicState);
                fragment.setArguments(getIntent().getExtras());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, fragment);
                transaction.commit();
            }
        }
    }

    public abstract void updateState(Bundle topicState);
}