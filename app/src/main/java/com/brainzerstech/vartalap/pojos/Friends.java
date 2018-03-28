package com.brainzerstech.vartalap.pojos;

import java.util.ArrayList;

/**
 * Created by ebsadmin on 27/03/18.
 */

public class Friends {
    String vFriendToken;
    String  vFriendName;
    String vFriendStatus;
    String vFriendLocation;


    public Friends(String vFriendToken, String vFriendName, String vFriendStatus, String vFriendLocation){
        this.vFriendToken = vFriendToken;
        this.vFriendName  = vFriendName;
        this.vFriendStatus = vFriendStatus;
        this.vFriendLocation  = vFriendLocation;

    }

    public String getvFriendToken() {
        return vFriendToken;
    }

    public String getvFriendName() {
        return vFriendName;
    }

    public String getvFriendStatus() {
        return vFriendStatus;
    }

    public String getvFriendLocation() {
        return vFriendLocation;
    }
}
