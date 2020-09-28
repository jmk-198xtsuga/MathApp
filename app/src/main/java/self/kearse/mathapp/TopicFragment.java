package self.kearse.mathapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

/** A standard Fragment for displaying various Topics. */
public abstract class TopicFragment extends Fragment {
    /** Bundle content identifier for Topic ID */
    public static String TOPIC_ID = "Topic ID";

    /** A standard Activity for displaying Topic Fragments. */
    public static class TopicFragmentActivity extends AppCompatActivity {

        /**
         * Upon Activity creation, look up the requested Topic, associate with the Topic's
         * Fragment, forward any Bundle Extras, and display the Fragment in fragment_container.
         * @param savedInstanceState Android Bundle with state information.
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_topic_fragment);
            Bundle extras = getIntent().getExtras();
            Topic<? extends TopicFragment> topic;
            TopicFragment fragment = null;
            if (extras != null) {
                topic = Topic.getTopicList().get(extras.getInt(TOPIC_ID));
                try {
                    fragment = topic.getFragmentClass().newInstance();
                } catch (Exception e) {
                    Log.e("TopicFragment", "unable to instantiate topic fragment: " +
                            e.getMessage());
                    fragment = null;
                }
            }
            if (fragment != null) {
                fragment.setArguments(getIntent().getExtras());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                /* Replace previous Fragment, if any, without saving it to the back stack. */
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        }
    }
}