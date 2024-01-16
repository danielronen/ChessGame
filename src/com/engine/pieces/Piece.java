package com.engine.pieces;

import com.engine.Alliance;
import com.engine.board.Board;
import com.engine.board.Move;

import java.util.Collection;
import java.util.List;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove){
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece))
            return false;
        final Piece otherPiece = (Piece) other;
        return pieceAlliance == otherPiece.getPieceAlliance() && pieceType == otherPiece.getPieceType() &&
                piecePosition == otherPiece.getPiecePosition() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }
    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public abstract Piece movePiece(Move move);

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }


    public enum PieceType{

        PAWN("P", 100){
            @Override
            public boolean isKing() {
                return false;
            }
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N", 300){
            @Override
            public boolean isKing() {
                return false;
            }
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B", 300){
            @Override
            public boolean isKing() {
                return false;
            }
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R", 500){
            @Override
            public boolean isKing() {
                return false;
            }
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q", 900){
            @Override
            public boolean isKing() {
                return false;
            }
            public boolean isRook() {
                return false;
            }
        },
        KING("K", 10000){
            @Override
            public boolean isKing() {
                return true;
            }
            public boolean isRook() {
                return false;
            }
        };

        final private String pieceName;
        final private int pieceValue;

        PieceType(final String pieceName, final int pieceValue){
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }

        public abstract boolean isKing();
        public abstract boolean isRook();
        public int getPieceValue(){
            return this.pieceValue;
        }
    }
}
