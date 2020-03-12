package com.example.reversitest;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.reversitest.model.Board;
import com.example.reversitest.model.Cell;
import com.example.reversitest.rule.rule;


public class ReversiView extends View {
    private Board mBoard = new Board();

    public ReversiView(Context context) {
        super(context);

        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBoard.setSize(getWidth(), getHeight());

        drawBoard(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int r = (int) (y / mBoard.getCellHeight());
                int c = (int) (x / mBoard.getCellWidth());
                if (rule.canPut(r, c, mBoard)) {//ここを”置けるか”に投げる
                    try {
                        mBoard.changeCell(r, c, mBoard.getTurn());
                        mBoard.flipAround(r, c, mBoard);
                        mBoard.changeTurn();
                    } catch (Exception e) {
                        //Toast.makeText(this.getContext(), e.getMessage(), 300).show();
                    }

                    invalidate();            //画面を再描画
                }
                int canPutCount = 0;
                for (int rTest = 0; rTest < Board.ROWS; rTest++) {
                    for (int cTest = 0; cTest < Board.COLS; cTest++) {
                        if (rule.canPut(rTest, cTest, mBoard)) {
                            canPutCount++;
                        }
                    }
                }
                if (canPutCount == 0) {
                    Toast.makeText(this.getContext(), "Pass!" + mBoard.getTurn(), Toast.LENGTH_SHORT).show();
                    mBoard.changeTurn();
                }
                for (int rTest = 0; rTest < Board.ROWS; rTest++) {
                    for (int cTest = 0; cTest < Board.COLS; cTest++) {
                        if (rule.canPut(rTest, cTest, mBoard)) {
                            canPutCount++;
                        }
                    }
                }
                if (canPutCount == 0) {
                    Toast.makeText(this.getContext(), "Game over!" + mBoard.getTurn(), Toast.LENGTH_SHORT).show();
                    int wCount = 0;
                    int bCount = 0;
                    Cell[][] cells = mBoard.getCells();
                    for (int rTest = 0; rTest < Board.ROWS; rTest++) {
                        for (int cTest = 0; cTest < Board.COLS; cTest++) {
                            if (cells[rTest][cTest].getStatus() == Cell.E_STATUS.Black) {
                                bCount++;
                            } else if (cells[rTest][cTest].getStatus() == Cell.E_STATUS.White) {
                                wCount++;
                            }
                        }
                    }
                    Toast.makeText(this.getContext(), "Black:" + bCount + " White:" + wCount, Toast.LENGTH_LONG).show();
                }

                break;
            //ここに終了判定（両者おけない＝置けるか黒、置けるか白）
            default:
                return true;
        }

        return true;
    }

    private void drawBoard(Canvas canvas) {
        int bw = mBoard.getWidth();
        int bh = mBoard.getHeight();
        float cw = mBoard.getCellWidth();
        float ch = mBoard.getCellHeight();

        if (mBoard.getWidth() <= 0) return;

        Paint paint = new Paint();
        paint.setColor(Color.rgb(0, 100, 0));

        canvas.drawRect(0, 0, bw, bh, paint);

        paint.setColor((Color.rgb(0, 0, 0)));

        for (int i = 0; i < Board.COLS; i++) {
            canvas.drawLine(cw * (i + 1), 0, cw * (i + 1), bh, paint);
        }

        for (int i = 0; i < Board.ROWS; i++) {
            canvas.drawLine(0, ch * (i + 1), bw, ch * (i + 1), paint);
        }

        paint.setAntiAlias(true);

        Cell[][] cells = mBoard.getCells();
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                Cell cell = cells[i][j];
                Cell.E_STATUS st = cell.getStatus();

                if (st == Cell.E_STATUS.Black) {
                    paint.setColor(Color.BLACK);
                } else if (st == Cell.E_STATUS.White) {
                    paint.setColor(Color.WHITE);
                }

                if (st != Cell.E_STATUS.None) {
                    canvas.drawCircle(cell.getCx(), cell.getCy(), (float) (cw * 0.46), paint);
                }
            }
        }
    }
}


//Reference: https://blog.makotoishida.com/2011/06/android-1.html

