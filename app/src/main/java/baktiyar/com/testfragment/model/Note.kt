package baktiyar.com.testfragment.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by admin on 20.04.2018.
 */
class Note : Parcelable {
    var id = 0
    var title: String? = null
    var description: String? = null
    var doDate: String? = null
    var doTime: String? = null

    constructor()
    constructor(id: Int, title: String?, description: String?, doDate: String?, doTime: String?) {
        this.id = id
        this.title = title
        this.description = description
        this.doDate = doDate
        this.doTime = doTime
    }

    constructor(title: String?, description: String?, doDate: String?, doTime: String?) {
        this.title = title
        this.description = description
        this.doDate = doDate
        this.doTime = doTime
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        title = `in`.readString()
        description = `in`.readString()
        doDate = `in`.readString()
        doTime = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeString(description)
        dest.writeString(doDate)
        dest.writeString(doTime)
    }

    companion object {
        const val TABLE_NAME = "notes"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DO_DATE = "do_date"
        const val COLUMN_DO_TIME = "do_time"
        const val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DO_TIME + " TEXT,"
                + COLUMN_DO_DATE + " TEXT"
                + ")")
        @JvmField
        val CREATOR: Parcelable.Creator<Note?> = object : Parcelable.Creator<Note?> {
            override fun createFromParcel(`in`: Parcel): Note {
                return Note(`in`)
            }

            override fun newArray(size: Int): Array<Note?> {
                return arrayOfNulls(size)
            }
        }
    }
}