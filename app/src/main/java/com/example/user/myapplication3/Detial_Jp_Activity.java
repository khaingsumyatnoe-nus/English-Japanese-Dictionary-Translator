package com.example.user.myapplication3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Detial_Jp_Activity extends Activity {
    TextToSpeech mTTs1;
    ImageView jp;

    String english;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jp);
        TextView wordjp=(TextView)findViewById(R.id.wordjp);
        TextView partspeechjp=(TextView)findViewById(R.id.partofspeechjp);
        TextView defeng=(TextView)findViewById(R.id.defeng);
        jp=(ImageView)findViewById(R.id.button_speak_eng);
      // TextView jpword=(TextView)findViewById(R.id.jp);
        Intent intent=getIntent();

        if(intent.getExtras()!=null){
            english=intent.getStringExtra("english1");
            String kanji=intent.getStringExtra("kanji1");
           String kana1=intent.getStringExtra("kana1");
            String romaji=intent.getStringExtra("romaji1");
           String part_of_speech=intent.getStringExtra("part_of_speech1");

            // tv.setText(english+"\n"+kanji+"\n"+kana+"\n"+romaji+"\n"+part_of_speech);
            wordjp.setText(kana1+"\n"+kanji+"\n"+romaji);
            partspeechjp.setText(part_of_speech);
           // jpword.setText(kanji+"\n"+romaji);
            defeng.setText(english);

            mTTs1=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status==TextToSpeech.SUCCESS){
                        int result=mTTs1.setLanguage(Locale.ENGLISH);
                        if(result == TextToSpeech.LANG_MISSING_DATA || result== TextToSpeech.LANG_NOT_SUPPORTED){
                            Log.e("TTS","Language not supported");
                        }
                        else{
                            jp.setEnabled(true);
                        }

                    }
                    else{
                        Log.e("TTS","initialization fialed");

                    }
                }

            });
 jp.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         speakNow();
     }
 });




        }


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_to_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }

        //if you want to go back to home
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(mTTs1!=null){
            mTTs1.stop();
            mTTs1.shutdown();
        }
        super.onDestroy();
    }


    private void speakNow() {
        String text=english.toString();
        mTTs1.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
}
