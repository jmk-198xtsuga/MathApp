package self.kearse.mathapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

public class DataRetentionDemoViewModel extends ViewModel {
    private final MutableLiveData<String> data = new MutableLiveData<>();
    private boolean recentlyCleared;

    public DataRetentionDemoViewModel() {
        data.setValue("Initial value");
        recentlyCleared = false;
    }

    @NonNull
    public LiveData<String> getData() {
        return data;
    }

    void append(@NonNull String extra) {
        if (recentlyCleared) {
            data.setValue(extra);
            recentlyCleared = false;
        } else {
            data.setValue(String.format("%s\n%s", data.getValue(), extra));
        }
    }

    void clearData() {
        data.setValue("cleared");
        recentlyCleared = true;
    }
}