package headhunt.schemas.classes;

import headhunt.schemas.ScraperSchema;
import lombok.Getter;
import lombok.Setter;

public class VimeoUsersScraper extends ScraperSchema {

	@Getter @Setter private int page = 1;
	@Getter @Setter private String query;

	public VimeoUsersScraper(){
		super(null,null);
	}

	public VimeoUsersScraper(String name, String token, String query){
		super(name,token);
		this.query = query;
	}

}
