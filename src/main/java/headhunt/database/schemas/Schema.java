package headhunt.database.schemas;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import headhunt.database.Db;

import java.util.List;

public class Schema {

	public void save(){
        Db.getConnection().save(this);
    }

    public static List<?> query(String queryString){
        return Db.getConnection().query(new OSQLSynchQuery<Schema>(queryString));
    }

}
