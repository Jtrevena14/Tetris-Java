import java.util.Scanner;
import java.util.Random;
public class TetrisV2{
	public static void main(String [] args){
		Scanner keyboard = new Scanner(System.in);
		int[][] board = new int[9][6];
		int y = 0,x=board[0].length/2;
		int num = 1;
		int holder=board.length-1;
		int counter=0,counter2=0,counter3=0,go_down=0,score=0;
		while(true){
			if(counter%2==0){
				num=1;             //1 is up and down, 2 is side to side.
			}
			else{
				num=2;
			}
			String a = keyboard.nextLine(); //Ask user to enter "a", "d" or nothing to move the pieces.
			switch(a){
				case "":
					y++;
					break;
				case "a":
					if(x>0){
						x--;
						if(num==2 && y<board.length-1 && x>=0 && board[y+1][x]==1){      //If the block moves out of bounds move it back in bounds.
							x++;
						}
						else if(num==1 && y<board.length-2 && x>=0 && board[y+2][x]==1){    //if user moves onto a block that already contains a one, stay in same position or else piece will disappear.
							x++;
						}
					}
					y++;
					break;
				case "d":
					if(x<board[0].length-1){
						x++;
						if(num==2 && x>=board[0].length-1 || num==2 && y<board.length-1 && board[y+1][x+1]==1){
							x--;
						}
						else if(num==1 && y<board.length-2 && board[y+2][x]==1){
							x--;
						}
					}
					y++;
					break;
			}
			if(num==1){
				if(y>board.length-2 || (board[y][x]==1 || board[y+1][x]==1)){  //If block reaches the bottom of the board or the block has a one underneith one of its piece, set x and y to the top.
					y=0;
					x=board[0].length/2;
					counter++;
					if(board[y][x]==1){                  //If the starting position contains a one, game ends because it means the blocks have reached all the way to the top.
						System.out.println("You Lose! Your score was: " + score);
						break;
					}
				}

				board[y][x]=1;    //At the top of the board set the initialize the block by putting ones in the place.
				board[y+1][x]=1;
			}
			if(num==2){
				if(y>board.length-1 || (board[y][x]==1 || board[y][x+1]==1)){   //Repeat of the last block of code but for a different variation of the block.
					y=0;
					x=board[0].length/2;
					counter++;
					if(board[y][x]==1){
						System.out.println("You Lose! Your score was: " + score);
						break;
					}
				}
				board[y][x]=1;
				board[y][x+1]=1;
			}

			counter2=0;
			for(int i = 0; i<board.length;i++){
				for(int j = 0;j<board[0].length;j++){
					if(board[i][j]==1){                //If a whole row equals one, meaning it is ready to break like in Tetris, check by adding one to counter2 and check if counter2 == board[0].length.
						counter2++;
					}
				}
				if(counter2==board[0].length){
					if(num==1 && y+1==board.length-1 || num==1 && y<board.length-2 && board[y+2][x]==1){
						for(int j = 0; j<board[0].length;j++){
							board[i][j]=0;                             //If there is a whole row of ones, set the row to zero and add one to counter3. and go_down to one for next block of code.
						}
						counter3++;
						go_down=1;

					}
					else if(num==2 && y==board.length-1 || num==2 && y<board.length-1 && board[y+1][x]==1 || num==2 && y<board.length-1 && x<board[0].length-1 && board[y+1][x+1]==1){  //Same code as last block just for different variation of block.
						for(int j = 0; j<board[0].length;j++){
							board[i][j]=0;
						}
						counter3++;
						go_down=1;
					}
					holder=i;
				}
				counter2=0;
			}

			if(num==1){
				if(y<board.length-1 && x>0 && board[y][x-1]==1 && a.equals("d")){   //If the current block has a one in the unit below and the user enters a d, set the previous position with zeros.
						board[y-1][x-1]=0;
						board[y][x-1]=0;
				}
				else if(y<board.length-1 && x<board[0].length-1 && board[y][x+1]==1 && a.equals("a")){   //if the correct block has a one below and user enters a set previous position with zeros.
						board[y-1][x+1]=0;
						board[y][x+1]=0;
				}
			}
			else if(num==2){
				if(y>0 && x>0 && a.equals("d")){
					board[y-1][x-1]=0;
					board[y-1][x]=0;
				}
				else if(y<board.length && y>0 && x<board[0].length-2 && a.equals("a")){
					board[y-1][x+1]=0;
					board[y-1][x+2]=0;
				}
			}

			if(go_down==1){
				score+=counter3;
				for(int i = holder; i>=0;i--){    //Starting from the bottom up, check every single one on the board, and move it down by counter3 value, if there is only one line to break counter3 will equal 1, meaning all ones go down by 1 unit.
					for(int j = 0; j<board[0].length;j++){
						if(board[i][j]==1 && i<board.length-1){ //Hold the row that the clear is at and go up from there
							board[i][j]=0;
							board[i+counter3][j]=1;       //if 2 rows need to be cleared, means all ones on the board go down by 2 units because counter3 will equal 2.
						}
					}
				}

				counter3=0;
				go_down=0;            //go_down = 0 to end the loop
				y=0;
				x=board[0].length/2;
			}


			for(int i = 0; i<board.length;i++){
				for(int j = 0; j<board[0].length;j++){
					System.out.print(board[i][j] + " " );       //Main display block to display the board to command prompt.
				}
				System.out.println();
			}
			System.out.println(score);
			if(num==1){                            //Determain what block variation it is and put zeros where the previous position used to be.
				board[y][x]=0;
				board[y+1][x]=0;
				if(y>=board.length-2 || (board[y+2][x]==1)){    //if y is at the bottom of the board or the unit below the currect block equals a one, set that position with ones. IE the perminent ones on the board.
					board[y][x]=1;
					board[y+1][x]=1;
				}
			}
			if(num==2){                 //Repeat of the last code in the side to side block variation.
				board[y][x]=0;
				board[y][x+1]=0;
				if(y>=board.length-1 || (board[y+1][x]==1 || board[y+1][x+1]==1)){
					board[y][x]=1;
					board[y][x+1]=1;
				}
			}
		}
	}
}
