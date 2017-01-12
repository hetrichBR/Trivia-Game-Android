package com.het.soreal.sorealgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import Categories.Emojis;
import Categories.HistoricalFigures;

/**
 * Created by Bradley on 10/28/2015.
 */
public class Menu extends Activity{
    TabHost th2;
    TabHost.TabSpec specs;
    ImageView histPic, emojiPic;
    Typeface type;
    TextView title, summaryHist, summaryMore, summaryEmoji;
    boolean noInternet;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);
        noInternet = false;
        //app is being launched for the first time
        final String PREFS_NAME = "MyPrefsFile";
          SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            final Dialog dialog = new Dialog(this);

            dialog.setContentView(R.layout.welcome);
            dialog.setTitle("Welcome..");
            //set button (the material button)
            com.gc.materialdesign.views.ButtonRectangle dialogButton = (com.gc.materialdesign.views.ButtonRectangle) dialog.findViewById(R.id.bready);
            //if the button is clicked then close dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }

        //Define tabs
        th2 = (TabHost) findViewById(R.id.tabHost);
        th2.setup();

        //People Tab
        specs = th2.newTabSpec("tag4");
        specs.setContent(R.id.tabPeople);
        specs.setIndicator("Historical Figures");
        th2.addTab(specs);

        //emoji Tab
        specs = th2.newTabSpec("tag5");
        specs.setContent(R.id.tabemoji);
        specs.setIndicator("Emoji");
        th2.addTab(specs);

        //more Tab
        specs = th2.newTabSpec("tag6");
        specs.setContent(R.id.tabmore);
        specs.setIndicator("More");
        th2.addTab(specs);

        //font
         type = Typeface.createFromAsset(getAssets(),"font/lily.ttf");
        Tabtext();
        SetUp();

        //set the pic on menu to a historical pic
        HistoricalFigures historicalFigures = new HistoricalFigures();
        String Image[] = historicalFigures.getImageURL();

            //random number so the pic will be different everytime
            Random rand1 = new Random();
            int num = rand1.nextInt(Image.length - 1) + 0;
            Picasso.with(this).load(Image[num]).into(histPic);

        //if there is no image
        if(histPic.getHeight() == 0) {
            histPic.setImageResource(R.mipmap.default_picmenu);
           // histPic.setTag(1);
        }

        //set the pic on menu to a emoji pic
        Emojis emojis = new Emojis();
        String Imageemoji[] = emojis.getImageURL();

        //random number so the pic will be different everytime
        Picasso.with(this).load(Imageemoji[num]).into(emojiPic);

        //if there is no image
        if(emojiPic.getHeight() == 0)
        {
            //images won't show no connection
            noInternet = true;
           // emojiPic.setTag(1);
            emojiPic.setImageResource(R.mipmap.default_picmenu);
        }



    }

    public void Tabtext() {
        //change text color to white
        for (int i = 0; i < th2.getTabWidget().getChildCount(); i++) {
            //title is default tv for tabs
            TextView tv = (TextView) th2.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTypeface(type);
            tv.setTextSize(20f);
        }
    }


    public void SetUp()
    {
        //reference views

        histPic = (ImageView)findViewById(R.id.IVHistorical);
        emojiPic = (ImageView)findViewById(R.id.IVemoji);
        title = (TextView)findViewById(R.id.menuTitleTV);
        summaryHist = (TextView)findViewById(R.id.TVhist);
        summaryMore = (TextView)findViewById(R.id.TVmore);
        summaryEmoji = (TextView)findViewById(R.id.TVemoji);
        //change text font passes a typeface than TV's
       setLayoutFont(type, title, summaryHist, summaryMore, summaryEmoji);
        //set size
        title.setTextSize(40f);
        summaryHist.setTextSize(25f);
        summaryMore.setTextSize(25f);
        summaryEmoji.setTextSize(25f);


    }
    //Play history category
    public void PlayGame(View v)
    {

        if(noInternet)
            Toast.makeText(this, "No connection, may have trouble loading images", Toast.LENGTH_SHORT).show();

        String category = "History";
        Intent playGame = new Intent("com.het.soreal.sorealgame.MAINACTIVITY");
        playGame.putExtra("CATEGORY", category);
        startActivity(playGame);

    }
    public void PlayGameEmoji(View v)
    {
        //notify user images wont be displayed
        if(noInternet)
            Toast.makeText(this, "No connection, may have trouble loading images", Toast.LENGTH_SHORT).show();

        String category = "Emoji";
        Intent playGame = new Intent("com.het.soreal.sorealgame.MAINACTIVITY");
        playGame.putExtra("CATEGORY", category);
        startActivity(playGame);


    }
//takes in a text view and sets its typeface, takes in a typeface and all the textview params ...params is shorthand
    public static void setLayoutFont(Typeface tf, TextView...params) {
        for (TextView tv : params) {
            tv.setTypeface(tf);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        noInternet = false;
    }
}
