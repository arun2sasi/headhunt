package com.urosjarc.headhunt.schemas;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.urosjarc.headhunt.app.AppModel;

import java.util.List;

public class Schema {

    public void save(){
        AppModel.getDb().save(this);
    }

    public static List<TwitterUser> query(String queryString){
        System.out.println(queryString);
        return AppModel.getDb().query(new OSQLSynchQuery<Schema>(queryString));
    }

}
