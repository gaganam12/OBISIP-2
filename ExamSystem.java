import java.util.Scanner;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ExamSystem {
    private HashMap<String, User> users;
    private User loggedInUser;
    private Exam currentExam;
    private Timer timer;

    public ExamSystem() {
        users = new HashMap<>();
        // Adding a sample user
        users.put("user1", new User("user1", "password1", "User One"));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        loggedInUser = login(username, password);

        if (loggedInUser == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("Welcome, " + loggedInUser.getProfile());

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateProfile(scanner);
                    break;
                case 2:
                    updatePassword(scanner);
                    break;
                case 3:
                    startExam(scanner);
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private void updateProfile(Scanner scanner) {
        System.out.print("Enter new profile name: ");
        scanner.nextLine(); // consume the newline
        String newProfile = scanner.nextLine();
        loggedInUser.updateProfile(newProfile);
        System.out.println("Profile updated successfully.");
    }

    private void updatePassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        scanner.nextLine(); // consume the newline
        String newPassword = scanner.nextLine();
        loggedInUser.updatePassword(newPassword);
        System.out.println("Password updated successfully.");
    }

    private void startExam(Scanner scanner) {
        currentExam = new Exam();
        System.out.println("Exam started! You have 60 seconds.");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Submitting your exam.");
                submitExam();
            }
        }, 60000); // 60 seconds timer

        for (int i = 0; i < currentExam.getQuestions().size(); i++) {
            Question question = currentExam.getQuestions().get(i);
            System.out.println("\n" + (i + 1) + ". " + question.getQuestionText());
            for (String option : question.getOptions()) {
                System.out.println(option);
            }
            System.out.print("Enter your answer (1-4): ");
            int answer = scanner.nextInt();
            currentExam.submitAnswer(i, answer);
        }

        submitExam();
    }

    private void submitExam() {
        timer.cancel();
        int score = currentExam.calculateScore();
        System.out.println("Exam submitted! Your score is: " + score + "/" + currentExam.getQuestions().size());
    }

    private void logout() {
        System.out.println("Logging out...");
        loggedInUser = null;
    }
}

