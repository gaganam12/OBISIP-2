import java.util.ArrayList;

public class Exam {
    private ArrayList<Question> questions;
    private int[] userAnswers;

    public Exam() {
        questions = new ArrayList<>();
        loadQuestions();
        userAnswers = new int[questions.size()];
    }

    private void loadQuestions() {
        questions.add(new Question("What is the capital of France?",
                new String[]{"1. Paris", "2. London", "3. Berlin", "4. Madrid"}, 1));
        questions.add(new Question("Which is the largest planet in our solar system?",
                new String[]{"1. Earth", "2. Jupiter", "3. Saturn", "4. Venus"}, 2));
        questions.add(new Question("Who wrote 'Hamlet'?",
                new String[]{"1. Dante", "2. Homer", "3. Shakespeare", "4. Goethe"}, 3));
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void submitAnswer(int questionIndex, int answer) {
        userAnswers[questionIndex] = answer;
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(userAnswers[i])) {
                score++;
            }
        }
        return score;
    }
}

