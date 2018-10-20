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
import com.ami.fundapter.FunDapterUtils;
import com.ami.fundapter.extractors.StringExtractor;
import com.ami.fundapter.interfaces.FunDapterFilter;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    private CardStackView cardStackView;
    private ArrayList<Question> questions;
    private ProgressBar progressBar;
    private QuestionLoader questionLoader;
    private int cardCount = 0;


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
        questions = new ArrayList<>();
        questionLoader = new QuestionLoader(getContext());

        loadLevel(1);

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

        final FunDapter funDapter = new FunDapter(getContext(), questions, R.layout.card_layout, bindDictionary);

        cardStackView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        cardStackView.setAdapter(funDapter);

        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {

            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                if(cardCount % 2 == 0){
                    if(questions.get(0).getType(numberfyDirection(direction)) == QuestionLoader.RIGHT){
                        questions.get(1).setQuest("Σωστό");
                    }
                    else questions.get(1).setQuest("Λάθος");
                }
                    questions.remove(0);
                    funDapter.updateData(questions);
                cardCount++;
            }

            @Override
            public void onCardReversed() {

            }

            @Override
            public void onCardMovedToOrigin() {

            }

            @Override
            public void onCardClicked(int index) {

            }
        });

    }

    private void loadLevel(int level){
        progressBar.setVisibility(View.VISIBLE);
        questions = questionLoader.getQuestionsList(level);
        ArrayList<Question> temp = new ArrayList<>();
        for(Question q: questions){
            temp.add(q);
            temp.add(new Question(""));
        }
        questions = temp;
        progressBar.setVisibility(View.GONE);
    }

    private int numberfyDirection(SwipeDirection direction){
        if(direction == SwipeDirection.Top) return 0;
        else if (direction == SwipeDirection.Right) return 1;
        else if (direction == SwipeDirection.Bottom) return 2;
        else return 3;
    }
}
