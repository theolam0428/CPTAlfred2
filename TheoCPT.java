import arc.*;

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
			int intCount;
	
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
			int intCount1;
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
				
				
			}
			}
			
		}
		
		
		}
		
	

