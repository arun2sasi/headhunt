package headhunt.schemas;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by urosjarc on 23.1.2016.
 */
public class ScraperSchema extends Schema {

	@Getter @Setter private String name;
	@Getter @Setter private String token;

	public ScraperSchema(String name,String token){

		this.name = name;
		this.token = token;

	}
}
