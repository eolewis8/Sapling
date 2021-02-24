package com.example.sapling;

import android.provider.BaseColumns;

public class QuestionsInfoContract {
    private QuestionsInfoContract() {}
    public static class Questions implements BaseColumns {
        public static final String TABLE_NAME = "QuestionsInfo";
        public static final String QUESTION_SUBJECT = "subject";
        public static final String QUESTION_TITLE = "title";
        public static final String QUESTION = "question";
        public static final String QUESTION_CHOICE1 = "choice1";
        public static final String QUESTION_CHOICE2 = "choice2";
        public static final String QUESTION_CHOICE3 = "choice3";
        public static final String QUESTION_CHOICE4 = "choice4";
        public static final String QUESTION_ANSWER = "answer";
    }
}
