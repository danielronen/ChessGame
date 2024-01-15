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
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> opponentLegal) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            // whites king side castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61, opponentLegal).isEmpty() &&
                        Player.calculateAttacksOnTile(62, opponentLegal).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,
                                                                    (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(),
                                                                    61));
                    }
                }
            }
            // whites queen side castle
            if (!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(59, opponentLegal).isEmpty() &&
                        Player.calculateAttacksOnTile(58, opponentLegal).isEmpty() &&
                        Player.calculateAttacksOnTile(57, opponentLegal).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                                                                    (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(),
                                                                    59));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
