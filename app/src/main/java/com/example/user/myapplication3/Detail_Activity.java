package com.example.user.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static java.util.Locale.JAPANESE;

public class Detail_Activity extends AppCompatActivity {
    ImageView speak;
    String kana;
    private TextToSpeech mTTs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_detail);

//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TextView word=(TextView)findViewById(R.id.word);
        TextView partspeech=(TextView)findViewById(R.id.partspeech);
        TextView def1=(TextView)findViewById(R.id.def1);
        speak=(ImageView) findViewById(R.id.button_speak);




        Intent intent=getIntent();

        if(intent.getExtras()!=null){
            String english=intent.getStringExtra("english");
            String kanji=intent.getStringExtra("kanji");
            kana=intent.getStringExtra("kana");
            String romaji=intent.getStringExtra("romaji");
            String part_of_speech=intent.getStringExtra("part_of_speech");

           // tv.setText(english+"\n"+kanji+"\n"+kana+"\n"+romaji+"\n"+part_of_speech);
            word.setText(english);
            partspeech.setText(part_of_speech);
            def1.setText(kanji+"\n"+kana+"\n"+romaji);
            mTTs=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status==TextToSpeech.SUCCESS){
                int result;
                        result = mTTs.setLanguage(JAPANESE);
                        if(result == TextToSpeech.LANG_MISSING_DATA || result== TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTS","Lang not supported");



                }
                else{
                    speak.setEnabled(true);
                }
                    }
                    else{
                        Log.e("TTS","Initialization failed");

                    }
                }
            });
            speak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    speak();
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
        if(mTTs!=null){
            mTTs.stop();
            mTTs.shutdown();
        }
        super.onDestroy();
    }

    private void speak(){
        String text=kana.toString();
        mTTs.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }
}
