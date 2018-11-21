package com.example.georg.savethepatient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementFragment extends Fragment {

    private String text;
    private long timePassed;
    private Button next;
    private TextView tvAnnouncment;

    public AnnouncementFragment() {
        // Required empty public constructor
    }


    public AnnouncementFragment(String text, long timePassed) {
        this.text = text;
        this.timePassed = timePassed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcement, container, false);

        tvAnnouncment = view.findViewById(R.id.tvAnnouncment);
        next = view.findViewById(R.id.btOK);

        tvAnnouncment.setText(text);

        if(text.equals(getString(R.string.first_level_intro))) {
            next.setText("Πάμε!");
        }
        else if(text.equals(getString(R.string.second_level_intro))) {
            next.setText("Πάμε!");
        }
        else if(text.equals(getString(R.string.third_level_intro))){
            next.setText("Πάμε!");
        }
        else if(text.equals(getString(R.string.you_lost))){
            next.setText("Μενού");
        }
        else if(text.equals(getString(R.string.you_won))){
            next.setText("Μενού");
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.equals(getString(R.string.first_level_intro))) {
                    ((MainActivity) getActivity()).switchLevel(timePassed, 1);
                }
                else if(text.equals(getString(R.string.second_level_intro))) {
                    ((MainActivity) getActivity()).switchLevel(timePassed, 2);
                }
                else if(text.equals(getString(R.string.third_level_intro))){
                    ((MainActivity) getActivity()).switchLevel(timePassed, 3);
                }
                else if(text.equals(getString(R.string.you_lost))){

                }
                else if(text.equals(getString(R.string.you_won))){

                }
            }
        });

        return view;
    }

}
