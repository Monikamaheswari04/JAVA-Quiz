import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class JavaQuizApp extends JFrame implements ActionListener {
    private JLabel questionLabel, timerLabel;
    private JRadioButton[] options;
    private JButton nextButton;
    private ButtonGroup optionGroup;
    private List<String[]> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timer = 15;
    private Timer countdownTimer;

    public JavaQuizApp() {
        setTitle("Java Quiz App");
        setSize(700, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Top panel with timer
        JPanel topPanel = new JPanel(new FlowLayout());
        timerLabel = new JLabel("Time: " + timer);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.BLUE);
        topPanel.add(timerLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel with question and options
        JPanel centerPanel = new JPanel(new GridLayout(5, 1));
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(questionLabel);

        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            centerPanel.add(options[i]);
        }
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with Next button
        JPanel bottomPanel = new JPanel();
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadHardcodedQuestions();
        startQuiz();
    }

    private void loadHardcodedQuestions() {
        questions = new ArrayList<>();
        questions.add(new String[]{"What is the size of int in Java?", "2", "4", "8", "Depends on OS", "4"});
        questions.add(new String[]{"Which keyword is used to inherit a class in Java?", "implement", "inherits", "extends", "super", "extends"});
        questions.add(new String[]{"Which of the following is not a Java feature?", "Object-Oriented", "Use of pointers", "Portable", "Dynamic", "Use of pointers"});
        questions.add(new String[]{"Which method is the entry point of a Java program?", "start()", "main()", "run()", "init()", "main()"});
        questions.add(new String[]{"What is the default value of boolean variable?", "true", "false", "0", "null", "false"});
        questions.add(new String[]{"Which collection does not allow duplicates?", "List", "ArrayList", "Set", "Map", "Set"});
        questions.add(new String[]{"Which operator is used for object comparison?", "==", "equals()", "!=", "<>", "equals()"});
        questions.add(new String[]{"Which keyword is used to define a constant?", "var", "final", "const", "static", "final"});
        questions.add(new String[]{"What is JVM?", "Java Visual Machine", "Java Very Machine", "Java Virtual Machine", "None", "Java Virtual Machine"});
        questions.add(new String[]{"Which company developed Java?", "Microsoft", "Apple", "Sun Microsystems", "Google", "Sun Microsystems"});
    }

    private void startQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        showQuestion();
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            String[] q = questions.get(currentQuestionIndex);
            questionLabel.setText("Q" + (currentQuestionIndex + 1) + ". " + q[0]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(q[i + 1]);
                options[i].setSelected(false);
            }
            startTimer();
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        timer = 15;
        timerLabel.setText("Time: " + timer);
        if (countdownTimer != null) countdownTimer.stop();
        countdownTimer = new Timer(1000, e -> {
            timer--;
            timerLabel.setText("Time: " + timer);
            if (timer <= 0) {
                countdownTimer.stop();
                checkAnswerAndProceed();
            }
        });
        countdownTimer.start();
    }

    private void checkAnswerAndProceed() {
        String selected = null;
        for (JRadioButton opt : options) {
            if (opt.isSelected()) {
                selected = opt.getText();
                break;
            }
        }
        String correct = questions.get(currentQuestionIndex)[5];
        if (selected != null && selected.equals(correct)) {
            score++;
        }
        currentQuestionIndex++;
        showQuestion();
    }

    private void endQuiz() {
        double percentage = (score * 100.0) / questions.size();
        String comment;
        if (score == questions.size()) {
            comment = "🏆 Excellent! You're a Java master!";
        } else if (percentage >= 70) {
            comment = "🎉 Good job! Keep practicing!";
        } else if (percentage >= 50) {
            comment = "🙂 Not bad! Review the weak areas!";
        } else {
            comment = "😅 Keep going! More practice will help!";
        }

        JOptionPane.showMessageDialog(this,
            "Quiz Over!\nYour Score: " + score + "/" + questions.size() + "\n" + comment,
            "Quiz Result",
            JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            if (countdownTimer != null) countdownTimer.stop();
            checkAnswerAndProceed();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JavaQuizApp().setVisible(true));
    }
}
