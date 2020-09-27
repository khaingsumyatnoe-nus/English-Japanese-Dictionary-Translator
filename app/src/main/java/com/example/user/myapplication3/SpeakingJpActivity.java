package com.example.user.myapplication3;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class SpeakingJpActivity extends AppCompatActivity {
    EditText speakingjapanese;
    Button translatespeakingjp;
    EditText result;
    ImageView clear_all_jp;
    ImageView copyeng;
    ImageView button_speaking_eng;
    String speakingjp;
    DatabaseAssets asset1;
    String rr;
    TextToSpeech mTTs3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking_jp);
        asset1 =  DatabaseAssets.getInstance(this);

        asset1.open();
        speakingjapanese=(EditText)findViewById(R.id.speakingjapanese);
        translatespeakingjp=(Button) findViewById(R.id.translatespeakingjp);
        result=(EditText)findViewById(R.id.result);
        clear_all_jp=(ImageView)findViewById(R.id.clear_all_jp);
        copyeng=(ImageView)findViewById(R.id.copyeng);
        button_speaking_eng=(ImageView)findViewById((R.id.button_speaking_eng));

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        translatespeakingjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakingjp=speakingjapanese.getText().toString();
                try {


//                    asset1 = new DatabaseAssets(getApplicationContext());
//
//                    asset1.open();
                    rr = asset1.getStringSpeakingJp(speakingjp);
                    if(rr!="") {

                        result.setText(rr);
                    }
                    else {
                        tokjp(speakingjp);
                    }
                    //asset1.close();
                }
                catch (IndexOutOfBoundsException error){
                    error.printStackTrace();
                    AlertDialog alertDialog=new AlertDialog.Builder(SpeakingJpActivity.this).create();
                    alertDialog.setTitle("Not Found");
                    alertDialog.setMessage("Click OK to start again");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            speakingjapanese.setText("");
                        }
                    });
                    alertDialog.show();
                }

            }


        });
        clear_all_jp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakingjapanese.setText("");
                result.setText("");
            }
        });
        copyeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setText(result.getText().toString());
                toast1();
            }
        });

mTTs3=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
    @Override
    public void onInit(int i) {
        if(i==TextToSpeech.SUCCESS){
            int result=mTTs3.setLanguage(Locale.ENGLISH);
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","language not supported");

            }
            else{
                button_speaking_eng.setEnabled(true);

            }
        }
        else{
            Log.e("TTS","initialization failed");
        }

    }
});
        button_speaking_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakEnglish();
            }
        });
    }

    private void tokjp(String wordjp) {
        StringTokenizer stjp=new StringTokenizer(wordjp," ");
        System.out.println("------split by space--------");
        ArrayList<String>listjp=new ArrayList<>();
        while (stjp.hasMoreElements()){
            String ele=stjp.nextElement().toString();
            listjp.add(ele);
            Log.d("token",ele);
        }

        rr=asset1.rule_db_jp(listjp);
//        if(rr.equals("")){
//            rr=asset1.rule_db_direct_jp(listjp);
//            result.setText(rr);
//        }
        Log.d("Main Kana",rr);
        result.setText(rr);

    }

    @Override
    protected void onDestroy() {
        if(mTTs3!=null){
            mTTs3.stop();
            mTTs3.shutdown();
        }
        super.onDestroy();
    }

    private void speakEnglish() {
        String text=rr.toString();
        mTTs3.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }

    private void toast1() {
        Toast.makeText(this,"copied to Clipboard",Toast.LENGTH_SHORT).show();
    }


}
