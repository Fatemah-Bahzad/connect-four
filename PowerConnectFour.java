/** implementation of the Power Connect Four game by.
 * updating the board, count the connecting tokens 
 * and checks moves 
 * @author Fatemah Bahzad
 */

public class PowerConnectFour {
	
	/**
	 *  The grid to contain tokens. Cells can be empty.
	 */
	//underlying array of columns for storage 
	private Column<Token>[] grid;

	/**
	 *  The fixed number of columns the game grid should have.
	 */
	private static final int NUM_COLS = 7;

	/**
	 *  The minimum number of rows of the grid _for display_.
	 */
	private static final int MIN_ROWS = 6;

	/**
	 * The two players of the game.
	 * playerOne is always the first to make a move when the game starts.
	 */
	private static final Token playerOne = Token.RED;
	/**
	 * playerTwo is the second player to go .
	 */
	private static final Token playerTwo = Token.YELLOW;

	/**
	 * The character used to represent empty cells when the grid is displayed.
	 */
	private static final Character empty = Character.valueOf('-');
	
	/**
	 * When grid is displayed, the top row of the grid should always be empty.
	 */  
	private static final int MARGIN_ROWS = 1;
	/**
	 * keep trach of players turn. 
	 */
	private int pturn=1; 
	/**
	 * keep trach of display rows.
	 */
	private int displayrow=MIN_ROWS;
	/**
	 * check of rows are completely empty. 
	 */
	private int emptyToken;
	/**
	 * count the connecting tokens. 
	 */
	private int count;
	
	/**
	*create a grid with NUM_COLS columns.
	*each column should have DEFAULT_CAPACITY defined in the column class
	*and a size 0
	*/
	@SuppressWarnings("unchecked")	
	public PowerConnectFour() {
		this.grid = (Column<Token>[]) new Column[NUM_COLS];
		// Constructor with no arguments.
		for (int i=0; i<NUM_COLS;i++) {
			this.grid[i]=new Column<Token>(); //each col in grid gets DEFAULT_CAPACITY and size=0
		}
		
	}

	/**
	*get the number of columns.
	*@return NUM_COLS 
	*/
	public int sizeCol() { 
		return NUM_COLS; //default return, make sure to remove/change
	}
	
	/**
	*get the numbers of rows currently in display. 
	*@return displayrow
	*/
	public int sizeRow() { 
		return displayrow; 
	}
	
	/**
	*return the empty cell symbol for display.
	*@return empty
	*/
	public Character getEmptySymbol(){
		return empty; 
	}
	
	/**
	*get the token at the given column and row. 
	*@param col is the column the token is in 
	*@param row is the row the token is in 
	*@return token at col,row
	*/
	public Token get(int col, int row){
		//if col is less then zero OR more then the set number of columns OR row is more then the rows in display 
		//throw an Exception
		if (col<0|| col>=NUM_COLS||row>displayrow) { 
			throw new IndexOutOfBoundsException("Col " + col + ", Row "+ row + " out of bounds!");
		}
		
		if (row>=grid[col].size()) {//if row is bigger then the number of values in col
			return null;
		}
		return grid[col].get(row); //return the token 
	}

	/**
	*get the column at the given index.
	*@param col is the index the column is in 
	*@return column at the given index
	*/
	public Column<Token> getColumn(int col){
		//if col is more then the set numbers of columns OR less then zero throw Exception
		if (col>=NUM_COLS || col<0) {
			throw new IndexOutOfBoundsException( "Col " + col + " out of bounds!");
		}
		return grid[col];
		
	}
	
	/**
	*get the Token of the current player. 
	*@return the token of the current player
	*/

	public Token currentPlayer(){ //red goes on odd yellow goes on even 
		if (pturn%2!=0) { //red turn when pturn is odd
			return Token.RED;
		}
		return Token.YELLOW; // yellow turn when pturn is even 
	}

	/**
	*update the grid by adding a token of the current player to the end of the given column. 
	*@param col is the index of the column where the token is added to 
	*@return true if it is a valid  move, and false if it is not
	*/

