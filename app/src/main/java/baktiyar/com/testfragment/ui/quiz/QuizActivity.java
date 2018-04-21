package baktiyar.com.testfragment.ui.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import baktiyar.com.testfragment.R;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton rdBtnAlways, rdBtnFrequently, rdBtnSometimes, rdBtnInfrequently, rdBtnNever;
    RadioGroup rdBtnGroup;
    private TextView tvQuizQuestion;
    private Button btnNext;
    private int score = 0;
    private int questionPosition = 0;

    List<String> myResArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        String[] myResArray = getResources().getStringArray(R.array.questions);
        myResArrayList = Arrays.asList(myResArray);
        init();
    }

    private void init() {
        initToolbar();
        initActivity();
    }

    private void initToolbar() {
        getSupportActionBar().setTitle(R.string.quiz_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initActivity() {
        rdBtnAlways = findViewById(R.id.rdBtnAlways);
        rdBtnFrequently = findViewById(R.id.rdBtnFrequently);
        rdBtnSometimes = findViewById(R.id.rdBtnSometimes);
        rdBtnInfrequently = findViewById(R.id.rdBtnInfrequently);
        rdBtnNever = findViewById(R.id.rdBtnNever);
        rdBtnGroup = findViewById(R.id.rdBtnGroup);

        tvQuizQuestion = findViewById(R.id.tvQuizQuestion);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        tvQuizQuestion.setText(myResArrayList.get(questionPosition));

    }

    private int setPointFromRdBtn() {
        if (rdBtnAlways.isChecked()) return 5;
        else if (rdBtnFrequently.isChecked()) return 4;
        else if (rdBtnSometimes.isChecked()) return 3;
        else if (rdBtnInfrequently.isChecked()) return 2;
        else if (rdBtnNever.isChecked()) return 1;
        else return 0;
    }

    private void unCheckRdBtns() {
        rdBtnAlways.setChecked(false);
        rdBtnFrequently.setChecked(false);
        rdBtnSometimes.setChecked(false);
        rdBtnInfrequently.setChecked(false);
        rdBtnNever.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        if (v == btnNext) {
            goToNextQuestion();
        }
    }

    private void goToNextQuestion() {
        changeQuestion();
        unCheckRdBtns();
    }


    private void changeQuestion() {
        questionPosition++;
        score += setPointFromRdBtn();
        if (questionPosition == myResArrayList.size()) {
            giveResult();
        } else {
            tvQuizQuestion.setText(myResArrayList.get(questionPosition));
        }
    }

    private void giveResult() {
        rdBtnGroup.setVisibility(View.GONE);
        tvQuizQuestion.setText("Ваше очко: " + score);
        btnNext.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
