package com.marquette.edu.puzzle8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.marquette.edu.common.TileLocation;

public class SimulatedAnnealingAlgorithmStrategy implements AlgorithmStrategy {

	private static final int maxTemp = 100000;
	private static int currTemp ;

	public boolean updateCurrentState(PuzzleState puzzleState,
			PuzzleState finalState) {
		currTemp = 100000;
		puzzleState.setPrevious(puzzleState);
		puzzleState.setStepSinceStart(puzzleState.getStepSinceStart() + 1);
		if (performMove(puzzleState, finalState)) { // if move possible
			return true;
		}
		return false;
	}

	private boolean performMove(PuzzleState puzzleState, PuzzleState finalState) {
		// get the current state energy level
		int energyLevel = getEnergyLevel(puzzleState, finalState);
		// System.out.println("energyLevel " + energyLevel);
		currTemp--;

		// get the options for emptySlide to move
		List<TileLocation> openLoc = new ArrayList<TileLocation>();
		if (currTemp > 0) {
			// get the options for emptySlide to move
			int emptyIndex = puzzleState.getEmptyTileIndex();
			int xcord = (emptyIndex % 3);
			int ycord = (emptyIndex / 3);

			// left tile check
			if (xcord != 0) { // no left
				TileLocation pt = new TileLocation(xcord - 1, ycord);
				openLoc.add(pt);
			}

			// right tile check
			if (xcord != 2) {// no right
				TileLocation pt = new TileLocation(xcord + 1, ycord);
				openLoc.add(pt);
			}

			// top tile check
			if (ycord != 0) {// no top
				TileLocation pt = new TileLocation(xcord, ycord - 1);
				openLoc.add(pt);
			}

			// bottom tile check
			if (ycord != 2) { // no bottom
				TileLocation pt = new TileLocation(xcord, ycord + 1);
				openLoc.add(pt);
			}

			// nothing available to move
			if (openLoc.isEmpty()) {
				System.out.println("Something wrong...");
				return false;
			}

			Random generator = new Random();
			int i = generator.nextInt(openLoc.size());
			TileLocation movedTileLoc = openLoc.get(i);
			int newIndex = movedTileLoc.getXcord() + movedTileLoc.getYcord()
					* 3;
			puzzleState.getTileLocs()[emptyIndex] = puzzleState.getTileLocs()[newIndex];
			puzzleState.getTileLocs()[newIndex] = 0;
			int newEnergyLevel = getEnergyLevel(puzzleState, finalState);
			// System.out.println("NewEnergyLevel " + newEnergyLevel);

			if (newEnergyLevel >= energyLevel) {
				if (!accpetanceFunction(currTemp)) {
					// later random using random generator
					//System.out.println("Rolling back ");
					puzzleState.getTileLocs()[newIndex] = puzzleState
							.getTileLocs()[emptyIndex];
					puzzleState.getTileLocs()[emptyIndex] = 0;
				}

			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Cost function
	 * 
	 * @param puzzleState
	 * @param finalState
	 * @return
	 */
	private int getEnergyLevel(PuzzleState puzzleState, PuzzleState finalState) {
		// find all available tiles that need to be moved
		int[] currentTileLoc = puzzleState.getTileLocs();
		int[] finalTileLoc = finalState.getTileLocs();
		int energyLevel = 0;
		for (int i = 0; i < currentTileLoc.length; i++) {
			if (currentTileLoc[i] != 0) {
				for (int j = 0; j < finalTileLoc.length; j++) {
					if (finalTileLoc[j] == currentTileLoc[i]) {
						int a = Math.abs(j - i);
						energyLevel += ((a / 3) + (a % 3));
						break;
					}
				}
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
