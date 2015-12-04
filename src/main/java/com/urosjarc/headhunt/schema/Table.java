package com.urosjarc.headhunt.schema;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

public class Table {

	@DatabaseField(generatedId = true)
	@Getter private int id;

}
