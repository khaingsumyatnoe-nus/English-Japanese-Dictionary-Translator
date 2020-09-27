package com.example.user.myapplication3;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class SpeakingActivity extends AppCompatActivity {
    EditText speakingenglish;
    EditText resultjp;
    Button translatespeaking;
    ImageView button_speaking;
    DatabaseAssets asset;
    String speakingeng;
    TextToSpeech mTTs2;
    ImageView clear_all_eng;
    String r;

    ImageView copy;
    //Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //asset = DatabaseAssets.getInstance(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking);
        asset = DatabaseAssets.getInstance(this);

        asset.open();
        speakingenglish = (EditText) findViewById(R.id.speakingenglish);
        resultjp = (EditText) findViewById(R.id.resultjp);
        translatespeaking = (Button) findViewById(R.id.translatespeaking);
        button_speaking = (ImageView) findViewById(R.id.button_speaking);
        clear_all_eng = (ImageView) findViewById(R.id.clear_all_eng);
        copy = (ImageView) findViewById(R.id.copy);
        translatespeaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakingeng = speakingenglish.getText().toString().toLowerCase();
                try {


                    //asset = new DatabaseAssets(getApplicationContext());

                    //asset.open();
                    r = asset.getStringspeaking(speakingeng);
                    if (r != "") {

                        resultjp.setText(r);
                    } else {

                        tok(speakingeng);
                    }
                    // asset.close();
                } catch (IndexOutOfBoundsException error) {
                    error.printStackTrace();
                    AlertDialog alertDialog = new AlertDialog.Builder(SpeakingActivity.this).create();
                    alertDialog.setTitle("Not Found");
                    alertDialog.setMessage("Click OK to start again");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            speakingenglish.setText("");
                        }
                    });
                    alertDialog.show();
                }

            }
        });
        clear_all_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakingenglish.setText("");
                resultjp.setText("");
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(resultjp.getText().toString());
                toast();
            }
        });
        mTTs2 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs2.setLanguage(Locale.JAPANESE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "language not supported");

                    } else {
                        button_speaking.setEnabled(true);

                    }
                } else {
                    Log.e("TTS", "initialization failed");
                }

            }
        });
        button_speaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakJapan();
            }
        });
    }

    private void tok(String word) {

            StringTokenizer st = new StringTokenizer(word, " ");
            System.out.println("------split by space--------");
            ArrayList<String> list = new ArrayList<>();
            label:
            while (st.hasMoreElements()) {
                String ele = st.nextElement().toString();
                if (ele.equals("a")) {
                   continue label;
                }
                else {
                   // String ele = st.nextElement().toString();
                    list.add(ele);
                    Log.d("token", ele);

                }
            }


            r = asset.rule_db(list);
//        if(r.equals("")){
//            r=asset.rule_db_direct(list);
//            resultjp.setText(r);
//        }
            Log.d("Main Kana", r);
            resultjp.setText(r);

        }


        private void toast () {
            Toast.makeText(this, "copied to Clipboard", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onDestroy () {
            if (mTTs2 != null) {
                mTTs2.stop();
                mTTs2.shutdown();
            }
            super.onDestroy();
        }

        private void speakJapan () {
            String text = r.toString();
            mTTs2.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        }
    }
