package africa.younglings.carelse.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0 = yellow
    //1 = red
    int activePlayer = 0;
    boolean gameIsActive = true;
    String winner = "Red";
    //2 = unplayed
    int[] gameState= {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    Button btnRestart;
    TextView winnerMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRestart = (Button)findViewById(R.id.btnRetry);
        winnerMessage = (TextView)findViewById(R.id.tvheading);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;


            counter.setTranslationY(-1800f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);

                activePlayer = 0;
            }

            counter.animate().translationYBy(1800f).setDuration(300);

            for(int[] winPosition : winPositions){

                if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                gameState[winPosition[1]] == gameState[winPosition[2]] &&
                gameState[winPosition[0]] != 2){

                    String winner = "Red";
                    if (gameState[winPosition[0]] == 0){
                        winner = "Yellow";
                    }
                    gameIsActive = false;
                    winnerMessage.setText(winner + " has won");
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
                    linearLayout.animate().alphaBy(1f).setDuration(3000);

                    btnRestart.setEnabled(true);
                }else{
                    boolean gameIsOver = true;
                    for(int counterState : gameState){
                        if(counterState == 2) gameIsOver = false;
                    }
                    if(gameIsOver){
                        winnerMessage.setText("It's a draw");
                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
                        linearLayout.animate().alphaBy(1f).setDuration(3000);

                        btnRestart.setEnabled(true);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        linearLayout.setAlpha(0f);
        activePlayer = 0;

        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.Grid);

        for(int i = 0; i < gridLayout.getChildCount(); i++){

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        gameIsActive = true;
        btnRestart.setEnabled(false);
    }
}
