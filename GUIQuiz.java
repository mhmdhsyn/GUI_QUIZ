//Importing needed libraries
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;

//Class declaration
public class GUIQuiz implements ActionListener {
    //Subclass declaration of Quiz Questions
    class QuizQuestion {
        private String questionText;
        private String answers[];
        private int correctAnswer;
        final int MAX_QUESTION = 4;
        QuizQuestion(String qt){
            questionText = qt;
            answers = new String[MAX_QUESTION];
        }
        void addQuestion(int index, String answerText) {
            answers[index] = answerText;
        }
        void setCorrectAnswer(int answerNumber) {
            correctAnswer = answerNumber;
        }

    }


    Random rand = new Random(); //initiating random generator

    int[] questions_chosen = new int[] {0,1,2,3,4}; //array to keep index of the questions chosen
    int[] user_input = new int[] {-1,-1,-1,-1,-1}; //Array to keep answers of the user
    int[] correct_answers = new int[5]; //Array to keep the correct answer for the chosen questions
    int score = 0;
    int question_number = 0;
    JRadioButton option1;
    JRadioButton option2;
    JRadioButton option3;
    JRadioButton option4;
    JButton bRestart;
    JButton bBack;
    JButton bNext;
    JButton bSubmit;
    JLabel text;
    ButtonGroup group;
    private ArrayList<QuizQuestion> quizGame = new ArrayList<>();  //Array list to keep the question bank
    //Function to read the file and store its content into an arraylist
    public void readFile(){
        File qst_file = new File("mcq.txt");
        Scanner infile = null;
        try {
            infile = new Scanner(qst_file);
        } catch (FileNotFoundException e) {
            System.out.println("Files does not exist.");
            e.printStackTrace();
        }
        String temp_process; //String to hold input
        QuizQuestion temp_qst; //Temporary question to keep the input and store it in the arraylist
        while(infile.hasNextLine()){
            temp_process = infile.nextLine();
            temp_process = temp_process.substring(1);
            temp_qst = new QuizQuestion(temp_process);
            //For loop to read 4 lines the question and possible answers
            for (int i =0; i < temp_qst.MAX_QUESTION; i++){
                temp_process = infile.nextLine();
                if (temp_process.charAt(0) == '>'){
                    temp_process = temp_process.substring(1);
                    temp_qst.setCorrectAnswer(i);
                }
                temp_qst.addQuestion(i, temp_process);
            }
            //Adding the question to arraylist
            quizGame.add(temp_qst);
        }

        infile.close();//Closing the file
    }
    //Function to draw the application
    public void draw(){
        //Picking questions
        pick_questions();
        JFrame frame = new JFrame("Quiz!");
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(2,2));
        optionPanel.setPreferredSize(new Dimension(100,200));
        option1 = new JRadioButton(quizGame.get(questions_chosen[question_number]).answers[0]);
        option2 = new JRadioButton(quizGame.get(questions_chosen[question_number]).answers[1]);
        option3 = new JRadioButton(quizGame.get(questions_chosen[question_number]).answers[2]);
        option4 = new JRadioButton(quizGame.get(questions_chosen[question_number]).answers[3]);


        option1.addActionListener(this);
        option2.addActionListener(this);
        option3.addActionListener(this);
        option4.addActionListener(this);

