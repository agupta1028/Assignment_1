package com.marquette.edu.puzzle8;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResourceFileReader {

	public static List<String> get8PuzzleInputs() throws IOException {
		
		List<String> indicatorList = new ArrayList<String>();
		InputStream resourceAsStream = ResourceFileReader.class.getResourceAsStream("/8PuzzleTestCases.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
		try {
			while(true)
			{
				String line = br.readLine();
				if(line != null) {
		    	indicatorList.add(line.trim());
				}else{
					break;
				}
			}
		} finally {
		    br.close();
		}
		return indicatorList;
	}

	
	/**
	 * executes all the test cases for 8 puzzle invokes the random move alog
	 * @throws IOException
	 */
	private static void drive8PuzzleUsingAlgorithmStrategy(AlgorithmStrategy algoType) throws IOException {
		List<String> stateStr = ResourceFileReader.get8PuzzleInputs();
		for (Iterator iterator = stateStr.iterator(); iterator.hasNext();) {
			String initState = (String) iterator.next();
			System.out.println("Initial State " + initState);
			int [] initInput = convertToIntArray(initState);
			PuzzleState puzzleInitState = new PuzzleState(initInput);
			
			String finalState = (String) iterator.next();
			System.out.println("Final State " + finalState);
			int [] finalInput = convertToIntArray(finalState);
			PuzzleState puzzleFinalState = new PuzzleState(finalInput);
			
			while(true) {
				boolean status = puzzleInitState.performMoveBasedOnStrategy(algoType, puzzleFinalState);
				if(status == false){
					System.out.println(" Cant' solve the puzzle ");
					puzzleInitState.printCurrentState();
					break;
				}
				//status true, check if solved
				boolean solved = puzzleInitState.isSolved(puzzleFinalState);
				if(solved){
					System.out.println(" Solved in " + puzzleInitState.getStepSinceStart()  + " steps");
					//puzzleInitState.printCurrentState();
					break;
				}
			}
		}
	}

	/**
	 * Utility function to convert input string to int array
	 * @param str
	 * @return
	 */
	private static int[] convertToIntArray(String str) {
		char[] charArray = str.toCharArray();
		int[] input = new int[9];
		for (int i = 0; i < charArray.length; i++) {
			input[i] =  Character.getNumericValue(charArray[i]);
		}
		return input;
	}
	
	
	public static void main(String args[]) throws Exception {
		AlgorithmStrategy randomAlgoType = new RandomAlgorithmStrategy(); // this strategy doesn't takes much longer
		drive8PuzzleUsingAlgorithmStrategy(randomAlgoType);
		AlgorithmStrategy saAlgoType = new SimulatedAnnealingAlgorithmStrategy();
		drive8PuzzleUsingAlgorithmStrategy(saAlgoType);
		
	}

}
