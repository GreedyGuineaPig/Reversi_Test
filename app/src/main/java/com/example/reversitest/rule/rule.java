package com.example.reversitest.rule;

import android.widget.Toast;

import com.example.reversitest.model.Board;
import com.example.reversitest.model.Cell;
import com.example.reversitest.model.Cell.E_STATUS;


public class rule {
    public static boolean canPut(int r, int c, Board mBoard){
        if(r > Board.ROWS || c > Board.COLS){
            return false;
        }

        Cell.E_STATUS P_Color = mBoard.getTurn();
        Cell.E_STATUS E_Color;
        if(P_Color == Cell.E_STATUS.White){
            E_Color = Cell.E_STATUS.Black;
        }else{
            E_Color = Cell.E_STATUS.White;
        }



        Cell[][] cell = mBoard.getCells();
        if(cell[r][c].getStatus() != E_STATUS.None){
            return false;
        }
        //Upward
        if (1 < r) {
            if (cell[r - 1][c].getStatus() == E_Color) {
                for (int i = 1; (r - i) > -1; i++) {
                    if(cell[r - i][c].getStatus() == E_STATUS.None){
                        break;
                    }
                    if (cell[r - i][c].getStatus() == P_Color) {
                        return true;
                    }
                }
            }
            //UpRight
            if (c < 6) {
                if (cell[r - 1][c + 1].getStatus() == E_Color) {
                    for (int i = 1; (c + i < 8) && (r - i > -1); i++) {
                        if(cell[r - i][c + i].getStatus() == E_STATUS.None){
                            break;
                        }
                        if (cell[r - i][c + i].getStatus() == P_Color) {
                            return true;
                        }
                    }
                }
            }
            //UpLeft
            if (1 < c) {
                if (cell[r - 1][c - 1].getStatus() == E_Color) {
                    for (int i = 1; (c - i > -1) && (r - i > -1); i++) {
                        if(cell[r - i][c - i].getStatus() == E_STATUS.None){
                            break;
                        }
                        if (cell[r - i][c - i].getStatus() == P_Color) {
                            return true;
                        }
                    }
                }
            }
        }

        //Right
        if (c < 6) {
            if (cell[r][c + 1].getStatus() == E_Color) {
                for (int i = 2; c + i < 8; i++) {
                    if(cell[r][c + i].getStatus() == E_STATUS.None){
                        break;
                    }
                    if (cell[r][c + i].getStatus() == P_Color) {
                        return true;
                    }
                }
            }
        }

        //Left
        if (1 < c) {
            if (cell[r][c - 1].getStatus() == E_Color) {
                for (int i = 2; c - i > -1; i++) {
                    if(cell[r][c - i].getStatus() == E_STATUS.None){
                        break;
                    }
                    if (cell[r][c - i].getStatus() == P_Color) {
                        return true;
                    }
                }
            }
        }

        //Down
        if (r < 6) {
            if (cell[r + 1][c].getStatus() == E_Color) {
                for (int i = 1; r + i < 8; i++) {
                    if(cell[r + i][c].getStatus() == E_STATUS.None){
                        break;
                    }
                    if (cell[r + i][c].getStatus() == P_Color) {
                        return true;
                    }
                }
            }

            //DownRight
            if (c < 6) {
                if (cell[r + 1][c + 1].getStatus() == E_Color) {
                    for (int i = 1; (c + i < 8) && (r + i < 8); i++) {
                        if(cell[r + i][c + i].getStatus() == E_STATUS.None){
                            break;
                        }
                        if (cell[r + i][c + i].getStatus() == P_Color) {
                            return true;
                        }
                    }
                }
            }

            //DownLeft
            if (1 < c) {
                if (cell[r + 1][c - 1].getStatus() == E_Color) {
                    for (int i = 1; (r + i < 8) && (c - i > -1); i++) {
                        if(cell[r + i][c - i].getStatus() == E_STATUS.None){
                            break;
                        }
                        if (cell[r + i][c - i].getStatus() == P_Color) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
