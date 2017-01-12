package com.het.soreal.sorealgame;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.revmob.RevMob;
import com.revmob.ads.interstitial.RevMobFullscreen;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Random;

import Categories.Emojis;
import Categories.HistoricalFigures;

public class MainActivity extends AppCompatActivity {
//TODO activate revmob
    /**Have seperate classes for question categories
     *
     * Celebs
     * Famous animals/pets
     * 
     */
    RevMobFullscreen fullscreen;
    String category = "";

    //Strings that will store word questions and urls
    String Word [] = {};
    String answerA [] = { };
    String answerB [] = {};
    String answerC [] = {};
    String answerD [] = {};
    String answers [] = {};
    String imageURL [] = {};

    public int questionsCompleted = 0;
    public int questionsRight = 0;
    Typeface type;
    ImageView imageView;
    RadioGroup groupAnswers;
    RadioButton choiceA, choiceB, choiceC, choiceD;
    TextView tvword,textViewgame;
    int count = 0;
    MediaPlayer playerCorrect;
    MediaPlayer playerWrong;
    //Animation
    Animation animationFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //audio funtion
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        playerCorrect = MediaPlayer.create(this, R.raw.correct);
        playerWrong = MediaPlayer.create(this, R.raw.wrong);

        RevMob revmob = RevMob.start(this); // RevMob Media ID configured in the AndroidManifest.xml file
        fullscreen = revmob.createFullscreen(this, null); // pre-load it without showing it

        //Initilize typeface
        type = Typeface.createFromAsset(getAssets(), "font/lily.ttf");

        //refernece views
        tvword = (TextView)findViewById(R.id.TVguessWord);
        textViewgame = (TextView)findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.imageView);

        groupAnswers = (RadioGroup)findViewById(R.id.RGAnswers);
        choiceA = (RadioButton)findViewById(R.id.checkBox);
        choiceB = (RadioButton)findViewById(R.id.checkBox2);
        choiceC = (RadioButton)findViewById(R.id.checkBox3);
        choiceD = (RadioButton)findViewById(R.id.checkBox4);

        //change font
        ChangeFont();

        //set fade in animation
        animationFadeIn = new AlphaAnimation(0.0f, 1.0f);
        animationFadeIn.setDuration(2000);

        //set category equal to the string passed with the intent
        category = getIntent().getStringExtra("CATEGORY");
        Log.d("Category", "Category is " + category);
        SetQuestions();

    }


