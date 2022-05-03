package fastcampus.aop.part4.chapter02.service

import fastcampus.aop.part4.chapter02.MusicModel
import fastcampus.aop.part4.chapter02.PlayerModel

fun MusicEntity.mapper(id: Long): MusicModel =
    MusicModel(
        id = id,
        streamUrl = this.streamUrl,
        artist = this.artist,
        coverUrl = this.coverUrl,
        track = this.track
    )


fun MusicDto.mapper(): PlayerModel =
    PlayerModel(
        playMusicList = musics.mapIndexed { index, musicEntity ->
            musicEntity.mapper(index.toLong())
        }
    )