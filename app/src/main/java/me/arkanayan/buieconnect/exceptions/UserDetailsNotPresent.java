package me.arkanayan.buieconnect.exceptions;

/**
 * Created by arka on 4/10/16.
 */
public class UserDetailsNotPresent extends Exception {


    public UserDetailsNotPresent() {
        super("User details not present in preferences");
    }
}
