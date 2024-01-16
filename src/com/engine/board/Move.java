package com.engine.board;

import com.engine.pieces.Pawn;
import com.engine.pieces.Piece;
import com.engine.pieces.Rook;

import static com.engine.board.Board.*;

public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();


    private Move(final Board board, final Piece piece, final int destination){
        this.board = board;
        this.movedPiece = piece;
        this.destinationCoordinate = destination;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board, final int destination){
        this.board = board;
        this.destinationCoordinate = destination;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
        return result;
    }

    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof Move)) return false;
        final Move otherMove = (Move) other;
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPiece() == otherMove.getMovedPiece();
    }

    public Piece getMovedPiece(){
        return this.movedPiece;
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public int getCurrentCoordinate() {
        return this.getMovedPiece().getPiecePosition();
    }

    public boolean isAttack(){
        return false;
    }

    public boolean isCastlingMove(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
    }

    public Board execute() {
        final Builder builder = new Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        // move the moved piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

        return builder.build();
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece piece, final int destination) {
            super(board, piece, destination);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString(){
            return movedPiece.getPieceType().toString() +" - "+ BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece piece, final int destination, final Piece attackedPiece) {
            super(board, piece, destination);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object other){
            if(this == other)
                return true;
            if(! (other instanceof AttackMove)) return false;
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public boolean isAttack(){
            return true;
        }

        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }

        @Override
        public String toString(){
            return this.movedPiece.getPieceType().toString() +" - "+BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static  final class PawnMove extends Move{
        public PawnMove(final Board board, final Piece piece, final int destination) {
            super(board, piece, destination);
        }
    }

    public static class PawnAttackMove extends AttackMove{
        public PawnAttackMove(final Board board, final Piece piece, final int destination, final Piece attackedPiece) {
            super(board, piece, destination, attackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove{
        public PawnEnPassantAttackMove(final Board board, final Piece piece, final int destination, final Piece attackedPiece) {
            super(board, piece, destination, attackedPiece);
        }
    }

    public static  final class PawnJump extends Move{
        public PawnJump(final Board board, final Piece piece, final int destination) {
            super(board, piece, destination);
        }

        @Override
        public Board execute(){
            final Builder builder= new Builder();
            for (final Piece piece: this.board.currentPlayer().getActivePieces()){
                if (!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();

        }

        @Override
        public String toString() {
            return "P - " + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

     static abstract class CastleMove extends Move {

        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMove(final Board board, final Piece piece, final int destination, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, piece, destination);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook(){
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove(){
            return true;
        }

        @Override
        public Board execute(){

            final Builder builder = new Builder();
            for (final Piece piece: this.board.currentPlayer().getActivePieces()){
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            //TODO look into the first move on normal pieces
            builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    public static final class KingSideCastleMove extends CastleMove{

        public KingSideCastleMove(final Board board, final Piece piece, final int destination,final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, piece, destination, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString(){
            return "O-O";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove{

        public QueenSideCastleMove(final Board board, final Piece piece, final int destination, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, piece, destination, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString(){
            return "O-O-O";
        }
    }

    public static  final class NullMove extends Move{

        public NullMove() {
            super(null,-1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("cannot execute the null move!");
        }
    }

    public static class MoveFactory {
        private MoveFactory(){
            throw  new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate){
            for (final Move move : board.getAllLegalMoves()){
                if (move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }



}
