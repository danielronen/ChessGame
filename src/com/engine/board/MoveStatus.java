package com.engine.board;

public enum MoveStatus {
    DONE{
        @Override
        public boolean isDone(){
            return true;
        }
    },

    LEAVES_PLAYER_IN_CHECK{
        public boolean isDone(){
            return false;
        }
    }

    ,
    ILLEGAL_MOVE {
        public boolean isDone(){
            return false;
        }
    }
    ;

    public abstract boolean isDone();

    }
