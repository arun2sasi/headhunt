package headhunt.database.records;

import lombok.Getter;
import lombok.Setter;

public class Website {

    @Getter @Setter private String link;
    @Getter @Setter private String name;
    @Getter @Setter private String description;

    public Website(){}

    public Website(String link, String name, String description) {
        this.link = link;
        this.name = name;
        this.description = description;
    }
}
