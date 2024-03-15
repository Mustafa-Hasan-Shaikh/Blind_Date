package com.dating.blinddate.Model;

public class StoryModel {
    String profile,name,time,story,like,comment,share,save,react,description;

    public StoryModel(String profile, String name, String time, String story, String like, String comment, String share, String save, String react, String description) {
        this.profile = profile;
        this.name = name;
        this.time = time;
        this.story = story;
        this.like = like;
        this.comment = comment;
        this.share = share;
        this.save = save;
        this.react = react;
        this.description = description;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getReact() {
        return react;
    }

    public void setReact(String react) {
        this.react = react;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
