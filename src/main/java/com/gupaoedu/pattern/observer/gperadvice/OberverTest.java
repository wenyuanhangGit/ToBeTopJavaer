package com.gupaoedu.pattern.observer.gperadvice;

public class OberverTest {

    public static void main(String[] args) {
        GPer gper = GPer.getInstance();
        Teacher tom = new Teacher("Tom");


        Question question = new Question();
        question.setUsername("小明");
        question.setContent("观察者设计模式使用于哪些场景？");
        gper.addObserver(tom);
        gper.publishQuestion(question);
    }

}
