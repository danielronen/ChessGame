package com.engine.player;

import com.engine.Alliance;
import com.engine.board.Board;
import com.engine.board.Move;
import com.engine.pieces.King;
import com.engine.pieces.Piece;

import java.util.Collection;
import java.util.Collections;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves){
        this.board = board;
        this.legalMoves = legalMoves;
        this.playerKing = establishKing();
    }

    private King establishKing(){
        for(final Piece piece: getActivePieces()){
            if(piece.getPieceType().isKing()){
                return(King)piece;
            }
        }
        throw new RuntimeException("Should not reach here! Not a valid Board");
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();

}
