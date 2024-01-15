package com.engine.player;

import com.engine.Alliance;
import com.engine.board.Board;
import com.engine.board.Move;
import com.engine.board.Tile;
import com.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WhitePlayer extends Player{


    public WhitePlayer(final Board board,final Collection<Move> whiteStandardLegalMoves,final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegal) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            // whites king side castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61, opponentLegal).isEmpty() &&
                        Player.calculateAttacksOnTile(62, opponentLegal).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()){
                        //TODO add a castleMove
                        kingCastles.add(null);
                    }

                }
            }
            if (!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(59, opponentLegal).isEmpty() &&
                        Player.calculateAttacksOnTile(58, opponentLegal).isEmpty() &&
                        Player.calculateAttacksOnTile(57, opponentLegal).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()){
                        //TODO add a castleMove
                        kingCastles.add(null);
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }
}