	public boolean drop(int col){
		if (col>=NUM_COLS || col<0) {//if the col is out of bound  return false
			return false;
		}
		grid[col].add(currentPlayer());//add the token of the current player
		if (grid[col].size()==displayrow) {
			displayrow++; //add one to the display rows if needed
		}
		pturn++; //move the turn to the next player if it is a valid  move
		return true; 
	}
	

	/**
	*update the grid by adding a token of the current player to the given column and row. 
	*@param col is the index of the column where the token is added to 
	*@param row is the index of the row of the column where the token is added
	*@return true if it is a valid  move, and false if it is not
	*/
	public boolean powerDrop(int col, int row){
		if (col>=NUM_COLS || col<0 || row>grid[col].size()) {
			return false; //if the col in more then 7 or less then 0
			//can not insert a "floating" return false
		}
		grid[col].add(row,currentPlayer());//add the token of the current player
		if (grid[col].size()==displayrow) {
			displayrow++;//add one to the display rows if needed
		}
		pturn++;//move the turn to the next player if it is a valid  move
		return true; 
	}
	
	/**
	*update the grid by removing the current player token from the bottom of the column.
	*@param col is the index of the column where the token is deleted from  
	*@return true if it is a valid move, and false if it is not
	*/
	public boolean pop(int col){
		emptyToken=0; //reset to count the empty tokens in the rowa
		if(grid[col].get(0)!=currentPlayer()||col>NUM_COLS||col<0) {
			return false;
			//if it is not the current player token OR col is more them the set number of columns
			//OR col is less then 0 return false
		}
		grid[col].delete(0); //remove the token 
		for (int i=0;i<NUM_COLS ;i++) {
			if (grid[i].size()==0) { //if there is no tokens in the column
				emptyToken++;//count the empty tokens
				continue;
			}
			if (grid[i].size()<displayrow-1) {//if there is more then 1 empty row
				emptyToken++;//how many empty col in the row under the margin row 
			}
		}
		//all 7 cols are empty remove one row to leave only one empty on the top
		if (emptyToken==NUM_COLS && displayrow>=MIN_ROWS+1) { 
			displayrow-=1; 
			emptyToken=0;//reset 
		}

		pturn++;// move to the next player turn if it was a valid 

		return true; 

	}

	/**
	*update the grid by removing the current player token from the given column and row.
	*@param col is the index of the column where the token is deleted from 
	*@param row is the index of the row of the column where the token is deleted
	*@return true if it is a valid move, and false if it is not
	*/
	public boolean powerPop(int col, int row){
		emptyToken=0;//reset to zero
		//if it is not the current player token OR col is more them the set number of columns
		//OR col is less then 0 return false
		if(get(col,row)!=currentPlayer()||col>NUM_COLS||col<0) {
			return false;
		}
		grid[col].delete(row); //delete the token 
		for (int i=0;i<NUM_COLS ;i++) {
			if (grid[i].size()==0) { //if there is no tokens in the column
				emptyToken++;//count the empty tokens
				continue;
			}
			if (grid[i].size()<displayrow-1) {//if there is more then 1 empty row
				emptyToken++;//how many empty col in the row before the margin row
			}
		}
		//all 7 cols are empty remove one row to leave only one empty on the top
		if (emptyToken==NUM_COLS && displayrow>=MIN_ROWS+1) { 
			displayrow-=1;  
			emptyToken=0;//reset
		}

		pturn++; // move to the next player turn if it was a valid 
		return true; 
	}
	
