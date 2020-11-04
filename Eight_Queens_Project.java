/** 
ITCS 3135  
Project 1
*/

// Import.
import java.util.Random;

/** 
Public class - Eight_Queens_Program.
Description - A program that places 8 queens on an nxn board (already set up for 8)where none of the queens are in conflict with each other.  
              It implements the solution by using the Hill-Climbing algorithm with random restarts.
              For loops are mainly used to analyze and modift the array-represented board.
*/        
public class Eight_Queens_Project {
   /**
   Methood - main
   Description - Main functions.
   @param args - Standard string arguments for the main method.
   */
   public static void main(String[] args) {
   
      // Variables + Constants.
         // Game board.
      int heuristics = 0;
      int[][] gameBoard;
      int[][] heuristicsArray;
         // Output Info.
      final int OUTPUT_INFO = 4;
      int[] outputInfo = new int[OUTPUT_INFO];
         // Goal State.
      int goalStateStatus = 0; 
         // Standard Values.
      final int STANDARD_BOARD_SIZE = 8;
      final int RESTART_OUTPUT = 0;
      final int NEXT_OUTPUT = 1;
      final int SOLUTION_OUTPUT = 2;
          
      // Set-Up for Game Board.
      gameBoard = new int[STANDARD_BOARD_SIZE][STANDARD_BOARD_SIZE];
      heuristicsArray = new int[STANDARD_BOARD_SIZE][STANDARD_BOARD_SIZE];
                              
      // Set the initial State.
      setInitialState(STANDARD_BOARD_SIZE, gameBoard);
      
      // Get and check if initial state is a goal state.
      heuristics = getCurrentHeuristics(gameBoard, STANDARD_BOARD_SIZE);      
      goalStateStatus = checkGoalState(heuristics);
      
      if (goalStateStatus == 1) {
         outputInfo[3] = 2; // Set to solution found state.
         displayOutput(STANDARD_BOARD_SIZE, gameBoard, outputInfo);
      }
      else {                                       
         // Get all heuristics for the current board state.                                  
         while ( outputInfo[3] != 2) { // While better board version exists. 
            getAllHeuristics(gameBoard, STANDARD_BOARD_SIZE, heuristicsArray);
            outputInfo = setBetterState(gameBoard, STANDARD_BOARD_SIZE, heuristicsArray, outputInfo);
            displayOutput(STANDARD_BOARD_SIZE, gameBoard, outputInfo);                                    
         } // End loop.                                                    
      } // End if series.      
} // End Main.
      
   /**
   Method - setInitialState.
   Description - Sets the intial state of the game board.
                 A queen is placed in each column, but in a random row.
   @param gameBoardSize - The game board size for RowsxColumns.
   @param gameBoard - The 2-D array that represents the game board.
   */
   public static void setInitialState(int gameBoardSize, int[][] gameBoard) {
      // Variables + Constants.
      Random randomRow;
      int rowPosition;   
      // Set-Up;
      randomRow = new Random();      
      /*
      Create a total of 8 Queens.
      Place a queen in each column, but in a random row.
      */
      for ( int column = 0; column < 8; column++ ) {
            rowPosition = randomRow.nextInt(gameBoardSize);
            gameBoard[rowPosition][column] = 1;          
      } // End loop.            
   } // End method.

   /**
   Method - displayCurrentState.
   Description - Displays the current state of the game board.
                 Prints 2-D array values in a RowsxColumns box.
   @param gameBoardSize - The game board size for RowsxColumns.
   @param gameBoard - The 2-D array that represents the game board.
   */
   public static void displayCurrentState(int gameBoardSize, int[][] gameBoard) {
      /*
      Loops through every nxn space in the 2-D array.
      Prints each space in a formatted style.
      */
      for ( int row = 0; row < gameBoardSize; row++ ) {
         for ( int column = 0; column < gameBoardSize; column++ ) {
            System.out.print(gameBoard[row][column]);
            if (column < (gameBoardSize-1)) 
               System.out.print(",");                     
            if (column == (gameBoardSize-1)) 
               System.out.print("\n");                     
         } 
      }// End loop. 
   } // End method.

