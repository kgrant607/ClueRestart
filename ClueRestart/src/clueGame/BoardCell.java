package clueGame;

import java.util.Set;

/**
 * Defines a cell the board is composed of
 * 
 * @author Mark Baldwin
 * @author Cyndi Rader
 * 
 */
public class BoardCell {

	// row and column of this piece on the 2D board
	private int row;
	private int column;

	private DoorDirection doorDirection;

	// initial of the room (or walkway). Used to display room name
	// and to track last room visited.
	private char initial;

	// adjacency list
	private Set<BoardCell> adjacencies;

	/**
	 * Constructor for BoardCell
	 * 
	 * @param row        - row of cell
	 * @param col        - column of cell
	 * @param parameters - letter flags to describe cell
	 * @throws BadConfigFormatException
	 */
	BoardCell(int row, int col, String parameters) throws BadConfigFormatException {
		this.row = row;
		this.column = col;
		this.doorDirection = DoorDirection.NONE;
		initial = parameters.charAt(0);

		// the config file lines contain the room initial
		// if a doorway or the location for the name, will have a second character
		if (parameters.length() == 2) {
			char direction = parameters.charAt(1);
			switch (direction) {
			case 'R':
				doorDirection = DoorDirection.RIGHT;
				break;
			case 'L':
				doorDirection = DoorDirection.LEFT;
				break;
			case 'U':
				doorDirection = DoorDirection.UP;
				break;
			case 'D':
				doorDirection = DoorDirection.DOWN;
				break;
			case 'N':
				break;
			default:
				throw new BadConfigFormatException("Invalid room direction " + direction);
			}
		}
	}

	/*
	 * By default, a cell is not a walkway, room or door. Flags will get overridden
	 * in constructors
	 */
	public boolean isWalkway() {
		return initial == 'W';
	}

	public boolean isRoom() {
		return initial != 'W' && initial != 'X';
	}

	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

	public char getInitial() {
		return initial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setAdjacencies(Set<BoardCell> adj) {
		adjacencies = adj;
	}

	public Set<BoardCell> getAdjacencies() {
		return adjacencies;
	}

}
