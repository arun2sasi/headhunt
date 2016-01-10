package headhunt.schemas.records;

import lombok.Getter;
import lombok.Setter;

public class Portrait {

    @Getter @Setter private long height;
    @Getter @Setter private long width;
    @Getter @Setter private String link;

    public Portrait(){}

    public Portrait(String link, long height, long width) {
        this.height = height;
        this.width = width;
        this.link = link;
    }
}
