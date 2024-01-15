package com.engine.board;

import com.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;


public abstract class Tile {

    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    public int getTileCoordinate(){
        return this.tileCoordinate;
    }

    private Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{

        private EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied(){
            return false;
        }

        @Override
        public String toString(){
            return "-";
        }


        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccupiedTile extends Tile{

        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }

        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }
    }
}
