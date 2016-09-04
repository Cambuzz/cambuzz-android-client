package com.cambuzz.internetcabin.cambuzzvitcc.data;

public class FeedItem {

    private int id;


    private String name, buzz_title, buzz_content, s_time, e_time, timeStamp, image, profilePic;

    public FeedItem()
    {
    }

    public FeedItem(int id, String name, String status)
    {
        super();
        this.id = id;
        this.name = name;
        //     this.status = status;
    }




    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getBuzz_title() {
        return buzz_title;
    }
    public void setBuzz_title(String buzz_title) {
        this.buzz_title = buzz_title;
    }


    public String getBuzz_content() {
        return buzz_content;
    }
    public void setBuzz_content(String buzz_content) {
        this.buzz_content = buzz_content;
    }

    public String getS_time() {
        return s_time;
    }
    public void setS_time(String s_time) {
        this.s_time = s_time;
    }

    public String getE_time() {
        return e_time;
    }
    public void setE_time(String e_time) {
        this.e_time = e_time;
    }


    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }



    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }




}