package com.example.model;

public class Question {
    private String Question;
    private String Answer;
    private String Choice1;
    private String Choice2;
    private String Choice3;
    private String Choice4;
    private String Subject;
    private String Title;

    public Question() {};

    public Question(String question, String answer, String choice1, String choice2, String choice3,
                    String choice4, String subject, String title) {
        Question = question;
        Answer = answer;
        Choice1 = choice1;
        Choice2 = choice2;
        Choice3 = choice3;
        Choice4 = choice4;
        Subject = subject;
        Title = title;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getChoice1() {
        return Choice1;
    }

    public void setChoice1(String choice1) {
        Choice1 = choice1;
    }

    public String getChoice2() {
        return Choice2;
    }

    public void setChoice2(String choice2) {
        Choice2 = choice2;
    }

    public String getChoice3() {
        return Choice3;
    }

    public void setChoice3(String choice3) {
        Choice3 = choice3;
    }

    public String getChoice4() {
        return Choice4;
    }

    public void setChoice4(String choice4) {
        Choice4 = choice4;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