public void ChangeFont()
{

    tvword.setTypeface(type);
    textViewgame.setTypeface(type);
    choiceA.setTypeface(type);
    choiceB.setTypeface(type);
    choiceC.setTypeface(type);
    choiceD.setTypeface(type);
    //set size
    textViewgame.setTextSize(25f);
    choiceA.setTextSize(19f);
    choiceB.setTextSize(19f);
    choiceC.setTextSize(19f);
    choiceD.setTextSize(19f);

}
    //set the questions based on category chosen
    public void SetQuestions()
    {
        HistoricalFigures historicalFigures = new HistoricalFigures();
        Emojis emojis = new Emojis();
        //get category
        switch(category)
        {

            case "History":

                Word = historicalFigures.getHistoricalWord();
                answerA = historicalFigures.getHistoricalA();
                answerB = historicalFigures.getHistoricalB();
                answerC = historicalFigures.getHistoricalC();
                answerD = historicalFigures.getHistoricalD();
                answers = historicalFigures.getHistoricalAnswers();
                imageURL = historicalFigures.getImageURL();



                break;

            case "Emoji":

                Word = emojis.getEmojiWord();
                answerA = emojis.getEmojiA();
                answerB = emojis.getEmojiB();
                answerC = emojis.getEmojiC();
                answerD = emojis.getEmojiD();
                answers = emojis.getEmojiAnswers();
                imageURL = emojis.getImageURL();



                break;

        };
        //Toast.makeText(this,"A is" + answerA[count],Toast.LENGTH_SHORT).show();
        //Initilize the first question
        //set word and image
        ShuffleOrder();
        tvword.setText(Word[count]);

        Picasso.with(this).load(imageURL[count]).into(imageView);
        //if there is no image
        if(imageView.getHeight() == 0)
            imageView.setImageResource(R.mipmap.default_pic);


        //imageView
        //set questions
        choiceA.setText("A: " + answerA[count]);
        choiceB.setText("B: " + answerB[count]);
        choiceC.setText("C: " + answerC[count]);
        choiceD.setText("D: " + answerD[count]);

    }

    public void ShuffleOrder()
    {//Shuffle questions
        String temp = "";

        for (int i = 0; i < 1000; i++) {

            Random rand1 = new Random();
            int num = rand1.nextInt(imageURL.length - 1) + 0;
            Random rand = new Random();
            int num2 = rand.nextInt(imageURL.length - 1) + 0;

            temp = imageURL[num];
            imageURL[num] = imageURL[num2];
            imageURL[num2] = temp;

            temp = answerA[num];
            answerA[num] = answerA[num2];
            answerA[num2] = temp;

            temp = answerB[num];
            answerB[num] = answerB[num2];
            answerB[num2] = temp;

            temp = answerC[num];
            answerC[num] = answerC[num2];
            answerC[num2] = temp;

            temp = answerD[num];
            answerD[num] = answerD[num2];
            answerD[num2] = temp;


            temp = answers[num];
            answers[num] = answers[num2];
            answers[num2] = temp;

            temp = Word[num];
            Word[num] = Word[num2];
            Word[num2] = temp;

        }


        //shuffle the order of the questions
        String temp2[];
        //each question has 2 versions/ orders
        Random rand3 = new Random();
        int num3 = rand3.nextInt(2) + 1;
        //if 2 change it if one keep order as initilized
       //Toast.makeText(this,"The num is" + num,Toast.LENGTH_SHORT).show();
        if(num3 == 2)
        {
            temp2 = answerA;
            answerA = answerB;
            answerB = temp2;

            temp2 = answerD;
            answerD = answerC;
            answerC = temp2;

            temp2 = answerA;
            answerA = answerC;
            answerC = temp2;

            temp2 = answerD;
            answerD = answerB;
            answerB = temp2;
        }
        //Toast.makeText(this,"The A is" + answerA[count],Toast.LENGTH_SHORT).show();
    }

    public void NextQuestion(View v)
    {

        Log.d("COUNT IS ",count +"");
        Log.d("length ",answers.length - 1 + "");
        //get the guess and see if it matches with the answer
        String guess = "";
        switch(groupAnswers.getCheckedRadioButtonId()){

            case R.id.checkBox:
                 guess = answerA[count];
                break;

            case R.id.checkBox2:
                guess = answerB[count];
                break;

            case R.id.checkBox3:
                guess = answerC[count];
                break;

            case R.id.checkBox4:
                guess = answerD[count];
                break;
        }
        //check if correct
        if (guess.equals(answers[count])) {

            //play sound
            playerCorrect.start();

            //set dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.correctdialog);
            dialog.setTitle("Your Answer is..");
          //
            //set button (the material button)
            com.gc.materialdesign.views.ButtonRectangle dialogButton = (com.gc.materialdesign.views.ButtonRectangle) dialog.findViewById(R.id.bcorrect);
            //if the button is clicked then close dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            //TODO Animations!!!!
             //dialog.startAnimation(animationFadeIn);
            dialog.show();
            questionsRight ++;
            questionsCompleted ++;

        }
        //if wrong
        else {
            //play sound
            playerWrong.start();

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.wrongdialog);
            dialog.setTitle("Your Answer is..");
            //set button (the material button)
            com.gc.materialdesign.views.ButtonRectangle dialogButton = (com.gc.materialdesign.views.ButtonRectangle) dialog.findViewById(R.id.bwrong);
            //if the button is clicked then close dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            //count question completed
            questionsCompleted ++;
        }

        Log.d("GUESS IS",guess);
        Log.d("Answer IS",answers[count]);

        //incre,ent count to go to next question
        if(count < answers.length - 1)
            count++;
        else if(count == answers.length - 1 ) {
            count = 0;

        }

        //check if 10 questions are completed, end round
        if(questionsCompleted == 10)
        {


            //new dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.results);
            dialog.setTitle("Results");

            //star image set
            ImageView star = (ImageView)dialog.findViewById(R.id.IVcorrect);

            if(questionsRight>= 0 && questionsRight < 6)
                 star.setImageResource(R.mipmap.star_1);
            else if(questionsRight>= 6 && questionsRight < 10)
                star.setImageResource(R.mipmap.star_2);
            else if (questionsRight == 10)
                star.setImageResource(R.mipmap.star_3);

                //set text
            TextView TVresult = (TextView)dialog.findViewById(R.id.TVresults);
            TVresult.setTypeface(type);
            TVresult.setTextSize(25f);
            TVresult.setText("You got " + questionsRight + " out of " + questionsCompleted + " correct!");
            //set button (the material button)
            com.gc.materialdesign.views.ButtonRectangle dialogButton = (com.gc.materialdesign.views.ButtonRectangle) dialog.findViewById(R.id.bresults);
            //if the button is clicked then main menu
            dialogButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    MainMenu(v);
                }
            });
            dialog.show();



        }

        //set next question
        tvword.setText(Word[count]);

        //method to check internet
        //Boolean internetConnection = CheckConnection();

        //if there is a connection load from URL if not load from resource

            Picasso.with(this).load(imageURL[count]).into(imageView);
        //if there is no image
        if(imageView.getHeight() == 0) {
            imageView.setImageResource(R.mipmap.default_pic);
            //notify user images wont display
             Toast.makeText(this,"No connection to display images",Toast.LENGTH_SHORT).show();
        }

        //imageView
        //set questions
        choiceA.setText("A: " + answerA[count]);
        choiceB.setText("B: " + answerB[count]);
        choiceC.setText("C: " + answerC[count]);
        choiceD.setText("D: " + answerD[count]);

    }
    public void MainMenu(View v)
    {
        Intent menu = new Intent(this, com.het.soreal.sorealgame.Menu.class);
        startActivity(menu);
        fullscreen.show();
        finish();
    }



}