        group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);


        optionPanel.add(option1);
        optionPanel.add(option2);
        optionPanel.add(option3);
        optionPanel.add(option4);

        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new GridLayout(1,4));
        commandPanel.setPreferredSize(new Dimension(100,100));
        bRestart = new JButton("Restart");
        bBack = new JButton("Back");
        bNext = new JButton("Next");
        bSubmit = new JButton("Submit");

        bRestart.addActionListener(this);
        bBack.addActionListener(this);
        bBack.setEnabled(false);
        bNext.addActionListener(this);
        bSubmit.addActionListener(this);



        commandPanel.add(bRestart, BorderLayout.WEST);
        commandPanel.add(bBack, BorderLayout.WEST);
        commandPanel.add(bNext, BorderLayout.WEST);
        commandPanel.add(bSubmit, BorderLayout.WEST);

        JPanel questionText = new JPanel();
        questionText.setLayout(new FlowLayout());
        questionText.setPreferredSize(new Dimension(100,200));
        text = new JLabel("Question " + (question_number + 1) + " of 5: " + quizGame.get(questions_chosen[question_number]).questionText, SwingConstants.CENTER);

        text.setPreferredSize(new Dimension(100,200));


        frame.setSize(new Dimension(500,500));
        frame.add(text,BorderLayout.NORTH);
        frame.add(commandPanel,BorderLayout.SOUTH);
        frame.add(optionPanel,BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Main function
    public static void main(String[] args){
        GUIQuiz test = new GUIQuiz();
        test.readFile();
        test.draw();

    }
    //Overriding the action performed function
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(bNext)){
            question_number++;
            text.setText("Question " + (question_number + 1) + " of 5: " + quizGame.get(questions_chosen[question_number]).questionText);
            option1.setText(quizGame.get(questions_chosen[question_number]).answers[0]);
            option2.setText(quizGame.get(questions_chosen[question_number]).answers[1]);
            option3.setText(quizGame.get(questions_chosen[question_number]).answers[2]);
            option4.setText(quizGame.get(questions_chosen[question_number]).answers[3]);
            group.clearSelection();
            //Showing the previous answer for the selected question
            if (user_input[question_number] == 0) option1.setSelected(true);
            else if (user_input[question_number] == 1) option2.setSelected(true);
            else if (user_input[question_number] == 2) option3.setSelected(true);
            else if (user_input[question_number] == 3) option4.setSelected(true);

        }
        else if (e.getSource().equals(bBack)){
            question_number--;
            text.setText("Question " + (question_number + 1) + " of 5: " + quizGame.get(questions_chosen[question_number]).questionText);
            option1.setText(quizGame.get(questions_chosen[question_number]).answers[0]);
            option2.setText(quizGame.get(questions_chosen[question_number]).answers[1]);
            option3.setText(quizGame.get(questions_chosen[question_number]).answers[2]);
            option4.setText(quizGame.get(questions_chosen[question_number]).answers[3]);
            group.clearSelection();
            if (user_input[question_number] == 0) option1.setSelected(true);
            else if (user_input[question_number] == 1) option2.setSelected(true);
            else if (user_input[question_number] == 2) option3.setSelected(true);
            else if (user_input[question_number] == 3) option4.setSelected(true);

        }
        else if (e.getSource().equals(bRestart)){
            pick_questions();
            for (int i =0; i < user_input.length; i++){
                user_input[i] = -1;
            }
            question_number = 0;
            bBack.setEnabled(false);
            score = 0;
            text.setText("Question " + (question_number + 1) + " of 5: " + quizGame.get(questions_chosen[question_number]).questionText);
            option1.setText(quizGame.get(questions_chosen[question_number]).answers[0]);
            option2.setText(quizGame.get(questions_chosen[question_number]).answers[1]);
            option3.setText(quizGame.get(questions_chosen[question_number]).answers[2]);
            option4.setText(quizGame.get(questions_chosen[question_number]).answers[3]);
            group.clearSelection();

            option1.setEnabled(true);
            option2.setEnabled(true);
            option3.setEnabled(true);
            option4.setEnabled(true);
            bSubmit.setEnabled(true);

        }
        else if (e.getSource().equals(bSubmit)){
            for(int i = 0; i < user_input.length;i++){
                if (user_input[i] == correct_answers[i]) score = score + 10;
            }
            JOptionPane.showMessageDialog(null,"Your score is: " + score);
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
            bSubmit.setEnabled(false);
        }
        else if (e.getSource().equals(option1)){
            user_input[question_number] = 0;
        }
        else if (e.getSource().equals(option2)){
            user_input[question_number] = 1;
        }
        else if (e.getSource().equals(option3)){
            user_input[question_number] = 2;
        }
        else if (e.getSource().equals(option4)){
            user_input[question_number] = 3;
        }

        //Disabling next and back button to prevent out of bound exception
        if (question_number >=4){
            bNext.setEnabled(false);
        }
        else bNext.setEnabled(true);

        if(question_number <=0){
            bBack.setEnabled(false);
        }
        else bBack.setEnabled(true);

    }
    //A function to pick random questions and their answers
    void pick_questions (){

        //Picking 5 different random numbers and storing them in question_chosen array
        questions_chosen = new Random().ints(0,quizGame.size()).distinct().limit(5).toArray();
        //Finding answer to each of the questions
        for (int i : correct_answers){
            correct_answers[i] = quizGame.get(questions_chosen[i]).correctAnswer;
        }
        System.out.print(Arrays.toString(questions_chosen));
    }
}
