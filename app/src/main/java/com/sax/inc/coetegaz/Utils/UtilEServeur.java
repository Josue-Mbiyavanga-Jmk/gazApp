package com.sax.inc.coetegaz.Utils;

import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.Memory.Keys;
import com.sax.inc.coetegaz.Memory.Preferences;

/**
 * Created by GoservicesPc on 07/06/2018.
 */

public class UtilEServeur {

    public static EServeur getServeur()
    {

        // parametreConnexion : HOST_Name
        // parametreConnexion : PORT

        EServeur eServeur=new EServeur();

        String conf = Preferences.get(Keys.PreferencesKeys.CONFIG_IP);
        if(conf!=null)
        {
            if(conf.equals(""))
            {
                eServeur.setHOSTNAME(Preferences.get(Keys.PreferencesKeys.HOSTNAME));
                eServeur.setPORT(Preferences.get(Keys.PreferencesKeys.PORT));
            }
            else
            {
                eServeur.setHOSTNAME(Preferences.get(Keys.PreferencesKeys.CONFIG_IP));
                eServeur.setPORT(Preferences.get(Keys.PreferencesKeys.CONFIG_PORT));
            }

        }
        else
        {
            eServeur.setHOSTNAME(Preferences.get(Keys.PreferencesKeys.HOSTNAME));
            eServeur.setPORT(Preferences.get(Keys.PreferencesKeys.PORT));
        }

        if(eServeur.getHOSTNAME()==null)
        {
            return  null;
        }
        else
        {
            return  eServeur;
        }


    }


}
