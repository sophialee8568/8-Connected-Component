import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class EightConnectedComponent {
	
	public static int[][] zeroFramed(int array[][], int rows, int cols){
		for (int i = 0; i < rows+2; i++){
			for (int j = 0; j < cols+2 ; j++){
				array[i][j] = 0;
			}
		}
		return array;
	}
	
	public static int[][] loadImage(int array[][], int rows, int cols, Scanner inFile){
		for (int i = 1; i < rows+1; i++){
			for (int j = 1; j < cols+1; j++){
				array[i][j] = inFile.nextInt();
			}
		}
		return array;
	}
	
	public static int ConnectCC_Pass1(int zeroFramedAry[][], int numRows, int numCols, int EQAry[], int newLabel){
		for(int i = 1; i < numRows+1; i++){
			for ( int j = 1; j < numCols+1 ; j++){
				int pij = zeroFramedAry[i][j];
				if ( pij > 0){
					
					int a = zeroFramedAry[i-1][j-1];
					int b = zeroFramedAry[i-1][j];
					int c = zeroFramedAry[i-1][j+1];
					int d = zeroFramedAry[i][j-1];
					
					if( a == 0 && b == 0 && c == 0 && d == 0){
						newLabel = newLabel + 1;
						zeroFramedAry[i][j] = newLabel;	
					}
					
					else if ( (a != 0 || b != 0 || c != 0 || d != 0) && ( ( a != 0 && a == b ) || ( a != 0 && a == c ) || 
							(a != 0 && a == d ) || (b != 0 && b == c) || (b != 0 && b == d) || (c != 0 && c == d))){
						int temp = -1;
						if (a != 0) temp = a;
						else if (b != 0) temp = b;
						else if ( c != 0)temp = c;
						else if ( d!=0) temp = d;
						zeroFramedAry[i][j] = temp;
					}
					
					else if (a != 0 || b != 0 || c != 0 || d != 0){
						int min = newLabel;
						int max = a;
						if ( a!= 0 && a < min ) min = a;
						if ( b > max) max = b;
						if ( b!= 0 && b < min ) min = b;
						if ( c > max) max = c;
						if ( c!=0 && c < min ) min = c;
						if ( d > max) max = d;
						if ( d!=0 && d < min ) min = d;
						zeroFramedAry[i][j] = min;
						EQAry[max] = min;
						
					}
					
				}
			}
		}
		
		return newLabel;
	}
	
	public static void ConnectCC_Pass2(int zeroFramedAry[][], int numRows, int numCols, int EQAry[]){
		for ( int i = numRows; i > 1; i--){
			for ( int j = numCols ; j > 1; j--){
				int e = zeroFramedAry[i][j+1];
				int f = zeroFramedAry[i+1][j-1];
				int g = zeroFramedAry[i+1][j];
				int h = zeroFramedAry[i+1][j+1];
				int pij = zeroFramedAry[i][j];
				
				if (pij > 0){
					
					if ( e == 0 && f == 0 & g == 0 && h == 0){
						//do nothing
					}
					else if ( (e == pij || e == 0) && (f == pij || f == 0) && (g == pij || g == 0) && (f == pij || f == 0) ){
						//do nothing
					}
					else if ( (e != pij && e!=0) || (f != pij && f!=0) || (g != pij && g!= 0)|| (h != pij && h!=0) ){
						int min = pij;
						int max = pij;
						if ( e!=0 && e < min) min = e;
						if ( e > max) max = e;
						if ( f!=0 && f < min) min = f;
						if ( f > max) max = f;
						if ( g!=0 && g < min) min = g;
						if ( g > max) max = g;
						if ( h!= 0 && h < min) min = h;
						zeroFramedAry[i][j] = min;
						EQAry[max] = min;
					}
				}
				
			}
		}
	}
	
	public static void ConnectCC_Pass3(int zeroFramedAry[][], int numRows, int numCols, int EQAry[], int count, PrintWriter writer){
		int pixelCount[] = new int[count+1];
		boundingBox boxes[] = new boundingBox[count+1];
		for ( int i = 0; i < count+1; i++){
			pixelCount[i] = 0;
			boxes[i] = new boundingBox((numRows*numCols)/4);
		}
		
		for ( int i = 1; i < numRows+1; i++){
			for ( int j = 1; j < numCols + 1; j++){
				int pij = zeroFramedAry[i][j];
				if (pij > 0){
					if ( pij != EQAry[pij]){
						zeroFramedAry[i][j] = EQAry[pij];
					}
					if(boxes[zeroFramedAry[i][j]].minrow > i) boxes[zeroFramedAry[i][j]].minrow = i-1;
					if(boxes[zeroFramedAry[i][j]].mincol > j) boxes[zeroFramedAry[i][j]].mincol = j-1;
					if(boxes[zeroFramedAry[i][j]].maxrow < i) boxes[zeroFramedAry[i][j]].maxrow = i-1;
					if(boxes[zeroFramedAry[i][j]].maxcol < j) boxes[zeroFramedAry[i][j]].maxcol = j-1;
				}
				
				pixelCount[zeroFramedAry[i][j]]++;
			}	
			
		}
		
		for ( int i = 1 ; i < count+1; i++){
			writer.println("for component: " + i );
			writer.println("pixel #: " + pixelCount[i]); 
			boxes[i].printBoundingBox(writer);	
			writer.println();
		}
		
	}
	
	public static int manageEQAry(int EQAry[], int newLabel){
		int count = 0;
		for ( int i = 1; i < newLabel+1; i++){
			if ( EQAry[i] == i){
				count++;
				EQAry[i] = count;
			}
			else {
				EQAry[i] = EQAry[EQAry[i]];
			}
		}
		return count;
		
	}
	
	public static void prettyPrintArray(int zeroFramedAry[][], int numRows, int numCols, PrintWriter writer ){
		for ( int i = 1 ; i < numRows +1; i++){
			for ( int j = 1; j < numCols + 1; j++){
				if ( zeroFramedAry[i][j] > 0){
					if (zeroFramedAry[i][j] < 10){
						writer.print(zeroFramedAry[i][j] + "  ");
					}
					else {
						writer.print(zeroFramedAry[i][j] + " ");
					}
				}
				else {
					writer.print("  ");
				}
			}
			writer.println();
		}
	}
	
	public static void main(String[] args) {
		
		Scanner inFile;
		PrintWriter writer;
		PrintWriter writer2;
		PrintWriter writer3;
		int numRows = 0;
		int numCols = 0;

		int zeroFramedAry[][];
		int EQAry[];
		int newLabel = 0;
		
		
		try {
			int minVal = 0;
			int maxVal = 0;
			int newMin = 0;
			int newMax = 0;
			inFile = new Scanner(new FileReader(args[0]));
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			zeroFramedAry = new int[numRows+2][numCols+2];
			zeroFramed(zeroFramedAry, numRows, numCols);
			EQAry = new int[(numRows * numCols) / 2];
			for ( int i = 0; i < (numRows*numCols)/2 ; i++){
				EQAry[i] = i;
			}
			while (inFile.hasNextInt()){
				loadImage(zeroFramedAry, numRows, numCols, inFile);
			}
			writer = new PrintWriter(args[1]);
			
			newLabel = ConnectCC_Pass1(zeroFramedAry, numRows, numCols, EQAry,newLabel);
			writer.println("PASS 1");
			prettyPrintArray(zeroFramedAry, numRows, numCols, writer);
			writer.println();
			writer.println("EQARY of pass 1");
			writer.println("Index of Array / Value");
			for ( int i = 1; i < newLabel + 1; i++){
				writer.println(i + " " + EQAry[i]);
			}
		

			ConnectCC_Pass2(zeroFramedAry, numRows, numCols, EQAry);
			
			writer.println();
			writer.println("PASS 2");
			prettyPrintArray(zeroFramedAry, numRows, numCols, writer);
			writer.println();
			writer.println("EQAry of pass 2");
			writer.println("Index of Array / Value");
			for ( int i = 1; i < newLabel + 1; i++){
				writer.println(i + " " + EQAry[i]);
			}
			
			int count = manageEQAry(EQAry, newLabel);
			writer.println();
			writer.println("EQARY AFTER MANAGE EQARY");
			writer.println("Index of Array / Value");
			for ( int i = 1; i < newLabel + 1; i++){
				writer.println(i + " " + EQAry[i]);
			}
			
			writer3 = new PrintWriter(args[3]);
			
			ConnectCC_Pass3(zeroFramedAry, numRows, numCols, EQAry, count, writer3);
			newMax = count;
			writer.println();
			writer.println("PASS 3");
			prettyPrintArray(zeroFramedAry, numRows, numCols, writer);
			writer.println();
			writer.println("EQARY of pass 3");
			writer.println("Index of Array / Value");
			for ( int i = 1; i < newLabel + 1; i++){
				writer.println(i + " " + EQAry[i]);
			}
			
			writer.close();
			writer2 = new PrintWriter(args[2]);
			writer2.println(numRows + " " + numCols + " " + newMin + " " + newMax);
			for ( int i = 1 ; i < numRows + 1; i ++){
				for (int j = 1; j < numCols + 1; j++ ){
					if (zeroFramedAry[i][j] < 10){
						writer2.print(zeroFramedAry[i][j] + "  ");
					}
					else {
						writer2.print(zeroFramedAry[i][j] + " ");
					}
				}
				writer2.println();
			}
			
			inFile.close();
			writer2.close();
			writer3.close();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
