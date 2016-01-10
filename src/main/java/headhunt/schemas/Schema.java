package headhunt.schemas;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import headhunt.app.AppModel;
import headhunt.schemas.twitter.TwitterUser;

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
