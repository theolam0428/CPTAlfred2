import arc.*;
import java.awt.Color;
public class TheoCPT{
	public static void main(String[] args){
		Console con = new Console("Multiple Choice Game", 1920, 1080);
		
		String[] quizList = new String[100];
		int intQuizCount;
		intQuizCount = 0;
		int intChoice;
		String strName;
		int intQuizFile;
		int intQuestionCount;
		intQuestionCount = 0;
		int intScore;
		intScore = 0;
		double dblPercentage;
		int intCount;
		int intCount1;
		
		Color backgroundColor = Color.BLACK;
		Color textColor = Color.WHITE;
		
		con.setBackgroundColor(backgroundColor);
		con.setTextColor(textColor);
		

		
		while(true){
			con.clear();
			con.println("=== MULTIPLE CHOICE QUIZ GAME ===");
			con.println("1) Play Game");
			con.println("2) View Leaderboard");
			con.println("3) Add Quiz");
			con.println("4) Settings");
			con.println("5) Quit Game");
			con.println("Enter a choice: ");
			intChoice = con.readInt();
			
			if(intChoice == 1){
				con.clear();
				con.println("Enter your name: ");
				strName = con.readLine();
				
				TextInputFile master = new TextInputFile("master.txt");
				intQuizCount = 0;
				
				while(master.eof() != true){
					quizList[intQuizCount] = master.readLine();
					intQuizCount++;
				}
			
	
			con.clear();
			con.println("Available Quizzes: ");
			
			for(intCount = 0; intCount < intQuizCount; intCount++){
				con.println((intCount+1)+") " + quizList[intCount]);
			}

			while(true){
				con.print("Type the quiz number you want to play: ");
				intQuizFile = con.readInt();
				if(0 < intQuizFile && intQuizFile < 3){
					break;
				}else{
					con.println("Invalid input. Try again");
				}
			}
			
			// Load the selected quiz into the array
			String[][] strQuizData = new String[100][7];
			TextInputFile quiz;
			if(intQuizFile == 1){
				quiz = new TextInputFile("general.txt");
			}else if(intQuizFile == 2){
				quiz = new TextInputFile("vocabulary.txt");
			}else{
				quiz = new TextInputFile("general.txt");
			}
			
			while(quiz.eof() != true){
				for(intCount = 0; intCount < 6; intCount++){
					strQuizData[intQuestionCount][intCount] = quiz.readLine();
				}
				strQuizData[intQuestionCount][6] = String.valueOf((int)(Math.random()*100+1));
				intQuestionCount++;
			}
			
			// Bubble sort
			intCount1 = 0;
			for(intCount = 0; intCount < intQuestionCount - 1; intCount++){
				for(intCount1 = 0; intCount1 < intQuestionCount - intCount - 1; intCount1++){
					if(Integer.parseInt(strQuizData[intCount1][6])>Integer.parseInt(strQuizData[intCount1+1][6])){
						String[] temp = strQuizData[intCount1];
						strQuizData[intCount1] = strQuizData[intCount+1];
						strQuizData[intCount1+1] = temp;
					}
				}
			}
			
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

                dblPercentage = (intScore * 100) / intQuestionCount;

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
                con.clear();
                con.println("=== LEADERBOARD ===");

                String[][] leaderboard = new String[100][3];
                int leaderCount = 0;
                TextInputFile in = new TextInputFile("leaderboard.txt");
                while(in.eof() != true){
					leaderboard[leaderCount][0] = in.readLine();
					leaderboard[leaderCount][1] = in.readLine();
					leaderboard[leaderCount][2] = in.readLine();
					leaderCount++;         
                }

                // Sort by score
                for (intCount = 0; intCount < leaderCount - 1; intCount++) {
                    for (intCount1 = 0; intCount1 < leaderCount - intCount - 1; intCount1++) {
                        if (Integer.parseInt(leaderboard[intCount1][2]) < Integer.parseInt(leaderboard[intCount1 + 1][2])) {
                            String[] temp = leaderboard[intCount1];
                            leaderboard[intCount1] = leaderboard[intCount1 + 1];
                            leaderboard[intCount1 + 1] = temp;
                        }
                    }
                }

                for (intCount = 0; intCount < leaderCount; intCount++) {
                    con.println(leaderboard[intCount][0] + " | " + leaderboard[intCount][1] + " | " + leaderboard[intCount][2] + "%");
                }

                con.println("Press Enter to return to menu...");
                con.readLine();

            } else if (intChoice == 3) {
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

                // Append to quizzes.txt
                TextOutputFile updateMaster = new TextOutputFile("quizzes.txt", true);
                updateMaster.println(newQuiz);
                updateMaster.close();

                con.println("Quiz created and saved!");
                con.println("Press Enter to return to menu...");
                con.readLine();

            }else if (intChoice == 4) {
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

				// Convert string to Color
				backgroundColor = getColorFromName(bgColorName);
				textColor = getColorFromName(textColorName);

				// Apply the colors
				con.setBackgroundColor(backgroundColor);
				con.setTextColor(textColor);
				con.clear();
				con.println("Colors updated. Press Enter to return to the menu.");
				con.readLine();

							}else if (intChoice == 5) {
								con.println("Thanks for playing!");
								break;
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
						case "indigo": return new Color(75, 0, 130);  // Indigo RGB
						case "violet": return new Color(138, 43, 226); // Violet RGB
						case "black": return Color.BLACK;
						case "white": return Color.WHITE;
						default: return Color.BLACK; // Fallback color
					}
				}
			}

			
			
			
		
		
		
		
		
	