   /**
   Method - getCurrentHeuristics.
   Description  Gets the current heuristics for the current state of the board.
                 Heuristics are queen conflicts with one another.
                 A conflict between two queens is +1 heuristic.
                 Checks for 2+ queens in the same horizontal, vertical, and diagonal lines of a game board.
                 
   @param gameBoard - The 2-D array that represents the game board.
   @param heuristics - A 1-D array that contain the heuristics for each column.
   @param gameBoardSize - The n size (from nxn) of the game board.
   @return numberOfConflicts - The total heuristics for the current game board state.
   */
   public static int getCurrentHeuristics(int[][] gameBoard, int gameBoardSize) { 
      // Variables + Constants.
      int numberOfConflicts = 0;                                             
      int numberOfQueensInDiagonal = 0;
      int x; // Row position.
      int y; // Column position.
      int diagonalsScanned = 0; // Number of diagonals scanned.
      int maxSpaces = 0; // Max spaces that can be scanned in a diagonal.
      int scannedSpaces = 0; 
      int rowPosition = 0; 
      int columnPosition = 0;                    
           
      /*
      Gets the number of row (queen-conflicts) heuristics for the current board state.
      */                                              
      for ( int row = 0; row < gameBoardSize; row++) { // Go through every row.          
         int numberOfQueensInRow = 0; // Reset for the next row.
         for ( int column = 0; column < gameBoardSize; column++ ) { // Go through every column.
            if ( gameBoard[row][column] == 1)
               numberOfQueensInRow++; // Keep track for each row.         
            if (column == (gameBoardSize - 1) && numberOfQueensInRow > 1)
               numberOfConflicts += (numberOfQueensInRow - 1); // Add row conflicts.                                                            
         } 
      }// End loop.    
      
      /*
      Gets the number of diagonal (queen-conflicts) heuristics for the current board state.
      Diagonals are (/,\,|,-)
      There are (2n-1) diagonals for nxn game board.
      */             
      for( int n = 0; n < 3; n++) {            
               
         // Note - No column heuristics occur because each queen is in a unique column.
   
         // Scan the lower-half diagonals.
         if ( n == 0) { // Only below center diagonal. 
            
            // Top Left to Bottom Right (\).
            numberOfQueensInDiagonal = 0; // Reset for each diagonal scan                                                  
            for (diagonalsScanned = 0, x = (gameBoardSize - 1); diagonalsScanned < gameBoardSize-1 ; diagonalsScanned++, x--) { // Only scan lower diagonals.
               rowPosition = x;
               columnPosition = 0; 
               numberOfQueensInDiagonal = 0; // Reset for each diagonal.                             
               for ( maxSpaces = (diagonalsScanned), scannedSpaces = 0; scannedSpaces <= maxSpaces; scannedSpaces++) {                            
                  if (gameBoard[rowPosition][columnPosition] == 1)
                     numberOfQueensInDiagonal++;               
                  if ( scannedSpaces == maxSpaces && numberOfQueensInDiagonal > 1) // Reached end of diagonal.
                     numberOfConflicts += (numberOfQueensInDiagonal - 1); 
                  rowPosition++;
                  columnPosition++;      
               } // End loop.                                                             
            } // End loop.
            
            // Top Right to Bottom Left (/).
            for (diagonalsScanned = 0, y = (gameBoardSize - 1); diagonalsScanned < gameBoardSize-1 ; diagonalsScanned++, y--) { // Only scan lower diagonals.
               rowPosition = (gameBoardSize - 1);
               columnPosition = y; 
               numberOfQueensInDiagonal = 0; // Reset for each diagonal.                             
               for ( maxSpaces = (diagonalsScanned), scannedSpaces = 0; scannedSpaces <= maxSpaces; scannedSpaces++) {                            
                  if (gameBoard[rowPosition][columnPosition] == 1)
                     numberOfQueensInDiagonal++;               
                  if ( scannedSpaces == maxSpaces && numberOfQueensInDiagonal > 1) // Reached end of diagonal.
                     numberOfConflicts += (numberOfQueensInDiagonal - 1); 
                  rowPosition--;
                  columnPosition++;      
               } // End loop.                                                             
            } // End loop.  
                            
         } // End if. 
         
         else if ( n == 1) { // Scan the center diagonal.         
            // Top Left to Bottom Right (\).
            numberOfQueensInDiagonal = 0; // Reset for each diagonal scan.
            for( x = 0, y = 0; x < (gameBoardSize); x++, y++) {
               if (gameBoard[x][y] == 1)
                  numberOfQueensInDiagonal++;               
               if ( x == (gameBoardSize - 1) && numberOfQueensInDiagonal > 1)
                  numberOfConflicts += (numberOfQueensInDiagonal - 1);                          
            } // End loop.
            
            // Top Right to Bottom Left (/).
            numberOfQueensInDiagonal = 0; // Reset for each diagonal scan.
            for( x = (gameBoardSize - 1), y = 0; x >= 0; x--, y++) {
               if (gameBoard[x][y] == 1)
                  numberOfQueensInDiagonal++;               
               if ( y == (gameBoardSize - 1) && numberOfQueensInDiagonal > 1)
                  numberOfConflicts += (numberOfQueensInDiagonal - 1);                          
            } // End loop.
         } // End if.              
                   
         // Scan the upper-half diagonals.
         else if ( n == 2) { // Only below center diagonal.                                                 
            // Top Left to Bottom Right (\).
            for (diagonalsScanned = 0, y = (gameBoardSize - 1); diagonalsScanned < gameBoardSize-1 ; diagonalsScanned++, y--) { // Only scan lower diagonals.
               rowPosition = 0;
               columnPosition = y; 
               numberOfQueensInDiagonal = 0; // Reset for each diagonal.                             
               for ( maxSpaces = (diagonalsScanned), scannedSpaces = 0; scannedSpaces <= maxSpaces; scannedSpaces++) {                            
                  if (gameBoard[rowPosition][columnPosition] == 1)
                     numberOfQueensInDiagonal++;               
                  if ( scannedSpaces == maxSpaces && numberOfQueensInDiagonal > 1) // Reached end of diagonal.
                     numberOfConflicts += (numberOfQueensInDiagonal - 1); 
                  rowPosition++;
                  columnPosition++;      
               } // End loop.                                                             
            } // End loop.
            
            // Top Right to Bottom Left (/).
            numberOfQueensInDiagonal = 0; // Reset for each diagonal scan                                                  
            for (diagonalsScanned = 0, x = 0; diagonalsScanned < gameBoardSize-1 ; diagonalsScanned++, x++) { // Only scan lower diagonals.
               rowPosition = x;
               columnPosition = 0; 
               numberOfQueensInDiagonal = 0; // Reset for each diagonal.                             
               for ( maxSpaces = (diagonalsScanned), scannedSpaces = 0; scannedSpaces <= maxSpaces; scannedSpaces++) {                            
                  if (gameBoard[rowPosition][columnPosition] == 1)
                     numberOfQueensInDiagonal++;               
                  if ( scannedSpaces == maxSpaces && numberOfQueensInDiagonal > 1) // Reached end of diagonal.
                     numberOfConflicts += (numberOfQueensInDiagonal - 1); 
                  rowPosition--;
                  columnPosition++;      
               } // End loop.                                                             
            } // End loop.             
         } // End if.                                                                                                
      } // End for loop.                                                                                                                                                                                  
      
      return numberOfConflicts; // Total heuristics.
   } // End method.
   
