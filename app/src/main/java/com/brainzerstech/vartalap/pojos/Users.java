package com.brainzerstech.vartalap.pojos;

import java.util.ArrayList;

/**
 * Created by ebsadmin on 27/03/18.
 */

public class Users {
    String vUserToken;
    String  vUserName;
    String vUserStatus;
    String vUserLocation;
    ArrayList<Friends> vFriendsList;
    Friends friends;

    public Users(){}
    public Users(String vUserId, String vUserName, String vUserStatus, String vUserLocation, ArrayList<Friends> vFriendsList, Friends friends){
        this.vUserToken = vUserId;
        this.vUserName  = vUserName;
        this.vUserStatus = vUserStatus;
        this.vUserLocation  = vUserLocation;
        this.vFriendsList = vFriendsList;
    }

    public String getvUserId() {
        return vUserToken;
    }

    public String getvUserName() {
        return vUserName;
    }

    public String getvUserStatus() {
        return vUserStatus;
    }

    public String getvUserLocation() {
        return vUserLocation;
    }
    public ArrayList<Friends> getvFriendsList() {
        return vFriendsList;
    }

    public Friends getFriends() {
        return friends;
    }
}
