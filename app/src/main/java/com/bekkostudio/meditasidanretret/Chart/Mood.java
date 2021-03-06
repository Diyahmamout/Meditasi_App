package com.bekkostudio.meditasidanretret.Chart;

import java.io.Serializable;

public class Mood implements Serializable {
    public String date;
    public int moodValue;
    public int medicineValue;

    //Mood value constant
    public static final int SANGAT_TENANG = 4;
    public static final int TENANG = 3;
    public static final int TIDAK_TENANG = 2;
    public static final int SANGAT_TIDAK_TENANG = 1;

    //Medicine value constant
    public static final int MINUM_OBAT = 3;
    public static final int KURANGI_DOSIS = 2;
    public static final int TIDAK_MINUM_OBAT = 1;

    public static final int TIDAK_MENGISI = 0;

    public Mood(String date, int moodValue, int medicineValue ) {
        this.date = date;
        this.moodValue = moodValue;
        this.medicineValue = medicineValue;
    }

}