   /**
   Method - getAllHeuristics.
   Description - Makes each queen (1 at a time) move to all possible up and down (vertical) spaces in its column.
                 For each movement, it records the current heuristic in a 2-D array.
                 The position of the heuristic in the array represents the position of the queen of that column when it was recorded.
   @param gameBoard - The 2-D array that represents the game board.
   @param gameBoardSize - The n size (from nxn) of the game board.
   @return heuristicsArray - The array that holds the heuristics for the every possible game board state.
   */
   public static void getAllHeuristics(int[][] gameBoard, int gameBoardSize, int[][] heuristicsArray) {             
      // Variables + Constants.
      int numberOfConflicts;   
                                             
      int numberOfBetterStates = 0;
      int numberOfEqualStates = 0;
      int numberOfWorseStates = 0;
      
      
      int row = 0;
      int column = 0;
      int x = 0; // Row.
      int y = 0; // Column 
      int originalX = 0;
      int originalY = 0;
      
      for (y = 0; y < gameBoardSize; y++) { // Go through every column.                           
         for (x = 0; x < gameBoardSize; x++) { // Go through each space in a column.
            for (x = 0; x < gameBoardSize; x++) { // Force 0 column.
               if (gameBoard[x][y] == 1) { // Record original game board.
                  originalX = x;
                  originalY = y;
                  gameBoard[x][y] = 0; // 0 column.                                            
               }                
               else {
                  gameBoard[x][y] = 0; // 0 column.
               }
            } // End loop.                                            
         
            for (x = 0; x < gameBoardSize; x++) { // Move 1 queen vertically in same column and get heuristics.
               gameBoard[x][y] = 1; // Move queen.
               heuristicsArray[x][y] = getCurrentHeuristics(gameBoard, gameBoardSize); // Get current heuristics for modified game board.
               gameBoard[x][y] = 0; // Remove queen.                                                            
            } // End loop.              
         
            // Return queen to original position once the heuristics are recorded.  
            gameBoard[originalX][originalY] = 1; // Return queen to original position.                                
         } // End loop.                                                                         
      } // End loop.                                                
   } // End method.   

