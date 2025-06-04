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
			intCount = 0;
			con.clear();
			con.println("Available Quizzes: ");
			
			for(intCount = 0; intCount < intQuizCount; intCount++){
				con.println((intCount+1)+") " + quizList[intCount]);
			}

			while(true){
				con.print("Type the quiz number you want to play: ");
				intQuizFile = con.readInt();
				if(intQuizFile > 0 && intQuizFile < 3){
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
			}
			
			}
			
		}
		
		
		}
		
	}

