package com.sax.inc.coetegaz.Utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

public class UtilsMaps {

    public static float getDiffDistance(LatLng start, LatLng end)
    {

        float[] results = new float[1];
        Location.distanceBetween(start.latitude, start.longitude,
                end.latitude, end.longitude, results);

        return results[0];
    }

    public static String getDistance(float v)
    {
        double rsl=v/1000;

        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);

        if(rsl<1)
        {
            return f.format(v)+" m";
        }
        else
        {
            return f.format(rsl)+" km";
        }
    }

    /**
     *
     * @param d
     * @param moyen : Moyen de transport:  1 = Véhicue, 2 = pieds, 3=  Motos
     * @return
     */
    public static String getTime(float d,int moyen)
    {
        switch (moyen)
        {
            case 1:
                return Math.ceil((d*60)/20000)+" min";
            case 2:
                return Math.ceil(((d*2)/1.3)/100)+" min";// 65 cm par pas en moyen: à revoir
            case 3:
                return Math.ceil((d*60)/40000)+" min";
            default:
                return "";
        }
    }
}
