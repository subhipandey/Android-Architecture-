

package com.subhipandey.android.loveletter.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Letter(@PrimaryKey val uid: Long = Date().time) : Parcelable {

  constructor(title: String = "", description: String = "", ps: String = "") : this() {
    this.title = title
    this.description = description
    this.ps = ps
  }

  @ColumnInfo(name = "title")
  var title: String = ""

  @ColumnInfo(name = "description")
  var description: String = ""

  @ColumnInfo(name = "ps")
  var ps: String = ""

  @ColumnInfo(name = "to")
  var to: String? = null

  @ColumnInfo(name = "from")
  var from: String? = null

  @ColumnInfo(name = "fromName")
  var fromName: String? = null

  @ColumnInfo(name = "saved_at")
  var savedAt: Long? = null

  @ColumnInfo(name = "sent_at")
  var sentAt: Long? = null

  @ColumnInfo(name = "received_at")
  var receivedAt: Long? = null

  constructor(parcel: Parcel) : this(parcel.readLong()) {
    title = parcel.readString()!!
    description = parcel.readString()!!
    ps = parcel.readString()!!
    to = parcel.readString()
    from = parcel.readString()
    fromName = parcel.readString()
    savedAt = parcel.readValue(Long::class.java.classLoader) as? Long
    sentAt = parcel.readValue(Long::class.java.classLoader) as? Long
    receivedAt = parcel.readValue(Long::class.java.classLoader) as? Long
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeLong(uid)
    parcel.writeString(title)
    parcel.writeString(description)
    parcel.writeString(ps)
    parcel.writeString(to)
    parcel.writeString(from)
    parcel.writeString(fromName)
    parcel.writeValue(savedAt)
    parcel.writeValue(sentAt)
    parcel.writeValue(receivedAt)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Letter> {
    override fun createFromParcel(parcel: Parcel): Letter {
      return Letter(parcel)
    }

    override fun newArray(size: Int): Array<Letter?> {
      return arrayOfNulls(size)
    }
  }

}
