package com.urosjarc.headhunt.schemas;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by urosjarc on 12/5/15.
 */
public class TwitterUser {

    @Setter @Getter private String name;
    @Setter @Getter private String surname;

    public TwitterUser() {
    }
}
