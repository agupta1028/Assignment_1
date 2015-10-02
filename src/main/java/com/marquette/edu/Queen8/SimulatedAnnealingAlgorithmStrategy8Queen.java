package com.marquette.edu.Queen8;

import java.util.Random;

public class SimulatedAnnealingAlgorithmStrategy8Queen implements
		AlgorithmStrategy8Queen {

	private static final int maxTemp = 1000000;
	private static int currTemp;

	public boolean updateCurrentState(PuzzleState8Queen puzzleState) {
		currTemp = maxTemp;
		if (performMove(puzzleState)) { // if move possible
			return true;
		}
		return false;
	}

	private boolean performMove(PuzzleState8Queen initPuzzleState) {
		// get the current state energy level
		int initEnergyLevel = getEnergyLevel(initPuzzleState);
		// System.out.println("energyLevel " + energyLevel);

		int startColumnIndex = 8 - initEnergyLevel;
		while (true) {
			if (!isSolved(initPuzzleState)) {
				if (currTemp > 0) {
					initPuzzleState.setPrevious(initPuzzleState);
					currTemp--;
					Random generator = new Random();
					int num = generator.nextInt(initEnergyLevel);
					int colToMod = startColumnIndex + num;
					initPuzzleState.getTileLocs()[colToMod]++;
					// move the respective column queen 1 place
					if (ifConflicts(initPuzzleState)) {
						// see if any conflicts if yes
						if (!accpetanceFunction(currTemp)) {
							// System.out.println("Rolling back ");
							initPuzzleState.getTileLocs()[colToMod]--;
							continue;
						}
					}
					if (initPuzzleState.getTileLocs()[colToMod] > 8) {
						initPuzzleState.getTileLocs()[colToMod] = 1;
					}
					initPuzzleState.setStepSinceStart(initPuzzleState
							.getStepSinceStart() + 1);
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}

	private boolean ifConflicts(PuzzleState8Queen initPuzzleState) {
		int[] locs = initPuzzleState.getTileLocs();
		for (int i = 0; i < locs.length; i++) {
			for (int j = i + 1; j < locs.length; j++) {
				if (locs[i] == locs[j]) {
					return true;
				}
			}
		}
		// check the diagonal attacking positions
		int row = 0;
		for (int i = 0; i < locs.length; i++) {
			row = locs[i];
			// move diagonally forward if conflict return true
			for (int j = i + 1; j < locs.length; j++) {
				row++;
				if (row > 8) {
					break;
				}
				if (row == locs[j]) {
					return true;
				}
			}
			row = locs[i];
			// move diagonally backward if conflict return true
			for (int j = i - 1; j > 0; j--) {
				row--;
				if (row < 1) {
					break;
				}
				if (row == locs[j]) {
					return true;
				}
			}

		}
		return false;
	}

	private boolean isSolved(PuzzleState8Queen initPuzzleState) {
		int sum = 0;
		int[] locs = initPuzzleState.getTileLocs();
		for (int i = 0; i < locs.length; i++) {
			sum += locs[i];
		}

		if ((!ifConflicts(initPuzzleState)) && (sum == 36)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Cost function
	 * 
	 * @param puzzleState
	 * @param finalState
	 * @return
	 */
	private int getEnergyLevel(PuzzleState8Queen puzzleState) {
		// find all available tiles that need to be moved
		int[] currentTileLoc = puzzleState.getTileLocs();

		int energyLevel = 0;
		for (int i = 0; i < currentTileLoc.length; i++) {
			if (currentTileLoc[i] == 0) {
				energyLevel++;
			}
		}
		return energyLevel;
	}

	/**
	 * Acceptance probability
	 * 
	 * @param currTemp
	 * @return
	 */
	private boolean accpetanceFunction(int currTemp) {
		Random generator = new Random();
		int random = generator.nextInt(100);
		double tempReducRate = (double) currTemp / (double) maxTemp;
		if (((double) random / 10) * tempReducRate > 1) {
			return false;
		}
		return true;
	}

}
