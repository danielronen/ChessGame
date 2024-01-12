package com.engine.player;

import com.engine.board.Board;
import com.engine.board.Move;
import com.engine.pieces.Piece;

import java.util.Collection;

public class BlackPlayer extends Player{

    public BlackPlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board,blackStandardLegalMoves,whiteStandardLegalMoves);


    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
}