   /** 
   Method - setBetterState.
   Description - Checks the 2-D heuristics array for the best game board state available.
                 It then changes the current state to match the state with the best heuristic.   
   @param gameBoard - The 2-D array that represents the game board.
   @param gameBoardSize - The n size (from nxn) of the game board.
   @param array1 - Info for displaying correct output. From main method.
   @return heuristicsArray - The array that holds the heuristics for the every possible game board state.   
   */
   public static int[] setBetterState (int[][] gameBoard, int gameBoardSize, int[][] heuristicsArray, int[] array1) {
      // Variables + Constants.
      int currentHeuristic = 0;
      int lowestHeuristic = 0;      
      int originalX = 0;
      int originalY = 0;
      int x,y; // Row, Column.
      int outputState = array1[3];
      int neigborsBetterState = array1[0];
      int restarts = array1[2];
      int stateChanges = array1[1];                  
      
      // Get currenct state heuristic.
      currentHeuristic = getCurrentHeuristics(gameBoard, gameBoardSize);     
      lowestHeuristic = getCurrentHeuristics(gameBoard, gameBoardSize); // Placeholder.

      // Find the state with the lowest heuristic.
      for ( int row = 0; row < gameBoardSize; row++ ) {
         for ( int column = 0; column < gameBoardSize; column++ ) {
            if ( lowestHeuristic > heuristicsArray[row][column] )   
               lowestHeuristic = heuristicsArray[row][column];
            if ( currentHeuristic > heuristicsArray[row][column] )
               neigborsBetterState++;                                                                      
         } 
      }// End loop. 
      
      if (lowestHeuristic == 0) {                        
         for (y = 0; y < gameBoardSize; y++) { // Go through every column.                           
            for (x = 0; x < gameBoardSize; x++) { // Go through every row.
               if ( heuristicsArray[x][y] == 0 ) {
                  originalX = x;
                  originalY = y;                                 
                  for (x = 0; x < gameBoardSize; x++) { // Force 0 column.
                     gameBoard[x][y] = 0;           
                  }               
                  // Set-up previously found solution heuristic.                
                  gameBoard[originalX][originalY] = 1;
                  x = gameBoardSize; // Force exit.
                  y = gameBoardSize; // Force exit.
               } // End loop,                                                            
            } // End loop.
         } // End loop.                                                                         
            
         // Signals solution state.
         outputState = 2;
      } // End if.
      
      else if (lowestHeuristic < currentHeuristic) {
       for (y = 0; y < gameBoardSize; y++) { // Go through every column.                           
         for (x = 0; x < gameBoardSize; x++) { // Go through every row.
            if ( heuristicsArray[x][y] == lowestHeuristic ) {
               originalX = x;
               originalY = y;
               for (x = 0; x < gameBoardSize; x++) { // Force 0 column.
                  gameBoard[x][y] = 0;          
               }           
               // Set-up previously found solution heuristic.                
               gameBoard[originalX][originalY] = 1;
               x = gameBoardSize;
               y = gameBoardSize;
            } // End loop,                                                            
         } // End loop.             
         // Signals change state.
         outputState = 1;       
      } // End loop.
      stateChanges++;
   } // End loop.                                                                         

      else if (lowestHeuristic >= currentHeuristic) {         
         // Restart,        
         setInitialState(gameBoardSize, gameBoard); // Restart.
         restarts++;
         // Signals restart state.
         outputState = 0;
      }
         
      // Store values in array.
      array1[0] = neigborsBetterState;
      array1[1] = stateChanges; 
      array1[2] = restarts; 
      array1[3] = outputState;     
   
      return array1;            
   } // End method.   
                 
