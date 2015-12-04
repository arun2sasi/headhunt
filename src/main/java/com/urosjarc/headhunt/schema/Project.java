package com.urosjarc.headhunt.schema;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@DatabaseTable(tableName = "projects")
public class Project extends Table {
    public Project() {}
    public static Dao<Project,Integer> table;

    @DatabaseField
    @Getter @Setter private String name;
}
