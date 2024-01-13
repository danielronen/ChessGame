package com.engine.board;

import com.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    private Move(final Board board, final Piece piece, final int destination){
        this.board = board;
        this.movedPiece = piece;
        this.destinationCoordinate = destination;
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece piece, final int destination) {
            super(board, piece, destination);
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece piece, final int destination, final Piece attackedPiece) {
            super(board, piece, destination);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
