package api;

public class Voc {
    private String answer,question,language;
    public Voc(String a, String q, String l) {
        this.answer = a;
        this.question = q;
        this.language = l;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
