package com.example.georg.savethepatient;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.extractors.StringExtractor;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    private CardStackView cardStackView;
    private ArrayList<Question> questions;
    private ProgressBar progressBar;
    private QuestionLoader questionLoader;
    private int cardCount = 0;
    private TextView tvLevel;
    private TextView tvQuestion;
    private CircularProgressBar timer;
    private TextView timerText;
    private int progressI = 0;
    private CountDownTimer countDownTimer = new CountDownTimer(45000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            progressI++;
            timerText.setText((45-progressI) +"");
            timer.setProgress((int) progressI * 100 / (45000 / 1000));
            Log.d("Mills unt fin", millisUntilFinished + "");
        }

        @Override
        public void onFinish() {
            //Do what you want
            progressI++;
            timer.setProgress(100);
        }
    };


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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardStackView = view.findViewById(R.id.quiz_main_card_stack_view);
        progressBar = view.findViewById(R.id.progressBar2);
        questions = new ArrayList<>();
        questionLoader = new QuestionLoader(getContext());
        tvLevel = view.findViewById(R.id.tvLevel);
        timerText = view.findViewById(R.id.timerTimeText);
        tvQuestion = view.findViewById(R.id.tvQuestionNu);
        timer = view.findViewById(R.id.progressBar);

        loadLevel(1);
        tvLevel.setText("Επίπεδο 1");
        tvQuestion.setText("Ερώτηση 1");


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
        bindDictionary.addStringField(R.id.textViewStableA, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                if (item.isHasAnswers()) return "Α.";
                return null;
            }
        });
        bindDictionary.addStringField(R.id.textViewStableB, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                if (item.isHasAnswers()) return "Β.";
                return null;
            }
        });
        bindDictionary.addStringField(R.id.textViewStableC, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                if (item.isHasAnswers()) return "Γ.";
                return null;
            }
        });
        bindDictionary.addStringField(R.id.textViewStableD, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                if (item.isHasAnswers()) return "Δ.";
                return null;
            }
        });

        final FunDapter funDapter = new FunDapter(getContext(), questions, R.layout.card_layout, bindDictionary);

        cardStackView.setAdapter(funDapter);
        cardStackView.setBackgroundColor(Color.TRANSPARENT);
        countDownTimer.start();
        timerText.setText("45");


        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {

            }

            @Override
            public void onCardSwiped(final SwipeDirection direction) {
                if (cardCount % 2 == 0) {
                    cardStackView.setEnabled(false);
                    countDownTimer.cancel();
                    Timer swipeCard = new Timer();
                    if (questions.get(0).getType(numberfyDirection(direction)) == QuestionLoader.RIGHT) {
                        questions.get(1).setQuest("Σωστό");
                    } else {
                        questions.get(1).setQuest("Λάθος");

                    }
                    swipeCard.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            swipeRight();
                        }
                    }, 1000);
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

    private void loadLevel(int level) {
        progressBar.setVisibility(View.VISIBLE);
        questions = questionLoader.getQuestionsList(level);
        ArrayList<Question> temp = new ArrayList<>();
        for (Question q : questions) {
            temp.add(q);
            temp.add(new Question(""));
        }
        questions = temp;
        progressBar.setVisibility(View.GONE);
    }

    public void swipeRight() {
        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View target = cardStackView.getTopView();
                View targetOverlay = cardStackView.getTopView().getOverlayContainer();

                ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                        target, PropertyValuesHolder.ofFloat("rotation", 10f));
                rotation.setDuration(200);
                ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                        target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f));
                ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                        target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
                translateX.setStartDelay(100);
                translateY.setStartDelay(100);
                translateX.setDuration(500);
                translateY.setDuration(500);
                AnimatorSet cardAnimationSet = new AnimatorSet();
                cardAnimationSet.playTogether(rotation, translateX, translateY);

                ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
                overlayAnimator.setDuration(800);
                AnimatorSet overlayAnimationSet = new AnimatorSet();
                overlayAnimationSet.playTogether(overlayAnimator);
                cardStackView.swipe(SwipeDirection.Right, cardAnimationSet, overlayAnimationSet);
                tvQuestion.setText("Ερώτηση " + (cardCount / 2 + 2));
                progressI = 0;
                countDownTimer.start();
                cardStackView.setEnabled(true);
            }
        });
    }

    private int numberfyDirection(SwipeDirection direction) {
        if (direction == SwipeDirection.Top) return 0;
        else if (direction == SwipeDirection.Right) return 1;
        else if (direction == SwipeDirection.Bottom) return 2;
        else return 3;
    }
}
