package self.kearse.mathapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimum specifications for topics to be presented in this app.  Requires a name, a Fragment
 * class, and a reference for a persistent saved state.  Also builds and maintains a list of all
 * current topics for the app.
 * @param <F> A Fragment class for displaying the topic in an Android UI
 */
public class Topic<F extends TopicFragment> implements OnSaveInstanceStateListener {
    @NonNull private String name;
    @NonNull private Class<F> fragmentClass;
    private Bundle savedInstanceState;
    private static List<Topic<? extends TopicFragment>> allTopics;

    /**
     * Establishes a new Topic with a given name, Fragment class, and saved state
     * @param name The name of the topic, as it will appear in a user-facing list
     * @param fragmentClass The Fragment used to display the topic
     * @param savedInstanceState The necessary information to reproduce the fragment if it closes
     */
    private Topic(@NonNull String name, @NonNull Class<F> fragmentClass, Bundle savedInstanceState) {
        this.name = name;
        this.fragmentClass = fragmentClass;
        this.savedInstanceState = savedInstanceState;
    }

    /**
     * Establishes a new Topic with a given name, Fragment class, and saved state
     * @param name The name of the topic, as it will appear in a user-facing list
     * @param fragmentClass The Fragment used to display the topic
     */
    private Topic(@NonNull String name, @NonNull Class<F> fragmentClass) {
        this(name, fragmentClass, null);
    }

    /**
     * @return The name of the topic
     */
    @NonNull
    protected String getName() {
        return this.name;
    }

    /**
     * @return The topic's text representation, currently its name
     */
    @Override
    @NonNull
    public String toString() {
        return getName();
    }

    @NonNull
    public Class<F> getFragmentClass() {
        return this.fragmentClass;
    }

    protected void setSavedInstanceState(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        setSavedInstanceState(savedInstanceState);
    }

    Bundle getSavedInstanceState() {
        return this.savedInstanceState;
    }

    public interface OnSelectTopicListener {
        void onSelectTopic(int topic);
    }

    public static List<Topic<? extends TopicFragment>> getTopicList() {
        if (allTopics == null) {
            createTopicsList();
        }
        return allTopics;
    }

    private static void createTopicsList() {
        // By intention the list of topics, itself, is built in a Singleton pattern
        allTopics = new ArrayList<>();
        allTopics.add(new Topic<>("Introduction",
                IntroductionFragment.class));
        allTopics.add(new Topic<>("Numerical Representation",
                NumericalRepresentationFragment.class));
//        allTopics.add(new Topic("Complex Plane", ComplexPlaneFragment.class));
//        allTopics.add(new Topic("Basic Operations", BasicOperationsFragment.class));
    }

    //TODO: Figure out a way to correctly handle fragment class instances.  Would not want to have
    // each of the classes persist the entire life of the application (defeats the Android lifecycle
    // design), but still retain the savedInstanceState.
}

interface OnSaveInstanceStateListener {
    void onSaveInstanceState(Bundle savedInstanceState);
}