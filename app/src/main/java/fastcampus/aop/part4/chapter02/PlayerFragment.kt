package fastcampus.aop.part4.chapter02

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import fastcampus.aop.part4.chapter02.databinding.FragmentPlayerBinding
import fastcampus.aop.part4.chapter02.service.MusicDto
import fastcampus.aop.part4.chapter02.service.MusicService
import fastcampus.aop.part4.chapter02.service.mapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val TAG = "로그"

    private var binding: FragmentPlayerBinding? = null
    private var isWatchingPlayListView = true
    private var player: SimpleExoPlayer? = null
    private lateinit var playListAdapter: PlayListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding

        initPlayView(fragmentPlayerBinding)
        initPlayListButton(fragmentPlayerBinding)
        initPlayControlButtons(fragmentPlayerBinding)
        initRecyclerView(fragmentPlayerBinding)

        getVideoListFromServer()
    }

    private fun initPlayControlButtons(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playControlImageView.setOnClickListener {
            val player = this.player ?: return@setOnClickListener

            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }

        fragmentPlayerBinding.skipNextImageView.setOnClickListener {

        }

        fragmentPlayerBinding.skipPrevImageView.setOnClickListener {

        }
    }

    private fun initPlayView(fragmentPlayerBinding: FragmentPlayerBinding) {
        context?.let {
            player = SimpleExoPlayer.Builder(it).build()
            fragmentPlayerBinding.playerView.player = player
        }

        binding?.let { binding ->
            player?.addListener(object : Player.EventListener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)

                    if (isPlaying) {
                        binding.playControlImageView.setImageResource(R.drawable.ic_baseline_pause_48)
                    } else {
                        binding.playControlImageView.setImageResource(R.drawable.ic_baseline_play_arrow_48)
                    }
                }
            })
        }
    }

    private fun initRecyclerView(fragmentPlayerBinding: FragmentPlayerBinding) {
        playListAdapter = PlayListAdapter {
            // todo : 음악 재생
        }
        fragmentPlayerBinding.playListRecyclerView.apply {
            adapter = playListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initPlayListButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playListImageView.setOnClickListener {
            // todo 만약 서버에서 데이터 로딩이 끝나지 않은 상태일 경우 예외처리

            fragmentPlayerBinding.playerViewGroup.isVisible = isWatchingPlayListView
            fragmentPlayerBinding.playListViewGroup.isVisible = isWatchingPlayListView.not()

            isWatchingPlayListView = isWatchingPlayListView.not()
        }
    }


    private fun getVideoListFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusicService::class.java)
            .also {
                it.listMusics()
                    .enqueue(object : Callback<MusicDto> {
                        override fun onResponse(
                            call: Call<MusicDto>,
                            response: Response<MusicDto>
                        ) {
                            //Log.d(TAG, "PlayerFragment onResponse() ${response.body()}")

                            if (response.isSuccessful.not()) {
                                return
                            }
                            // todo : map 사용법

                            response.body()?.let {
                                val modelList = it.musics.mapIndexed() { index, musicEntity ->
                                    musicEntity.mapper(index.toLong())
                                }

                                setMusicList(modelList)
                                playListAdapter.submitList(modelList)
                            }
                        }

                        override fun onFailure(call: Call<MusicDto>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
            }
    }

    private fun setMusicList(modelList: List<MusicModel>) {
        context?.let {
            player?.addMediaItems(modelList.map { musicModel ->
                MediaItem.Builder()
                    .setMediaId(musicModel.id.toString())
                    .setUri(musicModel.streamUrl)
                    .build()
            })

            player?.prepare()
            //player?.play()
        }
    }


    companion object {
        fun newInstance(): PlayerFragment {
            return PlayerFragment()
        }
    }

}
