package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public int current_turn_player = 1;
    public int[] boardSquares = new int[9];
    public ImageView playerViewTurn;
    public ArrayList<ImageView> board;
    public ImageView lineWinnerImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.playerViewTurn = findViewById(R.id.mainactivity_player_view_turn);
        this.lineWinnerImageView = findViewById(R.id.mainactivity_winner_line);

        this.board = new ArrayList<>();
        this.board.add(findViewById(R.id.mainactivity_q1));
        this.board.add(findViewById(R.id.mainactivity_q2));
        this.board.add(findViewById(R.id.mainactivity_q3));
        this.board.add(findViewById(R.id.mainactivity_q4));
        this.board.add(findViewById(R.id.mainactivity_q5));
        this.board.add(findViewById(R.id.mainactivity_q6));
        this.board.add(findViewById(R.id.mainactivity_q7));
        this.board.add(findViewById(R.id.mainactivity_q8));
        this.board.add(findViewById(R.id.mainactivity_q9));

        for (int i = 0; i < boardSquares.length; i++) {
            boardSquares[i] = -1;
        }

        for (int j = 0; j < board.size(); j++) {
            ImageView boardQ = board.get(j);
            int squareNumber = j;
            boardQ.setOnClickListener(view -> {
                if (!this.isSquareUsed(squareNumber)) {
                    if (this.current_turn_player % 2 == 0) {
                        this.onXClick(boardQ, squareNumber);
                    } else {
                        this.onOClick(boardQ, squareNumber);
                    }
                    this.current_turn_player++;
                }
            });
        }
    }

    public void cleanBoard(){
        this.current_turn_player = 1;
        for (int i = 0; i < boardSquares.length; i++) {
            boardSquares[i] = -1;
            this.board.get(i).setImageResource(R.drawable.empty);
            this.playerViewTurn.setImageResource(R.drawable.oplay);
        }
        this.lineWinnerImageView.setVisibility(View.GONE);
        this.lineWinnerImageView.setRotation(0);
    }

    public boolean isSquareUsed(int squareNumber) {
        return this.boardSquares[squareNumber] != -1;
    }

    public void onXClick(ImageView boardQ, int qNumber) {
        Log.d("TAG", "X Click");
        this.boardSquares[qNumber] = 0;
        boardQ.setImageResource(R.drawable.x);
        this.playerViewTurn.setImageResource(R.drawable.oplay);
        checkWinner(0,R.drawable.xwin);
    }

    public void onOClick(ImageView boardQ, int squareNumber) {
        Log.d("TAG", "O Click");
        boardSquares[squareNumber] = 1;
        boardQ.setImageResource(R.drawable.o);
        playerViewTurn.setImageResource(R.drawable.xplay);
        checkWinner(1,R.drawable.owin);
    }

    public void checkWinner(int player,int playerResourceId) {
        if (this.boardSquares[0] == player && this.boardSquares[1] == player && this.boardSquares[2] == player) {
            this.winner(playerResourceId,R.drawable.mark3,90);
        } else if (this.boardSquares[3] == player && this.boardSquares[4] == player && this.boardSquares[5] == player) {
            this.winner(playerResourceId,R.drawable.mark4,90);
        } else if (this.boardSquares[6] == player && this.boardSquares[7] == player && this.boardSquares[8] == player) {
            this.winner(playerResourceId,R.drawable.mark5,90);
        } else if (this.boardSquares[0] == player && this.boardSquares[3] == player && this.boardSquares[6] == player) {
            this.winner(playerResourceId,R.drawable.mark3,null);
        } else if (this.boardSquares[1] == player && this.boardSquares[4] == player && this.boardSquares[7] == player) {
            this.winner(playerResourceId,R.drawable.mark4,null);
        } else if (this.boardSquares[2] == player && this.boardSquares[5] == player && this.boardSquares[8] == player) {
            this.winner(playerResourceId,R.drawable.mark5,null);
        } else if (this.boardSquares[0] == player && this.boardSquares[4] == player && this.boardSquares[8] == player) {
            this.winner(playerResourceId,R.drawable.mark1,null);
        } else if (this.boardSquares[2] == player && this.boardSquares[4] == player && this.boardSquares[6] == player) {
            this.winner(playerResourceId,R.drawable.mark2,null);
        }
        else if (this.current_turn_player == 9){
            this.showGaveOverDialog(R.drawable.nowin);
        }
    }

    public void winner(int playerResourceId , int markLineWinner , Integer rotationNumber){
        this.showGaveOverDialog(playerResourceId);
        this.lineWinnerImageView.setVisibility(View.VISIBLE);
        this.lineWinnerImageView.setImageResource(markLineWinner);
        if (rotationNumber != null){
            this.lineWinnerImageView.setRotation(rotationNumber);
        }
    }

    public void showGaveOverDialog(int winnerResource){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.activity_winner_dialog);
        ImageView winnerImage = dialog.findViewById(R.id.winnerdialog_winner);
        Button playAgainButton = dialog.findViewById(R.id.winnerdialog_play_again_btn);

        playAgainButton.setOnClickListener(view -> {
            this.cleanBoard();
            dialog.dismiss();
        });
        winnerImage.setImageResource(winnerResource);
        dialog.show();
    }
}