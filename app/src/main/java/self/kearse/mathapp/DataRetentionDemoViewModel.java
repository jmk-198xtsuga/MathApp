package self.kearse.mathapp;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A sample ViewModel class to experiment with retaining data through activity and fragment
 * lifecycles, where the data is a string built by a user clicking a sequence of buttons.
 */

public class DataRetentionDemoViewModel extends ViewModel {
    /** The String data sample that will be built by the user clicking buttons in sequence. */
    //NOTE: the data should be static, else it gets re-created each time the ViewModel does.
    @NonNull
    private static final MutableLiveData<String> data = new MutableLiveData<>("Initial Value");
    /** Whether the data was recently cleared. */
    private boolean recentlyCleared = false;

    /** Constructs a new ViewModel. */
    public DataRetentionDemoViewModel() {
        Log.d(DataRetentionDemoViewModel.class.getSimpleName(), "Creating a new view model");
    }

    /** Retrieves the data. */
    @NonNull
    LiveData<String> getData() {
        return data;
    }

    /**
     * Adds content to the end of the string, including an extra space if needed.
     * @param extra the content to add.
     */
    void append(@NonNull String extra) {
        if (recentlyCleared) {
            data.setValue(extra);
            recentlyCleared = false;
        } else {
            data.setValue(String.format("%s\n%s", data.getValue(), extra));
        }
    }

    /** Clears the data held by the model. */
    void clearData() {
        data.setValue("cleared");
        recentlyCleared = true;
    }
}