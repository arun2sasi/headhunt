package com.urosjarc.headhunt.schemas;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.urosjarc.headhunt.AppModel;

public class TwitterUser {

    @Getter @Setter private String uri;
    @Getter @Setter private String name;
    @Getter @Setter private String link;
    @Getter @Setter private String location;
    @Getter @Setter private String bio;
    @Getter @Setter private Date createdTime = new Date();
    @Getter @Setter private String account;
    @Getter @Setter private List<String> portraits;
    @Getter @Setter private List<String> websites;
    @Getter @Setter private Map<String,Integer> statistics;

    public TwitterUser() {
    }

    static public ArrayList<TwitterUser> getAll(){
        return AppModel.getDb().query(new OSQLSynchQuery<TwitterUser>("select * from TwitterUser"));
    }

}
