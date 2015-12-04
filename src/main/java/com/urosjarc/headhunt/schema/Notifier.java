package com.urosjarc.headhunt.schema;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@DatabaseTable(tableName = "notifiers")
public class Notifier extends Table {
    public Notifier() {}
    public static Dao<Notifier,Integer> table;

    @DatabaseField
    @Getter @Setter private String name;

    @DatabaseField
    @Getter @Setter private String cron;

    //TODO: Set this date to Date type!
    @DatabaseField
    @Getter @Setter private String date;

    //TODO: Set this date to Date type!
    @DatabaseField
    @Getter @Setter private String cronStop;
}
