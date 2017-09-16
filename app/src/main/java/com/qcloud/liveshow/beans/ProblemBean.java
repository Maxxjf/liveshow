package com.qcloud.liveshow.beans;

/**
 * 类说明：常见问题
 * Author: Kuzan
 * Date: 2017/9/16 16:20.
 */
public class ProblemBean {
    long id;            // id
    String question;    // 问题
    String answer;      // 答案

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ProblemBean{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
