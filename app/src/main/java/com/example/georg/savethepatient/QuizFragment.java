package com.example.georg.savethepatient;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import static java.lang.Math.ceil;


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
    private int levelTime;
    private int level;
    private boolean timeFinished = false;
    private TextView tvOverallTime;
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private boolean lost = false;
    private CountDownTimer countDownTimer;

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {
                long raw = ((MainActivity) getActivity()).getTimeInSeconds();


                tvOverallTime.setText(timeFormater(raw));
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                //also call the same runnable to call it at regular interval
                handler.postDelayed(this, 1000);
            }
        }
    };


    public QuizFragment(int level, int levelTime) {
        this.level = level;
        this.levelTime = levelTime;
    }


    public QuizFragment() {

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
        tvOverallTime = view.findViewById(R.id.tvOverallTime);
        heart1 = view.findViewById(R.id.imageView1);
        heart2 = view.findViewById(R.id.imageView2);
        heart3 = view.findViewById(R.id.imageView3);

        if (level == 2) {
            heart1.setVisibility(View.INVISIBLE);
        } else if (level == 3) {
            heart1.setVisibility(View.INVISIBLE);
            heart2.setVisibility(View.INVISIBLE);
        }

        loadLevel(level);
        tvLevel.setText("Επίπεδο " + level);
        tvQuestion.setText("Ερώτηση 1");


        handler.post(runnable);

         countDownTimer = new CountDownTimer((levelTime + 1) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressI++;
                timerText.setText((int) ceil(millisUntilFinished / 1000) + "");
                timer.setProgress(100 - (progressI * 100 / 46));
            }

            @Override
            public void onFinish() {
                //Do what you want
                progressI++;
                timer.setProgress(0);
                timerText.setText("0");
                timeFinished = true;
                swipeRight();
            }
        };

        BindDictionary<Question> bindDictionary = new BindDictionary<>();

        bindDictionary.addStringField(R.id.tvQuestion, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                return item.getQuest();
            }
        });

        bindDictionary.addStringField(R.id.tvIfWrong, new StringExtractor<Question>() {
            @Override
            public String getStringValue(Question item, int position) {
                return item.getTextToDisplay();
            }
        }).visibilityIfNull(View.GONE);

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
        timerText.setText(levelTime + "");


        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                ///////MAKE A CASE WHEN X OR Y IS 100 PERCENT AND THERE IS NO TOUCH ON THE SCREEN TO HIDE
                ///////THE CARDSTACKVIEW
            }

            @Override
            public void onCardSwiped(final SwipeDirection direction) {
                if (questions.size() != 1) {
                    if (cardCount % 2 == 0) {
                        lost = false;
                        cardStackView.setEnabled(false);
                        countDownTimer.cancel();
                        Timer swipeCard = new Timer();
                        if (timeFinished) {
                            questions.get(1).setQuest("Τέλος Χρόνου");
                            questions.get(1).setTextToDisplay(onWrongAnswer());
                            timeFinished = false;
                        } else if (questions.get(0).getType(numberfyDirection(direction)) == QuestionLoader.RIGHT) {
                            questions.get(1).setQuest("Σωστό");
                        }
                        else if(questions.get(0).getType(numberfyDirection(direction)) == QuestionLoader.IMPLICATION){
                            questions.get(1).setQuest("Επιπλοκή");
                            questions.set(2, questions.get(0).getImplication());
                            questions.get(1).setTextToDisplay(getString(R.string.there_was_an_implication));
                        }
                        else {
                            questions.get(1).setQuest("Λάθος");

                            if(level==1){
                                if(!((MainActivity) getActivity()).getLives(2)){
                                    lost = true;
                                }
                            }
                            else if(level==2){
                                if(!((MainActivity) getActivity()).getLives(4)){
                                    lost = true;
                                }
                            }
                            else{
                                if(!((MainActivity) getActivity()).getLives(5)){
                                    lost = true;
                                }
                            }

                            questions.get(1).setTextToDisplay( onWrongAnswer() + "\n\n" + "Σωστη απαντηση: " + questions.get(0).getRightAnswer());
                        }

                        if (!(lost)) {
                            swipeCard.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    swipeRight();
                                }
                            }, 3000);
                        }

                    }
                    questions.remove(0);
                    funDapter.updateData(questions);
                    Log.d("fundapter", "Updated");
                    cardCount++;
                } else {
                    if (level == 1) {
                        ((MainActivity) getActivity()).switchLevel(getString(R.string.second_level_intro));
                        onDestroy();
                    } else if (level == 2) {
                        ((MainActivity) getActivity()).switchLevel(getString(R.string.third_level_intro));
                        onDestroy();
                    } else if (level == 3) {
                        ((MainActivity) getActivity()).switchLevel(getString(R.string.you_won)
                                + "\n\nΣυνολικός Χρόνος: " + tvOverallTime.getText().toString()
                                + "\n\nΚαρδιές που απομένουν: " + countHearts());
                        onDestroy();
                    }
                }
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


    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    private void loadLevel(int level) {
        progressBar.setVisibility(View.VISIBLE);
        questions = questionLoader.getQuestionsList(level);
        ArrayList<Question> temp = new ArrayList<>();
        for (Question q : questions) {
            temp.add(q);
            temp.add(new Question("", ""));
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
                Log.d("card", "swiped");
                if (!timeFinished) {
                    tvQuestion.setText("Ερώτηση " + (cardCount / 2 + 2));
                    progressI = 0;
                    countDownTimer.start();
                    cardStackView.setEnabled(true);
                }
            }
        });
    }

    public String onWrongAnswer() {
        String text = "";
        if (level == 1) {
            if (((MainActivity) getActivity()).getLives(0)) {
                text = getString(R.string.first_life_lost);
                ((MainActivity) getActivity()).setLives(false, 0);
                heart1.setImageResource(R.drawable.ic_heart_grey);
            } else if (((MainActivity) getActivity()).getLives(1)) {
                text = getString(R.string.second_life_lost);
                ((MainActivity) getActivity()).setLives(false, 1);
                heart2.setImageResource(R.drawable.ic_heart_grey);
            } else if (((MainActivity) getActivity()).getLives(2)) {
                text = getString(R.string.third_life_lost);
                ((MainActivity) getActivity()).setLives(false, 2);
                heart3.setImageResource(R.drawable.ic_heart_grey);

            } else {
                ((MainActivity) getActivity()).switchLevel(getString(R.string.you_lost));
                onDestroy();
            }
        } else if (level == 2) {
            if (((MainActivity) getActivity()).getLives(3)) {
                text = getString(R.string.first_life_lost);
                ((MainActivity) getActivity()).setLives(false, 3);
                heart2.setImageResource(R.drawable.ic_heart_grey);
            } else if (((MainActivity) getActivity()).getLives(4)) {
                text = getString(R.string.third_life_lost);
                ((MainActivity) getActivity()).setLives(false, 4);
                heart3.setImageResource(R.drawable.ic_heart_grey);

            } else { //LOOSE
                ((MainActivity) getActivity()).switchLevel(getString(R.string.you_lost));
                onDestroy();
            }
        } else {
            if (((MainActivity) getActivity()).getLives(5)) {
                text = getString(R.string.third_life_lost);
                ((MainActivity) getActivity()).setLives(false, 5);
                heart3.setImageResource(R.drawable.ic_heart_grey);


            } else { //LOOSE
                ((MainActivity) getActivity()).switchLevel(getString(R.string.you_lost));
                onDestroy();
            }
        }

        return text;
    }


    private int numberfyDirection(SwipeDirection direction) {
        if (direction == SwipeDirection.Top) return 0;
        else if (direction == SwipeDirection.Right) return 1;
        else if (direction == SwipeDirection.Bottom) return 2;
        else return 3;
    }

    public String timeFormater(long timeToConvert){
        long min = timeToConvert / 60;
        long secs = timeToConvert % 60;
        String seconds;
        String minutes;

        if (secs < 10) seconds = "0" + Long.toString(secs);
        else seconds = Long.toString(secs);

        if (min < 10) minutes = "0" + Long.toString(min);
        else minutes = Long.toString(min);

        return (minutes + ":" + seconds);
    }

    private int countHearts(){
        int border = ((MainActivity)getActivity()).getLives().length - 1;

        int sum = 0;
        for(int i = 0; i<border; i++){
            if(((MainActivity)getActivity()).getLives(i)){
                sum++;
            }
        }

        return sum;
    }
}