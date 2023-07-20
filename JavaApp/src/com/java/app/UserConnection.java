package com.java.app;
import java.util.UUID;
public class UserConnection {
    private UUID uuid;
    private String firstname;
    private String connextionDatetime;
    public UserConnection(UUID uuid, String firstname, String connextionDatetime) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.connextionDatetime = connextionDatetime;
    }
    public UUID getUuid() {
        return uuid;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getConnextionDatetime() {
        return connextionDatetime;
    }
    @Override
    public String toString(){
        return this.uuid + " - "
                + this.firstname + " - "
                + this.connextionDatetime;
    }
}
