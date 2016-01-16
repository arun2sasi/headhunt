package headhunt.schemas.classes;

import headhunt.schemas.Schema;
import lombok.Getter;
import lombok.Setter;

public class VimeoUsersScraper extends Schema {

	@Getter @Setter private String name;
	@Getter @Setter private int page = 1;
	@Getter @Setter private String token;
	@Getter @Setter private String query;

	public VimeoUsersScraper(){}

	public VimeoUsersScraper(String name, String token, String query){
		this.name = name;
		this.token = token;
		this.query = query;
	}

}
