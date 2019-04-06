package online.diary;


import com.google.firebase.database.IgnoreExtraProperties;

/**
 */
@IgnoreExtraProperties
public class CalendarNav {
    private String mATitle;
    private String mDUser;
    private String mFId;

    public CalendarNav(){
        //this constructor is required
    }

    public CalendarNav(String mATitle,String mDUser,String mFId) {
        this.mATitle = mATitle;
        this.mDUser=mDUser;
        this.mFId=mFId;
    }

    public String getmATitle() {return mATitle;}
    public String getmDUser() {return mDUser;}
    public String getmFId() {return mFId;}

}