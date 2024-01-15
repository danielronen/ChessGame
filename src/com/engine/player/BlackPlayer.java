package com.engine.player;

import com.engine.Alliance;
import com.engine.board.Board;
import com.engine.board.Move;
import com.engine.board.Tile;
import com.engine.pieces.Piece;
import com.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.engine.board.Move.*;

public class BlackPlayer extends Player{

    public BlackPlayer(final Board board,final Collection<Move> whiteStandardLegalMoves,final Collection<Move> blackStandardLegalMoves) {
        super(board,blackStandardLegalMoves,whiteStandardLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> opponentLegal) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            // Blacks king side castle
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(5, opponentLegal).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentLegal).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){
                            kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6,
                                                                    (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(),
                                                                    5));
                    }
                }
            }
            // Blacks queen side castle
            if (!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(1, opponentLegal).isEmpty() &&
                            Player.calculateAttacksOnTile(2, opponentLegal).isEmpty() &&
                            Player.calculateAttacksOnTile(3, opponentLegal).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2,
                                                                    (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(),
                                                                    3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
