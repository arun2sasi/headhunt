package headhunt.schemas;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import headhunt.app.AppModel;

import java.util.List;

public class Schema {

    public void save(){
        AppModel.getDb().save(this);
    }

    public static List<?> query(String queryString){
        System.out.println(queryString);
        return AppModel.getDb().query(new OSQLSynchQuery<Schema>(queryString));
    }

}
