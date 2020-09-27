package com.example.user.myapplication3;

public class Eng_To_Jp {
    private Integer id;
    private String english;
    private String kanji_notation;
    private String kana_notation;
    private String romaji;
    private String japan_audio;
    private String english_audio;
    private String part_of_speech;

    public Integer getId() {
        return id;
    }

    public String getEnglish() {
        return english;
    }

    public String getKanji_notation() {
        return kanji_notation;
    }

    public String getKana_notation() {
        return kana_notation;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setKanji_notation(String kanji_notation) {
        this.kanji_notation = kanji_notation;
    }

    public void setKana_notation(String kana_notation) {
        this.kana_notation = kana_notation;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public void setJapan_audio(String japan_audio) {
        this.japan_audio = japan_audio;
    }

    public void setEnglish_audio(String english_audio) {
        this.english_audio = english_audio;
    }

    public String getJapan_audio() {
        return japan_audio;
    }

    public String getEnglish_audio() {
        return english_audio;
    }
    public String getPart_of_speech(){
        return part_of_speech;
    }
    public void setPart_of_speech(String part_of_speech){
        this.part_of_speech=part_of_speech;
    }
}
