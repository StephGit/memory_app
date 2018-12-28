package ch.stephgit.memory.persistence

import android.arch.persistence.room.TypeConverter
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.PNG
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromBitmapToByteArray(bitmap: Bitmap?) : ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(PNG, 100, stream)
        return stream.toByteArray()
    }

    @TypeConverter
    fun fromByteArrayToBitmap(bytes: ByteArray?) : Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
    }
}