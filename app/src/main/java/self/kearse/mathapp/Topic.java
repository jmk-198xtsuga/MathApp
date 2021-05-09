package self.kearse.mathapp;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimum specifications for topics to be presented in this app.  Requires a name, and a Fragment
 * class.  Also builds and maintains a list of all current topics for the app.
 * @param <F> A Fragment class for displaying the topic in an Android UI.
 */
public class Topic<F extends TopicFragment> {
    /** The name of the Topic. */
    @NonNull private String name;
    /** The Topic's associated Fragment Class. */
    @NonNull private Class<F> fragmentClass;
    /** A list of all declared Topics. */
    private static List<Topic<? extends TopicFragment>> allTopics;

    /**
     * Establishes a new Topic with a given name and Fragment class.
     * @param name The name of the topic, as it will appear in a user-facing list.
     * @param fragmentClass The Fragment used to display the topic.
     */
    private Topic(@NonNull String name, @NonNull Class<F> fragmentClass) {
        this.name = name;
        this.fragmentClass = fragmentClass;
    }

    /** @return The name of the topic. */
    @NonNull
    protected String getName() {
        return this.name;
    }

    /**
     * @return The topic's text representation, currently its name.
     */
    @Override
    @NonNull
    public String toString() {
        return getName();
    }

    /** @return The Topic's associated Fragment Class. */
    @NonNull
    public Class<F> getFragmentClass() {
        return this.fragmentClass;
    }

    /**
     * Providing an interface for topic selection so that I can update that from a static
     * RecyclerView.ViewHolder.
     */
    interface OnSelectTopicListener {
        /* Referencing https://developer.android.com/guide/components/fragments
         * and https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9 */

        /**
         * Respond to the user selecting the topic listed at the indicated position.
         * @param position the position of the topic in a list.
         */
        void onSelectTopic(int position);
    }

    /** @return A list of all declared Topics. */
    public static List<Topic<? extends TopicFragment>> getTopicList() {
        if (allTopics == null) {
            createTopicsList();
        }
        return allTopics;
    }

    /** Constructs a list of Topics. */
    private static void createTopicsList() {
        // By intention the list of topics, itself, is built in a Singleton pattern
        allTopics = new ArrayList<>();
        allTopics.add(new Topic<>("Introduction",
                IntroductionFragment.class));
        allTopics.add(new Topic<>("Numerical Representation",
                NumericalRepresentationFragment.class));
        allTopics.add(new Topic<>("Data Retention Demo",
                DataRetentionDemoFragment.class));
        allTopics.add(new Topic<>("ComplexNumber Roots",
                ComplexRootsFragment.class));
//        allTopics.add(new Topic("ComplexNumber Plane", ComplexPlaneFragment.class));
        allTopics.add(new Topic<>("Basic Operations", BasicOperationsFragment.class));
    }
}