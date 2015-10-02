package com.marquette.edu.Queen8;

import java.util.Arrays;

public class PuzzleState8Queen {

	private int[] tileLocs = new int[8];
	private int stepSinceStart;
	private PuzzleState8Queen previous;

	public PuzzleState8Queen getPrevious() {
		return previous;
	}

	public int[] getTileLocs() {
		return tileLocs;
	}

	public void setTileLocs(int[] tileLocs) {
		this.tileLocs = tileLocs;
	}

	public int getStepSinceStart() {
		return stepSinceStart;
	}

	public void setStepSinceStart(int stepSinceStart) {
		this.stepSinceStart = stepSinceStart;
	}

	public void setPrevious(PuzzleState8Queen previous) {
		this.previous = previous;
	}

	public PuzzleState8Queen(int[] input) {
		this.tileLocs = input;
		this.previous = null;
	}

	public int getEmptyTileIndex() {
		for (int i = 0; i < tileLocs.length; i++) {
			if (tileLocs[i] == 0) {
				return i;
			}
		}
		return -1; // error case
	}

	public boolean performMoveBasedOnStrategy(AlgorithmStrategy8Queen algoType) {
		return algoType.updateCurrentState(this);
	}

	public boolean isSolved(PuzzleState8Queen finalState) {
		return Arrays.equals(getTileLocs(), finalState.getTileLocs());
	}

	public void printCurrentState() {
		System.out.println("Current State:");
		for (int i = 0; i < tileLocs.length; i++) {
			System.out.println(tileLocs[i]);
		}

		System.out.println("Steps since start:" + stepSinceStart);

		System.out.println("Previous State:");
		int[] tileLocs2 = previous.getTileLocs();
		for (int i = 0; i < tileLocs2.length; i++) {
			System.out.println(tileLocs2[i]);
		}
	}

}
