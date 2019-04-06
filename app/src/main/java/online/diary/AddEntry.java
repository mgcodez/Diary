package online.diary;


import com.google.firebase.database.IgnoreExtraProperties;

/**
 */
@IgnoreExtraProperties
public class AddEntry {
    private String mATitle;
    private String mBDescription;
    private String mCDate;
    private String mDUser;
    private String mETags;
    private String mFId;

    public AddEntry(){
        //this constructor is required
    }

    public AddEntry(String mATitle, String mBDescription, String mCDate,String mDUser,String mETags,String mFId) {
        this.mATitle = mATitle;
        this.mBDescription = mBDescription;
        this.mCDate = mCDate;
        this.mDUser=mDUser;
        this.mETags=mETags;
        this.mFId=mFId;
    }

    public String getmATitle() {return mATitle;}

    public String getmBDescription() {
        return mBDescription;
    }

    public String getmCDate() {return mCDate;  }
    public String getmDUser() {
        return mDUser;
    }
    public String getmETags() {
        return mETags;
    }
    public String getmFId() {
        return mFId;
    }

}