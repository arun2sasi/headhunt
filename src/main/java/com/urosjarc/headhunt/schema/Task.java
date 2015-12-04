package com.urosjarc.headhunt.schema;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@DatabaseTable(tableName = "tasks")
public class Task extends Table {

    public Task() {}
    public static Dao<Task,Integer> table;

	@DatabaseField
	@Getter @Setter private String header;

    @DatabaseField
    @Getter @Setter private String location;

    @DatabaseField
    @Getter @Setter private int importance;

    @DatabaseField
    @Getter @Setter private String details;

    @DatabaseField
    @Getter @Setter private int projectId;

    @DatabaseField
    @Getter @Setter private String ecac;

    @DatabaseField
    @Getter @Setter private String EcacIntensity;

    @DatabaseField
    @Getter @Setter private int notifierId;
}

