package com.example.syl.androidcleanarchdemo.data;

/**
 * Created by shenyunlong on 4/20/15.
 */
public class User {

    private String mName;
    private String mEmail;
    private String mFollowers;
    private String mDescription;

    public User(String name, String email, String followers, String description) {
        this.mName = name;
        this.mEmail = email;
        this.mFollowers = followers;
        this.mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getFollowers() {
        return mFollowers;
    }

    public void setFollowers(String followers) {
        this.mFollowers = followers;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
