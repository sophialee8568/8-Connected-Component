import java.io.PrintWriter;

public class boundingBox {	
	int minrow;
	int mincol;
	int maxrow;
	int maxcol;
	
	boundingBox(){
		minrow = 0;
		mincol = 0;
		maxrow = 0;
		maxcol = 0;
		
	}
	
	boundingBox(int count){
		minrow = count;
		mincol = count;
		maxrow = 0;
		maxcol = 0;
	}
	
	boundingBox(int minr, int minc, int maxr, int maxc){
		minrow = minr;
		mincol = minc;
		maxrow = maxr;
		maxcol = maxc;
	}
	
	public void printBoundingBox(PrintWriter writer){
		writer.println("min " + minrow +"," + mincol + " max " + maxrow + "," + maxcol);
	
	}
}

