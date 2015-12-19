package com.urosjarc.headhunt.schemas;

import com.sun.jna.platform.win32.ObjBase;
import com.urosjarc.headhunt.AppModel;
import org.json.simple.JSONObject;

public class Schema {

    public void save(){
        AppModel.getDb().save(this);
    }


}
