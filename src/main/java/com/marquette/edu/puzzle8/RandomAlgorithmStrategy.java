package com.marquette.edu.puzzle8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.marquette.edu.common.TileLocation;

public class RandomAlgorithmStrategy implements AlgorithmStrategy {

	/**
	 * update the current state based on strategy
	 */
	public boolean updateCurrentState(PuzzleState puzzleState,
			PuzzleState finalState) {
		puzzleState.setPrevious(puzzleState);
		puzzleState.setStepSinceStart(puzzleState.getStepSinceStart() + 1);
		if (performMove(puzzleState, finalState)) { // if move possible
			return true;
		}
		return false;
	}

	/**
	 * Perform the actual move operations, going only 1 level at a time
	 * 
	 * @param puzzleState
	 * @param finalState
	 * @return
	 */
	private boolean performMove(PuzzleState puzzleState, PuzzleState finalState) {
		List<TileLocation> openLoc = new ArrayList<TileLocation>();

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
			return false;
		}

		// later random using random generator
		Random generator = new Random();
		int i = generator.nextInt(openLoc.size());
		TileLocation movedTileLoc = openLoc.get(i);
		int newIndex = movedTileLoc.getXcord() + movedTileLoc.getYcord() * 3;
		puzzleState.getTileLocs()[emptyIndex] = puzzleState.getTileLocs()[newIndex];
		puzzleState.getTileLocs()[newIndex] = 0;
		return true;
	}

}
