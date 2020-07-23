package self.kearse.mathapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    private RecyclerView activityList;
    private RecyclerView.Adapter activityAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.activityList = findViewById(R.id.activityMenu);
        this.activityList.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.activityList.setLayoutManager(this.layoutManager);
        this.activityAdapter = new ActivityListAdapter(
                getResources().getStringArray(R.array.activities_list));
        this.activityList.setAdapter(this.activityAdapter);
    }

    public static class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder>
            {
        private String[] activities;

        public ActivityListAdapter(String[] activityList) {
            this.activities = activityList;
        }

        @Override
        public ActivityListAdapter.ViewHolder onCreateViewHolder (ViewGroup parent,
                                                                  int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).
                    inflate(android.R.layout.simple_list_item_1, parent, false);
            ActivityListAdapter.ViewHolder vh = new ActivityListAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder (ActivityListAdapter.ViewHolder holder, int position) {
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
            public ViewHolder(TextView v) {
                super(v);
                v.setOnClickListener(this);
                this.textView = v;
            }
            public void onClick(View v) {
                if (v instanceof TextView) {
                    Toast message = Toast.makeText(v.getContext(),
                            "" + getAdapterPosition() + ": "+ ((TextView) v).getText(),
                            Toast.LENGTH_SHORT);
                    message.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    message.show();
                    Fragment fragment = null;
                    switch (getAdapterPosition()) {
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
                        FragmentTransaction transaction;
                        //TODO: Get this to crank out a new fragment
                    }
                }
            }
        }
    }
}
