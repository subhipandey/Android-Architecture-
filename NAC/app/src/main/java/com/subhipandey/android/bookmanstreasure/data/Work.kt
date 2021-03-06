

package com.subhipandey.android.bookmanstreasure.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.subhipandey.android.bookmanstreasure.db.Converters
import java.io.Serializable

/**
 * A collection of all editions of a book.
 */
@Entity(tableName = "Work")
@TypeConverters(Converters::class)
data class Work(

    @PrimaryKey
    @SerializedName("key")
    val id: String,

    @ColumnInfo(name = "cover_id")
    @SerializedName("cover_i")
    val coverId: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "subtitle")
    @SerializedName("subtitle")
    val subtitle: String?,

    @ColumnInfo(name = "author_name")
    @SerializedName("author_name")
    val authorName: List<String>,

    @ColumnInfo(name = "author_key")
    @SerializedName("author_key")
    val authorKey: List<String>,

    @ColumnInfo(name = "isbn")
    @SerializedName("isbn")
    val editionIsbns: List<String>
) : Serializable
