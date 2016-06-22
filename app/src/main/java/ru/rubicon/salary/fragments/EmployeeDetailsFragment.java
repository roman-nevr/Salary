package ru.rubicon.salary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.rubicon.salary.R;

import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 21.06.2016.
 */
public class EmployeeDetailsFragment extends Fragment {

    OnItemClickObserver mCallback;

    public interface OnItemClickObserver {
        public void onEmployeeItemClick(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnItemClickObserver) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnItemClickObserver");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Intent launchingIntent = getActivity().getIntent();
        //String catName = launchingIntent.getStringExtra("catname");
        View viewer = inflater.inflate(R.layout.employee_details, container, false);
        //mCatDescriptionTextView = (TextView) viewer.findViewById(R.id.textViewDescription);

        log("details");
        return viewer;
    }



}
