package com.urosjarc.headhunt.schemas;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.urosjarc.headhunt.AppModel;

import java.util.List;

public class Schema {

    public void save(){
        AppModel.getDb().save(this);
    }

    public static List<Schema> query(String queryString){
        return AppModel.getDb().query(new OSQLSynchQuery<Schema>(queryString));
    }

}
