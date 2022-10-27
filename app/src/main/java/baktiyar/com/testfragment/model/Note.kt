package baktiyar.com.testfragment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 20.04.2018.
 */

public class Note implements Parcelable {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DO_DATE = "do_date";
    public static final String COLUMN_DO_TIME = "do_time";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_DO_TIME+ " TEXT,"
                    + COLUMN_DO_DATE + " TEXT"
                    + ")";

    private int id;
    private String title;
    private String description;
    private String doDate;
    private String doTime;

    public String getDoTime() {
        return doTime;
    }

    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }

    public Note() {
    }

    public Note(int id, String title, String description, String doDate, String doTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.doDate = doDate;
        this.doTime = doTime;
    }

    public Note(String title, String description, String doDate, String doTime) {
        this.title = title;
        this.description = description;
        this.doDate = doDate;
        this.doTime = doTime;
    }

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        doDate = in.readString();
        doTime = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoDate() {
        return doDate;
    }

    public void setDoDate(String doDate) {
        this.doDate = doDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(doDate);
        dest.writeString(doTime);
    }
}