	/**
	*count the number of consecutive tokens for the given player in a row, one of them. 
	*has to be in column, row
	*@param col is the index of the column 
	*@param row is the index of the row 
	*@param player the token to count
	*@return count of the consecutive tokens in a row
	*/
	public int countRow(int col, int row, Token player){ //do it in one loop
		count=0;//reset count 
		//if the token at that location is not the wanted player token return 0
		if (row>displayrow ||row>grid[col].size()||col>NUM_COLS||col<0) {
			return 0;
		}
		for (int i=0; i<NUM_COLS;i++) {
			//if row if more then the size of the column continue
			if (row>=grid[i].size()) {
				continue;
			}
			//if it is not the player token and it is before the starting point
			if (grid[i].get(row)!=player && i<col) {
				count=0;//reset the count
			}
			//if it is not the player token and it is after the starting point
			if (grid[i].get(row)!=player && i>=col) {
				break; //stop the count
			}
			if (grid[i].get(row)==player) {
				count++; //if it is the player token count it 
			}
		}

		return count; 
	}
		
	/**
	*count the number of consecutive tokens for the given player in a column, one of them.
	*has to be in column, row
	*@param col is the index of the column 
	*@param row is the index of the row 
	*@param player the token to count
	*@return count of the consecutive tokens in a column
	*/
	public int countCol(int col, int row, Token player){
		count=0;//reset the count
		//if the token at that location is not the wanted player token return 0
		if (row>displayrow ||row>grid[col].size()||col>NUM_COLS||col<0) { 
			return 0;
		}
		for (int i=0; i<grid[col].size();i++) {
			//if it is not the player token and it is after the starting point
			if (grid[col].get(i)!=player && i>=row) {
				break; //break the loop
			}
			//if it is not the player token and it is before the starting point
			if (grid[col].get(i)!=player && i<row) {
				count=0; //reset the count
			}
			if (grid[col].get(i)==player) {
				count++;//if it is the player token count it

			}
		}
		return count; 
	}

	/**
	*count the number of consecutive tokens for the given player in a major diagonal.
	*such that one of those tokens is at the given row and col
	*@param col is the index of the column 
	*@param row is the index of the row 
	*@param player the token to count
	*@return count of the consecutive tokens in a major diagonal
	*/
	public int countMajorDiagonal(int col, int row, Token player){
		count=0;
		if (row>displayrow ||row>grid[col].size()||col>NUM_COLS||col<0) {//if the token at that location is not the wanted player token 
			return 0;
		}
		for(int i=col,j=row;i<NUM_COLS && j>=0;i++,j--) {//going down to the right
			if (row>=grid[i].size()) {//if row is bigger then the size of the column
				continue;
			}
			if (grid[i].get(j)==player) {//if it is the players token
				count++;//count it
			}
			else {
				break;
			}
		}
		for(int i=col-1,j=row+1;i>0 && j<grid[i].size();i--,j++) {//going up to the left
			if (row>=grid[i].size()) { //if row is bigger then the size of the column
				continue;
			}
			if (grid[i].get(j)==player) { //if it is the players token 
				count++;//count it
			}
			else {
				break;
			}
		}
		return count;
	}
	/**
	*count the number of consecutive tokens for the given player in a minor diagonal.
	*such that one of those tokens is at the given row and col
	*@param col is the index of the column 
	*@param row is the index of the row 
	*@param player the token to count
	*@return count of the consecutive tokens in a minor diagonal
	*/
	public int countMinorDiagonal(int col, int row, Token player){
		count=0;
		if (row>displayrow ||row>grid[col].size()|| col>NUM_COLS||col<0) {//if the token at that location is not the wanted player token 
			return 0;
		}
		for(int i=col,j=row;i<NUM_COLS && j<grid[i].size();i++,j++) {//going up to the right
			if (row>=grid[i].size()) { //if row is bigger then the size of the column
				continue;
			}
			if (grid[i].get(j)==player) {//if it is the players token
				count++;//count it
			}
			else {
				break;
			}
		}
		for(int i=col,j=row;i>0 && j>0;i--,j--) {//going down to the left
			if (row>=grid[i].size()) {//if row is bigger then the size of the column
				continue;
			}
			if (grid[i].get(j)==player) {//if it is the players token
				count++;//count it
			}
			else {
				break;
			}
		}
		
		return count; 
	}

	


	//******************************************************
	//*******  DO NOT EDIT ANYTHING IN THIS SECTION  *******
	//*******        But do read this section!       *******
	//******************************************************
	
