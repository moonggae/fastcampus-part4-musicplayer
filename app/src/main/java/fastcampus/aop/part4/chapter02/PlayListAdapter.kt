package fastcampus.aop.part4.chapter02

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fastcampus.aop.part4.chapter02.databinding.ItemMusicBinding

class PlayListAdapter(private val callback: (MusicModel) -> Unit) :
    ListAdapter<MusicModel, PlayListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MusicModel) {
            binding.itemTrackTextView.text = item.track
            binding.itemArtistTextView.text = item.artist

            Glide.with(binding.itemCoverImageView)
                .load(item.coverUrl)
                .into(binding.itemCoverImageView)

            if (item.isPlaying) {
                binding.root.setBackgroundColor(Color.GRAY)
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }

            binding.root.setOnClickListener {
                callback(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].also { musicModel ->
            holder.bind(musicModel)
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MusicModel>() {
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}