package com.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initCol(0);
    public static final boolean [] SECOND_COLUMN = initCol(1);
    public static final boolean [] SEVENTH_COLUMN = initCol(6);
    public static final boolean [] EIGHTH_COLUMN = initCol(7);

    public static final boolean [] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);
    public static final int NUM_TILES = 64;
    public static final  int NUM_TILES_PER_ROW = 8;

    private BoardUtils(){
        throw new RuntimeException("You cannot use me");
    }

    private static boolean[] initCol(int colNumber) {
        final boolean [] column = new boolean[NUM_TILES];
        do {
            column[colNumber] = true;
            colNumber+=NUM_TILES_PER_ROW;
        }while(colNumber < NUM_TILES);
        return column;
    }

    private static boolean[] initRow(int rowNum){
        final boolean[] row = new boolean[NUM_TILES];
        do{
            row[rowNum] = true;
            rowNum++;
        }while(rowNum % NUM_TILES_PER_ROW != 0);

        return row;
    }

    public static boolean isValidCoordinate(final int coordinate) {
        return coordinate < NUM_TILES && coordinate >= 0;
    }
}
