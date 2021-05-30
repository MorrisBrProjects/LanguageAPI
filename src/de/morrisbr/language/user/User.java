package de.morrisbr.language.user;

import de.morrisbr.language.user.propety.UserProfile;

public class User {

    private UserProfile profile;
    private String uuid;

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
