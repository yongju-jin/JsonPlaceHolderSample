package yongju.riiidhw.model

import android.os.Parcel
import android.os.Parcelable

data class TypiCodeModel(
    val userId: Long = -1,
    val id: Long = -1,
    val title: String = "",
    val body: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readLong(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(userId)
        writeLong(id)
        writeString(title)
        writeString(body)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TypiCodeModel> =
            object : Parcelable.Creator<TypiCodeModel> {
                override fun createFromParcel(source: Parcel): TypiCodeModel = TypiCodeModel(source)
                override fun newArray(size: Int): Array<TypiCodeModel?> = arrayOfNulls(size)
            }
    }
}