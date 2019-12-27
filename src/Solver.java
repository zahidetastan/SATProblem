import java.io.*;
import java.util.Scanner;

public class Solver {

	public static Boolean[][] createTruthTable(int numberOfVar){
		int row=(int) Math.pow(2, numberOfVar);
		
		Boolean truthTable[][]= new Boolean[row][numberOfVar];
		int m=row/2;
		for(int i=0; i<numberOfVar; i++){
			
			Boolean value = true;
			for(int j=0; j<row; j++){
				if(j%m==0)
				{
					value = !value;
				}
				truthTable[j][i] = value;
				
			}

			m=m/2;
		}
		return truthTable;
	}
	public static Boolean checkSat(Boolean table[][],int numberOfClauses,int numberOfVar){

		for(int i=0;i<numberOfClauses;i++){
			Boolean isAllLineFalse = true;
			for(int j=0;j<numberOfVar;j++){
				if(table[i][j]==null) continue;
				if(table[i][j]) isAllLineFalse = false;
			}
			if(isAllLineFalse) return false;	 
		}
		return true;
	}



	public static Boolean[][] tryTable(Boolean eqTable[][], int numberOfClauses, int numberOfVar,Boolean[] pattern){
		 
	
		int row=(int) Math.pow(2, numberOfVar);
		Boolean newTable[][] = new Boolean[numberOfClauses][numberOfVar];

		for(int i=0;i<numberOfClauses;i++){
			for(int j=0;j<numberOfVar;j++){
				if(eqTable[i][j]==null) continue;

				newTable[i][j] = !(eqTable[i][j]^pattern[j]);
			}
		}

		return newTable;
	} 

	public static void printOutput(Boolean[] truthTable) throws IOException {
		PrintWriter writer = new PrintWriter("src/output.cnf");
		String text = "";
		writer.println("SAT");
		for(int i=0;i<truthTable.length;i++){
			if(truthTable[i]) text+= (i+1)+" ";
			else text+="-"+(i+1)+" ";
		}
		writer.println(text);
		writer.close();
	}

	public static void printOutput() throws FileNotFoundException {
			PrintWriter writer = new PrintWriter("src/output.cnf");
			writer.println("UNSAT");
			writer.close();
	}


	public static void main(String[] args) throws IOException {
		
		File f= new File("src/input.cnf");
		String headerLine;
		String[] headerParams = null;
		int numberOfVar, numberOfClauses;
		Boolean eqTable[][]=null;

	
		
		//System.out.println(f.getPath());	
		if (!f.exists()) {
			System.out.println("Input file couldn't be found!");
		    return;
		}
		
	    Scanner s = new Scanner(f);
	    
	    if(s.hasNextLine()) {
	    	headerLine =s.nextLine();
	    	headerParams =headerLine.split(" ");
  	    }
	    
	
	    numberOfVar=Integer.parseInt(headerParams[2]);
		numberOfClauses=Integer.parseInt(headerParams[3]);

		eqTable= new Boolean[numberOfClauses][numberOfVar];



		for(int i=0; i<numberOfClauses; i++){
			String line =s.nextLine();
			String[] lineParams =line.split(" ");
			int j = 0;
			while(!lineParams[j].equals("0")){
				
				int a= Integer.parseInt(lineParams[j]);

				if(a<0){
					eqTable[i][Math.abs(a)-1] =false;
				}
				else eqTable[i][Math.abs(a)-1] =true;

				j++;
			}
			
		}
		

/*		for(int k=0; k<numberOfClauses; k++){
			for(int l=0; l<numberOfVar; l++)
			{
				System.out.print(eqTable[k][l]+" ");
			}
			System.out.println();
		}


		Boolean pattern [] = new Boolean[3];
		pattern[0]=true;
		pattern[1]=true;
		pattern[2]=true;
		Boolean[][] newTABLE=tryTable(eqTable, numberOfClauses, numberOfVar, pattern);

		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){

				System.out.print(newTABLE[i][j]+" ");

			}
			System.out.println();
		}
	*/		

	    Boolean truthTable[][] = createTruthTable(numberOfVar);
		Boolean resultTable[][];
		final int row = (int) Math.pow(2, numberOfVar);
		for(int i=0;i<row;i++){
			resultTable = tryTable(eqTable, numberOfClauses, numberOfVar, truthTable[i]);
			if(checkSat(resultTable, numberOfClauses, numberOfVar)){
				printOutput(truthTable[i]);
				return;
			}
		}
		printOutput();

		}
		
	  
	}


