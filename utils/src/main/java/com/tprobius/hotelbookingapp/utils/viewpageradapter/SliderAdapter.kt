package com.tprobius.hotelbookingapp.utils.viewpageradapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tprobius.hotelbookingapp.utils.R
import java.nio.charset.Charset
import java.security.MessageDigest

class SliderAdapter : ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val hold = holder as ViewHolder
        hold.onBind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        fun onBind(image: String) {
            Glide.with(imageView)
                .load(image)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_error)
                .transform(MultiTransformation(FillSpace(), RoundedCorners(15)))
                .into(imageView)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        )
    }
}

class FillSpace : BitmapTransformation() {
    public override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return if (toTransform.width == outWidth && toTransform.height == outHeight) {
            toTransform
        } else Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, true)
    }

    override fun equals(other: Any?): Boolean {
        return other is FillSpace
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private const val ID = ""
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }
}