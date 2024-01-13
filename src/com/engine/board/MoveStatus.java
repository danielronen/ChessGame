package com.engine.board;

public enum MoveStatus {
    DONE{
        @Override
        public boolean isDone(){
            return false;
        }
    };

    public abstract boolean isDone();

    }
