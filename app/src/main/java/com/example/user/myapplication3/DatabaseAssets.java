package com.example.user.myapplication3;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DatabaseAssets {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAssets instance;
    private String result_speaking;
    private String result_speaking_eng;
    Boolean ruleB = true;

Boolean flag=false;
    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    public DatabaseAssets(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

    }


    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAssets getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAssets(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
        Log.e("Open database", "Open");
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public String getStringspeaking(String engSpeaking) {

        Cursor cursor = database.rawQuery("SELECT kana_notation FROM dictionarytbl where english='" + engSpeaking + "'", null);
        String data = "";

        if (cursor.moveToFirst()) {
            do {


                data = cursor.getString(0);
                Log.d("database data", data);
            }
            while (cursor.moveToNext());
        }
        return data;

    }


    public String getStringSpeakingJp(String jpSpeaking) {
        Cursor cursor = database.rawQuery("SELECT english FROM dictionarytbl WHERE kana_notation='" + jpSpeaking + "'", null);
        String result_speaking_eng="";
        if (cursor.moveToFirst()) {
            do {
                result_speaking_eng = cursor.getString(0);
                Log.d("database data", result_speaking_eng);
            }
            while(cursor.moveToNext());
        }
        return result_speaking_eng;
    }

    public String rule_db(ArrayList<String> list) {
       // String da="";
        String kana="";

        ArrayList<Eng_To_Jp> ruleList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            String query = "select english,kana_notation,romaji,part_of_speech from dictionarytbl where english='" + list.get(i) + "'";
            Cursor cursor = database.rawQuery(query, null);



                Eng_To_Jp rule = new Eng_To_Jp();
                if (cursor!=null) {
                    cursor.moveToFirst();

                        String engw = cursor.getString(0);
                        rule.setEnglish(engw);
                        String kanaw = cursor.getString(1);
                        rule.setKana_notation(kanaw);
                        String romw = cursor.getString(2);
                        rule.setRomaji(romw);
                        String sp = cursor.getString(3);
                        rule.setPart_of_speech(sp);
                        Log.d("ruleList", engw + " " + kanaw + " " + romw + " " + sp);
                        ruleList.add(rule);


                    //while (cursor.moveToNext());
                }

            }



            String part = "";
            for (int j = 0; j < ruleList.size(); j++) {
                String part_of_speech = ruleList.get(j).getPart_of_speech();
                part += part_of_speech + " ";

            }
            Log.d("part of speech", part);
            String query_rule = "select japanese_rule from indirect_rules where english_rule='" + part.trim() + "'";
            Cursor cursor1 = database.rawQuery(query_rule, null);
            String jp_rule = "";
            String jp="";
            if (cursor1!=null) {
                cursor1.moveToFirst();
                    jp_rule = cursor1.getString(0);




            }
            Log.d("Jp rule", jp_rule);
            StringTokenizer st = new StringTokenizer(jp_rule);
            System.out.println("--------Split by space----------");
            ArrayList<String> jp_List = new ArrayList<>();
            while (st.hasMoreElements()) {
                String jp_ru = st.nextElement().toString();
                jp_List.add(jp_ru);
                Log.d("jprule", jp_ru);


            }

            //String kana = "";
        String ps="";
            label:
            for (int z = 0; z < jp_List.size(); z++) {
                for (int u =0; u < ruleList.size(); u++) {
                    if (ruleList.get(u).getPart_of_speech().equals(jp_List.get(z).trim() ))
                    {
                       // if(ruleList.get(u).getKana_notation().equals(jp_List.get(z).getK))
                       if(!kana.contains(ruleList.get(u).getKana_notation() )) {

                           //if( flag==false) {

                               ps += ruleList.get(u).getPart_of_speech();
                               kana += ruleList.get(u).getKana_notation();
                               //flag=true;
                           continue label;

                           }








                        }




                    }




            }
            Log.d("Kana", kana + "kana");


            return kana;
        }








   public ArrayList<Eng_To_Jp> getAll(){
        ArrayList<Eng_To_Jp> list=new ArrayList<Eng_To_Jp>();

       String query="select * from dictionarytbl";

       Cursor cursor=database.rawQuery(query,null);

       if(cursor.moveToFirst()){
            do{
                Eng_To_Jp eng_to_jp=new Eng_To_Jp();
                eng_to_jp.setId(cursor.getInt(0));
               eng_to_jp.setEnglish(cursor.getString(1));
               eng_to_jp.setKanji_notation(cursor.getString(2));
               eng_to_jp.setKana_notation(cursor.getString(3));
               eng_to_jp.setRomaji(cursor.getString(4));
               eng_to_jp.setPart_of_speech(cursor.getString(5));
               eng_to_jp.setJapan_audio(cursor.getString(6));
               eng_to_jp.setEnglish_audio(cursor.getString(7));

               list.add(eng_to_jp);
          }while(cursor.moveToNext());
       }
       return list;
   }

//    public String rule_db_direct(ArrayList<String> list1) {
//        ArrayList<Eng_To_Jp>rulelist_direct=new ArrayList<>();
//        for(int i=0;i<list1.size();i++) {
//            String ql = "select english,kana_notation,romaji,part_of_speech from dictionarytbl where english='" + list1.get(i) + "'";
//            Cursor c1=database.rawQuery(ql,null);
//            Eng_To_Jp rule_d=new Eng_To_Jp();
//            if(c1!=null){
//                c1.moveToFirst();
//                    String engdir=c1.getString(0);
//                    rule_d.setEnglish(engdir);
//                    String kanadir=c1.getString(1);
//                    rule_d.setKana_notation(kanadir);
//                    String romdir=c1.getString(2);
//                    rule_d.setRomaji(romdir);
//                    String spdir=c1.getString(3);
//                    rule_d.setPart_of_speech(spdir);
//                    Log.d("ruleListdirect",engdir+" "+kanadir+" "+romdir+" "+spdir+" ");
//                    rulelist_direct.add(rule_d);
//
//
//
//                //while ((c1.moveToNext()));
//            }
//        }
//        String partdir="";
//        for(int j=0;j<rulelist_direct.size();j++){
//            String part_of_speech_dir=rulelist_direct.get(j).getPart_of_speech();
//            partdir+=part_of_speech_dir+" ";
//
//        }
//        Log.d("part of speech direct",partdir);
//        String qu="select japaneseformal from speaking_rules where englishformal='"+partdir.trim()+"'";
//        Cursor cdir=database.rawQuery(qu,null);
//        String jp_rule_dir="";
//        if(cdir!=null){
//             cdir.moveToFirst();
//                jp_rule_dir=cdir.getString(0);
//
//
//
//        }
//        Log.d("Jp rule direct",jp_rule_dir);
//        StringTokenizer stdir=new StringTokenizer(jp_rule_dir);
//        System.out.println("----------Split by space---------");
//        ArrayList<String>jp_list_dir=new ArrayList<>();
//        while ((stdir.hasMoreElements())){
//            String jp_ru_dir=stdir.nextElement().toString();
//            jp_list_dir.add(jp_ru_dir);
//
//        }
//        String kana_dir="";
//        for(int z=0;z<jp_list_dir.size();z++){
//            for(int u=0;u<rulelist_direct.size();u++){
//                if(rulelist_direct.get(u).getPart_of_speech().equals(jp_list_dir.get(z).trim())){
//                    kana_dir+=rulelist_direct.get(u).getKana_notation();
//                }
//            }
//        }
//Log.d("Kanadir",kana_dir+"kanadir");
//        return kana_dir;
//    }

    public String rule_db_jp(ArrayList<String> listjp) {

        String eng = "";

        ArrayList<Eng_To_Jp> ruleListeng = new ArrayList<>();
        for (int i = 0; i < listjp.size(); i++) {

            String query = "select english,kana_notation,romaji,part_of_speech from dictionarytbl where kana_notation='" + listjp.get(i) + "'";
            Cursor cursor = database.rawQuery(query, null);


            Eng_To_Jp rule_jp = new Eng_To_Jp();
            if (cursor != null) {
                cursor.moveToFirst();

                String engw1 = cursor.getString(0);
                rule_jp.setEnglish(engw1);
                //check(engw1);
                String kanaw1 = cursor.getString(1);
                rule_jp.setKana_notation(kanaw1);
                String romw1 = cursor.getString(2);
                rule_jp.setRomaji(romw1);
                String sp1 = cursor.getString(3);
                rule_jp.setPart_of_speech(sp1);
                Log.d("ruleList", engw1 + " " + kanaw1 + " " + romw1 + " " + sp1);
                ruleListeng.add(rule_jp);


                //while (cursor.moveToNext());
            }

        }

        //String eng1 = "";
        //String rr="am";

//        for (int j = 0; j < .size(); j++) {
//
//            if(ruleListeng.get(j).getKana_notation()=="わたし"){
//
//
//
//                j++;
//                ruleListeng.get(j).setEnglish("am");
//                //String english=ruleListeng.get(j).getEnglish();
//                //eng1+=english;
//
//
//                break;
//
//            }

        String part1 = "";
        for(int j=0;j<ruleListeng.size();j++) {
            String part_of_speech1 = ruleListeng.get(j).getPart_of_speech();
            part1 += part_of_speech1 + " ";
        }

//Log.d("rulelist",ruleListeng.get(j).getEnglish());

        //Log.d("part of speech", part1);

        String query_rule = "select english_rule from indirect_rules where japanese_rule='" + part1.trim() + "'";
        Cursor cur = database.rawQuery(query_rule, null);
        String engrule = "";
        if (cur!=null) {
            cur.moveToFirst();
            engrule = cur.getString(0);


        }
        Log.d("eng rule", engrule);
        StringTokenizer st = new StringTokenizer(engrule);
        System.out.println("--------Split by space----------");
        ArrayList<String> eng_list = new ArrayList<>();
        while (st.hasMoreElements()) {
            String eng_ru = st.nextElement().toString();
            eng_list.add(eng_ru);
            Log.d("eng rule", eng_ru);


        }

        //String kana = "";
        label:
        for (int z = 0; z < eng_list.size(); z++) {
            for (int u = 0; u < ruleListeng.size(); u++) {
                if (ruleListeng.get(u).getPart_of_speech().equals(eng_list.get(z).trim())) {
                    if (!(eng.contains(ruleListeng.get(u).getEnglish()))) {
                        if(ruleListeng.get(u).getPart_of_speech().equals("iden")&&(eng.contains("i"))){
                            eng+="am ";
                            Log.d("IDEN equals----","am");
                            continue label;
                        }
                        else {
                            Log.d("RULELSITENG",ruleListeng.get(u).getEnglish());

                            eng += ruleListeng.get(u).getEnglish() + " ";
                            continue label;
                        }
                    }
                }
            }
        }
        Log.d("eng", eng + "eng");


        return eng;
    }

//    private String check(String engw1) {
//        if(engw1=="")
//        return ;
//    }

//    public String rule_db_direct_jp(ArrayList<String> listjpd) {
//        ArrayList<Eng_To_Jp>rulelist_direct_eng=new ArrayList<>();
//        for(int i=0;i<listjpd.size();i++) {
//            String ql = "select english,kana_notation,romaji,part_of_speech from dictionarytbl where kana_notation='" + listjpd.get(i) + "'";
//            Cursor c1=database.rawQuery(ql,null);
//            Eng_To_Jp rule_dir=new Eng_To_Jp();
//            if(c1!=null){
//                c1.moveToFirst();
//                    String engdir1=c1.getString(0);
//                    rule_dir.setEnglish(engdir1);
//                    String kanadir1=c1.getString(1);
//                    rule_dir.setKana_notation(kanadir1);
//                    String romdir1=c1.getString(2);
//                    rule_dir.setRomaji(romdir1);
//                    String spdir1=c1.getString(3);
//                    rule_dir.setPart_of_speech(spdir1);
//                    Log.d("ruleListdirect",engdir1+" "+kanadir1+" "+romdir1+" "+spdir1+" ");
//                    rulelist_direct_eng.add(rule_dir);
//
//
//                }
//
//            }
//
//        String partdireng="";
//        for(int j=0;j<rulelist_direct_eng.size();j++){
//            String part_of_speech_dir_eng=rulelist_direct_eng.get(j).getPart_of_speech();
//            partdireng+=part_of_speech_dir_eng+" ";
//
//        }
//        Log.d("part of speech direct ",partdireng);
//        String qu="select englishformal from speaking_rules where japaneseformal='"+partdireng.trim()+"'";
//        Cursor cdireng=database.rawQuery(qu,null);
//        String eng_rule_dir="";
//        if(cdireng!=null){
//            cdireng.moveToFirst();
//            eng_rule_dir=cdireng.getString(0);
//
//
//
//        }
//        Log.d("eng  rule direct",eng_rule_dir);
//        StringTokenizer stdireng=new StringTokenizer(eng_rule_dir);
//        System.out.println("----------Split by space---------");
//        ArrayList<String>eng_list_dir=new ArrayList<>();
//        while ((stdireng.hasMoreElements())){
//            String eng_ru_dir=stdireng.nextElement().toString();
//            eng_list_dir.add(eng_ru_dir);
//
//        }
//        String eng_dir="";
//        for(int z=0;z<eng_list_dir.size();z++){
//            for(int u=0;u<rulelist_direct_eng.size();u++){
//                if(rulelist_direct_eng.get(u).getPart_of_speech().equals(eng_list_dir.get(z).trim())){
//                    eng_dir+=rulelist_direct_eng.get(u).getKana_notation();
//                }
//            }
//        }
//        Log.d("ENg dir",eng_dir+"eng_dir");
//        return eng_dir;
//    }
//

//   public String getResult_speaking_Jp(){
//
//        return ;
//   }

//
//    public List<Question> getAllQuestions() {
//        List<Question> quesList = new ArrayList<Question>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM chapter";
//
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Question quest = new Question();
//                quest.setID(cursor.getInt(0));
//                quest.setQUESTION(cursor.getString(1));
//                quest.setANSWER(cursor.getString(2));
//                quest.setOPTA(cursor.getString(3));
//                quest.setOPTB(cursor.getString(4));
//                quest.setOPTC(cursor.getString(5));
//                quest.setOPTD(cursor.getString(6));
//                quesList.add(quest);
//            } while (cursor.moveToNext());
//        }
//        // return quest list
//        return quesList;
//    }
//
//    public int getNoOfQuestion() {
//        String query = "select count(*) from chapter";
//        Cursor cursor = database.rawQuery(query, null);
//        cursor.moveToFirst();
//        int num = cursor.getInt(0);
//        return num;
//    }
}

