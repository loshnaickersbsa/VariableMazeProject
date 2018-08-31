package com.maize;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;
//
public class MazeByDirectionPreference implements Comparable<MazeByDirectionPreference>{

	int startPositionRow;
	int endPositionRow;
	int endPositionColumn;
	int startPositionColumn;
	int mazeRowsFromZero;
	int mazeColumnsFromZero;
	String fileName;
	char[][] mazeStructure;
	int routeLengthSize;
	private int storeLength;
	private String directionPreference; 

	MazeByDirectionPreference(String fileName , String directionPreference)
	{
		this.fileName=fileName;
		this.directionPreference = directionPreference;
		loadTheData();
		findPath(startPositionRow, startPositionColumn);
		routeLengthSize=countStars();
	}

	/*
	 * 
	 * 2)search all paths 
	 * and
	 * 3)print the paths
	 * 
	 */



	private int countStars() 
	{
		int returnCount=0;
		for (int row = 0; row<= mazeRowsFromZero; row++) 
		{
			for (int col= 0; col <= mazeColumnsFromZero; col ++) {
				if ( mazeStructure[row][col] =='*')  
				{	
					returnCount++;
				}
			}

		}
		return returnCount;
	}

	private void printMaze() {
		// TODO Auto-generated method stub

		for (int row = 0; row<= mazeRowsFromZero; row++) {
			for (int col= 0; col <= mazeColumnsFromZero; col ++) {
				System.out.print(mazeStructure[row][col]);
			}
			System.out.println();
		}

	}

	private  boolean findPath(int row  , int col )
	{

		if (row < 0  || row  > this.mazeRowsFromZero)
		{
			return false;
		}

		if (col < 0 || col > this.mazeColumnsFromZero)
		{
			return false;
		}
		//Obstacle W or + or "
		if (! (row==startPositionRow && col==startPositionColumn ) )
		{
			if (checkIfWeCannotProceedToADot(row , col))
			{
				return false;
			}
		}

		if (mazeStructure[row][col] == 'E' ||  mazeStructure[row][col] == 'e')
		{
			return true;
		}

		mazeStructure[row][col] = '*'; // path *

		for (int i=0; i< this.directionPreference.length(); i++) 
		{

			int callCol=col, callRow=row;

			switch(directionPreference.charAt(i))
			{
			case 'N': --callRow; break;
			case 'S': ++callRow; break;
			case 'E': ++callCol; break;
			case 'W': --callCol; break;
			}

			// East 
			if  ( findPath (callRow  , callCol ) == true)
			{
				return true;
			}

		}

		mazeStructure[row][col] = '"'; // unmarked "

		return false;

	}

	private boolean checkIfWeCannotProceedToADot(int row, int col) 
	{
		if (mazeStructure[row][col] == '"' || mazeStructure[row][col] == 'w' || mazeStructure[row][col] == 'W' ||  mazeStructure[row][col] == '*')
			return true;

		return false;
	}


	private void loadTheData() {
		// TODO Auto-generated method stub
		File filein = new File("resources/" +this.fileName);
		BufferedReader bR=null;

		int storeLength=0;
		String matrixLoad; 
		ArrayList<String> matrixList =new ArrayList<String>();
		try 
		{
			bR = new BufferedReader(new FileReader(filein));

			String stringFromFile = null;

			while ( (stringFromFile = bR.readLine()) != null )
			{

				if (stringFromFile.length() == 0)
				{
					throw new IOException("this is an invalid length file"); 
				}

				if ( (storeLength !=0) && (storeLength != stringFromFile.trim().length()) )
				{
					throw new IOException("this is an invalid length file"); 
				}


				matrixLoad=stringFromFile.trim();

				// load takes place in a character array thats

				storeLength = stringFromFile.trim().length(); 

				matrixList.add(stringFromFile.trim());
			}

			this.mazeStructure = new char[matrixList.size()][storeLength];

			this.mazeColumnsFromZero= storeLength-1;
			this.mazeRowsFromZero= matrixList.size() - 1;

			int startList=0;

			/*
			 * in here we create the matrix array, print and
			 * store the start and end locations
			 */
			for (int r = 0; r < matrixList.size(); r++) 
			{
				String sT = matrixList.get(r);

				for (int c = 0; c < sT.length(); c++)
				{
					//System.out.println( "R" +r + " C " + c);
					mazeStructure[r][c] = sT.charAt(c);
					int[][] endPosition;
					//System.out.print(mazeStructure[r][c]);

					// start set based on the file input
					if (Character.toUpperCase(mazeStructure[r][c]) == 'S')
					{
						startPositionRow=r;
						startPositionColumn=c;
					}

					// end set based on the file input
					if (Character.toUpperCase(mazeStructure[r][c]) == 'E')
					{
						endPositionRow=r;
						endPositionColumn=c;
					}


				}
				//System.out.println();
			}

			// if no start and end *** flag it 


		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}   
		finally {
			try {
				bR.close();
			} catch (IOException e) 
			{
				System.out.println("Close error");
				e.printStackTrace();
			}
		}



	}



	@Override
	public int compareTo(MazeByDirectionPreference o) 
	{
		// TODO Auto-generated method stub
		return (this.routeLengthSize < o.routeLengthSize) ? -1: ((this.routeLengthSize == o.routeLengthSize) ? 0 : 1);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<MazeByDirectionPreference> mazeList = new ArrayList<>(); 
		mazeList.add(new MazeByDirectionPreference("map.txt" , "NEWS") );
		mazeList.get(0).printMaze();
		mazeList.add(new MazeByDirectionPreference("map.txt" , "NWES"));
		mazeList.add(new MazeByDirectionPreference("map.txt" , "SEWN"));
		mazeList.add(new MazeByDirectionPreference("map.txt" , "SWEN"));
		mazeList.add(new MazeByDirectionPreference("map.txt" , "WESN"));
		mazeList.add(new MazeByDirectionPreference("map.txt" , "WENS"));
		mazeList.add(new MazeByDirectionPreference("map.txt" , "ENSW"));
		mazeList.add(new MazeByDirectionPreference("map.txt" , "ESNW"));
		Collections.sort(mazeList);
		boolean first=true;
		for (MazeByDirectionPreference maze: mazeList)
		{
			//print stars
			if (first)
			{
				System.out.println("Best route using : " + maze.directionPreference);
				maze.printMaze();
			}
			else
			{
				System.out.println("Rest of routes ");
				maze.printMaze();
			}
			first=false;
		}
	}
}