package self.kearse.mathapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class TopicFragment extends Fragment {

    public static String TOPIC_ID = "Topic ID";

    public static class TopicFragmentActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_topic_fragment);
            Fragment fragment;
            switch (getIntent().getExtras().getInt(TOPIC_ID)) {
                case 0:
                    fragment = new IntroductionFragment();
                    break;
                case 1:
                    fragment = new NumericalRepresentationFragment();
                    break;
                case 2:
                    //fragment = new ComplexPlaneFragment();
                    //break;
                case 3:
                    //fragment = new BasicOperationsFragment();
                    //break;
                default:
                    fragment = new IntroductionFragment();
            }
            fragment.setArguments(getIntent().getExtras());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }
}