   /**
   Method - checkGoalState.
   Description - Checks the goal state of the board.
                 If there is at least one queen conflicts or none.
   @param gameBoard - The 2-D array that represents the game board.
   @return The status (1-on / 0-off) of the board. On is solution found.
   */
   public static int checkGoalState(int heuristics) {   
      int status;
      if (heuristics == 0) 
         status = 1;
      else
         status = 0;          
      
      return status;
   }
   
   /**
   Method - displayOutputSolution.
   Description - Displays solution output.
   @param gameBoard - The 2-D array that represents the game board.
   @param gameBoardSize - The n size (from nxn) of the game board.
   @param info - Info for displaying correct output. From main method.   
   */
   public static void displayOutput(int gameBoardSize, int[][] gameBoard, int[] info) {   
      if (info[3] == 0) { // Restart.
         System.out.print("Current h: " + getCurrentHeuristics(gameBoard, gameBoardSize) + "\n");
         System.out.print("Current State" + "\n");
         displayCurrentState(gameBoardSize, gameBoard);
         System.out.print("Neighbors found with lower h: " + info[0] + "\n");
         System.out.print("RESTART");
         System.out.print("\n\n");
      }
      else if (info[3] == 1) { // Set new state.
         System.out.print("Current h: " + getCurrentHeuristics(gameBoard, gameBoardSize) + "\n");
         System.out.print("Current State" + "\n");
         displayCurrentState(gameBoardSize, gameBoard);
         System.out.print("Neighbors found with lower h: " + info[0] + "\n");
         System.out.print("Setting new current state");
         System.out.print("\n\n");
      }
      else if (info[3] == 2) { // Solution.
         System.out.print("Current State" + "\n");
         displayCurrentState(gameBoardSize, gameBoard);
         System.out.print("Solution Found!\n");
         System.out.print("State changes: " + info[1] + "\n");      
         System.out.print("Restarts: " + info[2]);  
         System.out.print("\n\n");
      }                          
   } // End method.
} // End public class.