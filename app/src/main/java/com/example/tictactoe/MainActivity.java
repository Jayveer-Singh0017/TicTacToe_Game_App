package com.example.tictactoe;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    boolean gameActive = true;
    // Player Representation
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int []gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    // State meaning
    // 0 - X
    // 1 - O
    // 2 - Null(Empty)
    int [][] winPositions = {   {0,1,2},{3,4,5}, {6,7,8},
                                {0,3,6},{1,4,7},{2,5,8},
                                {0,4,8},{2,4,6}
                            };
      public void playerTap(View view){
          ImageView img = (ImageView) view;
          int tappedImg = Integer.parseInt(img.getTag().toString());
//          BackgroundColorSpan bgcolor;

          if(!gameActive){
              gameReset(view);
          }

          if(gameState[tappedImg] == 2){
              gameState[tappedImg] = activePlayer;
              img.setTranslationY(-1000f);

              if(activePlayer == 0){
                  img.setImageResource(R.drawable.x);
                  activePlayer = 1;
                  TextView status = findViewById(R.id.status);
                  status.setText("O's Turn - Tap to play");
              }
              else{
                  img.setImageResource(R.drawable.o);
                  activePlayer = 0;
                  TextView status = findViewById(R.id.status);
                  status.setText("X's Turn - Tap to play");
              }
              img.animate().translationYBy(1000f).setDuration(300);
          }


          // Check if any Player has Won
          for(int[] winPosition : winPositions){
              if(gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]]
                      &&  gameState[winPosition[0]] != 2){
                  // Somebody has won! = Find who!
                  String winnerStr;
                  gameActive = false;
                  if(gameState[winPosition[0]] == 0){
                      winnerStr = "X has Won \uD83C\uDFC6";
                  }
                  else{
                      winnerStr = "O has Won \uD83C\uDFC6";
                  }
                  // Update the status bar for winner announcement
                  TextView status = findViewById(R.id.status);
                  status.setText(winnerStr);
              }
          }

          // TGY Logic
          if(gameActive == true){
              int checkFillAllBlock = 0;
              for(int i=0; i<gameState.length; i++){
                  if (gameState[i] != 2) {
                      checkFillAllBlock++;
                  }
              }
              if(checkFillAllBlock == 9){
                  TextView status = findViewById(R.id.status);
                  status.setText("Match Tie \uD83D\uDC4A");
                  gameActive = false;
              }
          }

      }

      public void gameReset(View view){
          gameActive = true;
          activePlayer = 0;
          for(int i=0; i<gameState.length; i++){
              gameState[i] = 2;
          }

          ( (ImageView)findViewById(R.id.imageView0) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView1) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView2) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView3) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView4) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView5) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView6) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView7) ).setImageResource(0);
          ( (ImageView)findViewById(R.id.imageView8) ).setImageResource(0);

          TextView status = findViewById(R.id.status);
          status.setText("X's Turn - Tap to play");

      }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.titleBar_color)));

    }
}