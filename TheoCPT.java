// Theodore Lam
// Multiple Choice Game
// June 12, 2025
// Version 3.4

import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class TheoCPT {
    public static void main(String[] args) {
        Console con = new Console("QUIZ QUEST (Multiple Choice Game)", 1280, 720);

        String[] quizList = new String[100];
        int intQuizCount = 0;
        int intChoice;
        String strName;
        int intQuizFile;
        int intQuestionCount = 0;
        int intScore = 0;
        double dblPercentage;
        int intCount;
        int intCount1;

        // Setting default background and text colors
        Color backgroundColor = Color.WHITE;
        Color textColor = Color.BLACK;

        con.setBackgroundColor(backgroundColor);
        con.setTextColor(textColor);

        // Loading image of game logo
        BufferedImage imgLogo = con.loadImage("quizquest.png");

        // Printing the main menu
        while (true) {
            con.clear();
            con.println("=== WELCOME TO QUIZ QUEST!!!! ===");
            con.println("1) Play Game");
            con.println("2) View Leaderboard");
            con.println("3) Add Quiz");
            con.println("4) Change Screen Colors");
            con.println("5) Quit Game");
            con.println("6) Help");
            con.println("Enter a choice: ");
            con.drawImage(imgLogo, 830, -100);
            con.repaint();
            intChoice = con.readInt();

            if (intChoice == 1) {
                // PLAY GAME
                con.clear();
                con.println("Enter your name: ");
                strName = con.readLine();

                TextInputFile master = new TextInputFile("master.txt");
                intQuizCount = 0;
                while (!master.eof()) {
                    quizList[intQuizCount] = master.readLine();
                    intQuizCount++;
                }

                con.clear();
                con.println("Available Quizzes:");
                for (intCount = 0; intCount < intQuizCount; intCount++) {
                    con.println((intCount + 1) + ") " + quizList[intCount]);
                }

                while (true) {
                    con.print("Type the quiz number you want to play: ");
                    intQuizFile = con.readInt();
                    if (0 < intQuizFile && intQuizFile < 4) {
                        break;
                    } else {
                        con.println("Invalid input. Try again");
                    }
                }

                // Load quiz
                intQuestionCount = 0;
                String[][] strQuizData = new String[100][7];
                TextInputFile quiz;
                if (intQuizFile == 1) {
                    quiz = new TextInputFile("geography.txt");
                } else if (intQuizFile == 2) {
                    quiz = new TextInputFile("vocabulary.txt");
                } else if (intQuizFile == 3) {
                    quiz = new TextInputFile("food.txt");
                } else {
                    quiz = new TextInputFile("geography.txt");
                }

                while (!quiz.eof()) {
                    for (intCount = 0; intCount < 6; intCount++) {
                        strQuizData[intQuestionCount][intCount] = quiz.readLine();
                    }
                    strQuizData[intQuestionCount][6] = String.valueOf((int) (Math.random() * 100 + 1));
                    intQuestionCount++;
                }

                System.out.println("[DEBUG] Quiz questions loaded: " + intQuestionCount);

                // Bubble sort
                for (intCount = 0; intCount < intQuestionCount - 1; intCount++) {
                    for (intCount1 = 0; intCount1 < intQuestionCount - intCount - 1; intCount1++) {
                        if (Integer.parseInt(strQuizData[intCount1][6]) > Integer.parseInt(strQuizData[intCount1 + 1][6])) {
                            String[] temp = strQuizData[intCount1];
                            strQuizData[intCount1] = strQuizData[intCount1 + 1];
                            strQuizData[intCount1 + 1] = temp;
                        }
                    }
                }

                System.out.println("[DEBUG] Quiz questions sorted");

                // Gameplay
                for (intCount = 0; intCount < intQuestionCount; intCount++) {
                    con.clear();
                    con.println("Name: " + strName + " | Quiz: " + intQuizFile + " | Score: " + intScore);
                    con.println();
                    con.println((intCount + 1) + ". " + strQuizData[intCount][0]);
                    con.println("A) " + strQuizData[intCount][1]);
                    con.println("B) " + strQuizData[intCount][2]);
                    con.println("C) " + strQuizData[intCount][3]);
                    con.println("D) " + strQuizData[intCount][4]);
                    con.print("Your answer (A-D): ");
                    String ans = con.readLine().toUpperCase();
                    if (ans.length() > 0 && ans.charAt(0) == strQuizData[intCount][5].charAt(0)) {
                        con.println("Correct!");
                        intScore++;
                    } else {
                        con.println("Incorrect! The correct answer was " + strQuizData[intCount][5]);
                    }
                    con.println("Press Enter to continue...");
                    con.readLine();
                }

                dblPercentage = (intScore * 100.0) / intQuestionCount;
                con.clear();
                con.println("You scored: " + dblPercentage + "%");
                con.println("Press Enter to return to menu...");
                con.readLine();

                // Save to leaderboard
                TextOutputFile HighScores = new TextOutputFile("leaderboard.txt", true);
                HighScores.println(strName);
                HighScores.println(intQuizFile);
                HighScores.println(dblPercentage);
                HighScores.close();

            } else if (intChoice == 2) {
                // VIEW LEADERBOARD
                con.clear();
                con.println("=== LEADERBOARD ===");

                intQuizCount = 0;
                TextInputFile master = new TextInputFile("master.txt");
                while (!master.eof()) {
                    quizList[intQuizCount] = master.readLine();
                    intQuizCount++;
                }

                String[][] leaderboard = new String[100][3];
                int leaderCount = 0;
                TextInputFile in = new TextInputFile("leaderboard.txt");
                while (!in.eof()) {
                    leaderboard[leaderCount][0] = in.readLine(); // name
                    leaderboard[leaderCount][1] = in.readLine(); // quiz number
                    leaderboard[leaderCount][2] = in.readLine(); // score
                    leaderCount++;
                }

                System.out.println("[DEBUG] Loaded " + leaderCount + " leaderboard entries.");

                for (intCount = 0; intCount < leaderCount - 1; intCount++) {
                    for (intCount1 = 0; intCount1 < leaderCount - intCount - 1; intCount1++) {
                        if (Double.parseDouble(leaderboard[intCount1][2]) < Double.parseDouble(leaderboard[intCount1 + 1][2])) {
                            String[] temp = leaderboard[intCount1];
                            leaderboard[intCount1] = leaderboard[intCount1 + 1];
                            leaderboard[intCount1 + 1] = temp;
                        }
                    }
                }

                for (intCount = 0; intCount < leaderCount; intCount++) {
                    int quizIndex = Integer.parseInt(leaderboard[intCount][1]) - 1;
                    String quizName = (quizIndex >= 0 && quizIndex < intQuizCount) ? quizList[quizIndex] : "Unknown Quiz";
                    con.println(leaderboard[intCount][0] + " | " + quizName + " | " + leaderboard[intCount][2] + "%");
                }

                con.repaint();
                con.println("Press Enter to return to menu...");
                con.readLine();

            } else if (intChoice == 3) {
                // ADD QUIZ
                con.clear();
                con.print("Enter name of new quiz file (e.g. science.txt): ");
                String newQuiz = con.readLine();

                TextOutputFile newOut = new TextOutputFile("newQuiz");

                while (true) {
                    con.println("Type 'stop' as the question to end quiz creation.");
                    con.print("Enter question: ");
                    String q = con.readLine();
                    if (q.equalsIgnoreCase("stop")) break;

                    con.print("A) "); String a = con.readLine();
                    con.print("B) "); String b = con.readLine();
                    con.print("C) "); String c = con.readLine();
                    con.print("D) "); String d = con.readLine();
                    con.print("Correct answer (A/B/C/D): ");
                    String correct = con.readLine().toUpperCase();

                    newOut.println(q);
                    newOut.println(a);
                    newOut.println(b);
                    newOut.println(c);
                    newOut.println(d);
                    newOut.println(correct);
                }

                newOut.close();

                TextOutputFile updateMaster = new TextOutputFile("master.txt", true);
                updateMaster.println(newQuiz);
                updateMaster.close();

                con.println("Quiz created and saved!");
                con.println("Press Enter to return to menu...");
                con.readLine();

            } else if (intChoice == 4) {
                // SETTINGS
                con.clear();
                con.println("=== SETTINGS ===");
                con.println("Choose a background color:");
                con.println("Options: red, orange, yellow, green, blue, indigo, violet, black, white");
                con.print("Background color: ");
                String bgColorName = con.readLine().toLowerCase();

                con.println();
                con.println("Choose a text color:");
                con.println("Options: red, orange, yellow, green, blue, indigo, violet, black, white");
                con.print("Text color: ");
                String textColorName = con.readLine().toLowerCase();

                backgroundColor = getColorFromName(bgColorName);
                textColor = getColorFromName(textColorName);

                con.setBackgroundColor(backgroundColor);
                con.setTextColor(textColor);
                con.clear();
                con.println("Colors updated. Press Enter to return to the menu.");
                con.readLine();

            } else if (intChoice == 5) {
                // QUIT GAME
                con.println("Thanks for playing!");
                break;

            } else if (intChoice == 6) {
                // HELP
                con.clear();
                con.println("=== HELP MENU ===");
                con.println("Welcome to the Multiple Choice Game!");
                con.println("Here's what each option does:");
                con.println("1) Play Game - Choose a quiz and answer questions.");
                con.println("2) View Leaderboard - See top scores.");
                con.println("3) Add Quiz - Create your own quiz.");
                con.println("4) Settings - Change background/text color.");
                con.println("5) Quit Game - Exit the program.");
                con.println("6) Help - Show this help menu.");
                con.println("28) Secret - (Shhh!) Try it.");
                con.println("\nAnswer questions with A, B, C, or D.");
                con.println("Have fun and good luck!");
                con.println("Press Enter to return to the menu...");
                con.readLine();

            } else if (intChoice == 28) {
                // SECRET MENU
                con.clear();
                con.println("=== SECRET MENU ===");
                con.println("Here's your secret joke:");
                con.println("Why do Java developers wear glasses?");
                con.println("Because they can't C#! 😆");
                con.println("\nPress Enter to return to the menu...");
                con.readLine();

            } else {
                con.println("Invalid choice. Press Enter to try again...");
                con.readLine();
            }
        }
    }

    public static Color getColorFromName(String name) {
        name = name.toLowerCase();
        switch (name) {
            case "red": return Color.RED;
            case "orange": return Color.ORANGE;
            case "yellow": return Color.YELLOW;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "indigo": return new Color(75, 0, 130);
            case "violet": return new Color(138, 43, 226);
            case "black": return Color.BLACK;
            case "white": return Color.WHITE;
            default: return Color.BLACK;
        }
    }
}
