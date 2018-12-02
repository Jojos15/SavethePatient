package com.example.georg.savethepatient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private Button btStartGame;
    private Button btInstructions;
    private Button btAbout;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        btStartGame = view.findViewById(R.id.btStartGame);
        btInstructions = view.findViewById(R.id.btManual);
        btAbout = view.findViewById(R.id.btAbout);

        btStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).switchLevel(getString(R.string.first_level_intro));
            }
        });

        return view;
    }

}
