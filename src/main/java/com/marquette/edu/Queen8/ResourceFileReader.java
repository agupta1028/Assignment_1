package com.marquette.edu.Queen8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResourceFileReader {

	public static List<String> get8QueensInputs() throws IOException {

		List<String> indicatorList = new ArrayList<String>();
		InputStream resourceAsStream = ResourceFileReader.class
				.getResourceAsStream("/8QueensTestCases.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				resourceAsStream));
		try {
			while (true) {
				String line = br.readLine();
				if (line != null) {
					indicatorList.add(line.trim());
				} else {
					break;
				}
			}
		} finally {
			br.close();
		}
		return indicatorList;
	}

	/**
	 * Utility function to convert input string to int array
	 * 
	 * @param str
	 * @return
	 */
	private static int[] convertToIntArray(String str) {
		char[] charArray = str.toCharArray();
		int[] input = new int[8];
		for (int i = 0; i < input.length; i++) {
			input[i] = 0;
		}
				
		for (int i = 0; i < charArray.length; i++) {
			input[i] = Character.getNumericValue(charArray[i]);
		}
		return input;
	}

	/**
	 * executes all the test cases for 8 puzzle invokes the random move alog
	 * 
	 * @throws IOException
	 */
	private static void drive8QueenUsingAlgorithmStrategy(
			AlgorithmStrategy8Queen algoType) throws IOException {

		List<String> queen8InitialState = ResourceFileReader.get8QueensInputs();
		for (Iterator iterator = queen8InitialState.iterator(); iterator
				.hasNext();) {
			String inputStateStr = (String) iterator.next();
			System.out.println("State " + inputStateStr);
			int[] stateArray = convertToIntArray(inputStateStr);
			PuzzleState8Queen puzzleInitState = new PuzzleState8Queen(
					stateArray);

			boolean status = puzzleInitState
					.performMoveBasedOnStrategy(algoType);
			if (!status) {
				System.out.println(" Cant' solve the puzzle ");
				//puzzleInitState.printCurrentState();
			}
			// status true, check if solved
			if (status) {
				System.out.println(" Solved in "
						+ puzzleInitState.getStepSinceStart() + " steps");
				//puzzleInitState.printCurrentState();
			}
		}
	}

	public static void main(String args[]) throws Exception {
		AlgorithmStrategy8Queen saAlgoType = new SimulatedAnnealingAlgorithmStrategy8Queen();
		drive8QueenUsingAlgorithmStrategy(saAlgoType);
	}

}
