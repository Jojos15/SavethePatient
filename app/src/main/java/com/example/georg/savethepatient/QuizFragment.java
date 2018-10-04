package com.example.georg.savethepatient;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.extractors.StringExtractor;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    CardStackView cardStackView;
    ArrayList<Question> questions;
    ProgressBar progressBar;


    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardStackView = view.findViewById(R.id.quiz_main_card_stack_view);
        progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        questions = new ArrayList<>();
        QuestionLoader questionLoader = new QuestionLoader(getContext());
        questions = questionLoader.getQuestionsList(1);
        progressBar.setVisibility(View.GONE);


        BindDictionary<Question> bindDictionary = new BindDictionary<>();

        bindDictionary.addStringField(R.id.tvQuestion, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                return item.getQuest();
            }
        });

        bindDictionary.addStringField(R.id.tvAnswerA, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                return item.getAnswers(0);
            }
        });
        bindDictionary.addStringField(R.id.tvAnswerB, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                return item.getAnswers(1);
            }
        });
        bindDictionary.addStringField(R.id.tvAnswerC, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                return item.getAnswers(2);
            }
        });
        bindDictionary.addStringField(R.id.tvAnswerD, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                return item.getAnswers(3);
            }
        });

        FunDapter funDapter = new FunDapter(getContext(), questions, R.layout.card_layout, bindDictionary);

        cardStackView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        cardStackView.setAdapter(funDapter);
    }
}