	/**
	 * The method that checks whether the specified player has four connected tokens
	 * horizontally, vertically, or diagonally.  It relies on the methods of countRow(),
	 * countCol(), countMajorDiagonal(), and countMinorDiagonal() to work correctly.
	 *
	 * @param player the token to be checked
	 * @return whether the given player has four tokens connected
	 */
	public boolean hasFourConnected(Token player){
		// Check whether the specified player has four tokens either in a row,
		// in a column, or in a diagonal line (major or minor). Return true if 
		// so; return false otherwise.	
		
		for (int j = 0; j<sizeCol(); j++){
			for (int i = 0; i<sizeRow(); i++){
				if (countRow(j, i, player)>=4 || countCol(j, i, player)>=4
					|| countMajorDiagonal(j, i, player)>=4 
					|| countMinorDiagonal(j, i, player)>=4 )
					return true;
			}
		}
		return false;
		
	}

	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//*******		Remember to add JavaDoc			 *******
	//******************************************************
	/**
	*test cases.
	*@param args to call the class
	*/
	public static void main(String[] args) {
		// init with an empty grid
		PowerConnectFour myGame = new PowerConnectFour();
		if (myGame.sizeCol() == NUM_COLS && myGame.sizeRow() == MIN_ROWS
			&& myGame.getColumn(2).size() == 0 && myGame.currentPlayer() == Token.RED
			&& myGame.get(0,0) == null){
			System.out.println("Yay 1!");		
		}
	
		// drop
		if (!myGame.drop(10) && myGame.drop(2) && myGame.getColumn(2).size() == 1 && 
			myGame.get(2,0) == Token.RED && myGame.currentPlayer() == Token.YELLOW ){
			System.out.println("Yay 2!");					
		}
		
		// drop, pop, column growing/shrinking, board display changed
		boolean ok = true;
		for (int i=0; i<5; i++){
			ok = ok && myGame.drop(2); 	//take turns to drop to column 2 for five times
		}
		//System.out.println("===Current Grid===");		
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		if (ok && myGame.getColumn(2).size() == 6 && myGame.sizeRow() == 7
			&& myGame.pop(2) && myGame.sizeRow() == 6 && myGame.get(2,1) == Token.RED){
			System.out.println("Yay 3!");							
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		
		// power drop
		if (!myGame.powerDrop(3,1) && myGame.powerDrop(3,0) && myGame.powerDrop(2,2)
			&& myGame.getColumn(2).size() == 6 && myGame.get(2,2) == Token.RED
			&& myGame.get(2,3) == Token.YELLOW && myGame.getColumn(3).size() == 1){
			System.out.println("Yay 4!");							
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		
		//power pop
		if (!myGame.powerPop(2,1) && myGame.powerPop(2,3) 
			&& myGame.getColumn(2).size() == 5 && myGame.get(2,3).getSymbol()=='R'){
			System.out.println("Yay 5!");									
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		//PowerConnectFourGUI.reportcurrentPlayer(myGame);
		// expected display:
		//|   || 0 || 1 || 2 || 3 || 4 || 5 || 6 |
		//| 5 || - || - || - || - || - || - || - |
		//| 4 || - || - || Y || - || - || - || - |
		//| 3 || - || - || R || - || - || - || - |
		//| 2 || - || - || R || - || - || - || - |
		//| 1 || - || - || R || - || - || - || - |
		//| 0 || - || - || Y || Y || - || - || - |
		//Player R's turn

		//counting
		if (myGame.countRow(3,0,Token.YELLOW) == 2 && myGame.countRow(3,0,Token.RED) == 0
			&& myGame.countCol(2,3,Token.RED) == 3 && myGame.drop(3) /*one more R*/
			&& myGame.countMajorDiagonal(3,1,Token.RED) == 2 /* (3,1) and (2,2) */
			&& myGame.countMinorDiagonal(2,0,Token.YELLOW) == 1){
			System.out.println("Yay 6!");												
		}
			
	
	}
}