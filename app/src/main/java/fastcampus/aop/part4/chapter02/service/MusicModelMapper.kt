package fastcampus.aop.part4.chapter02.service

import fastcampus.aop.part4.chapter02.MusicModel

fun MusicEntity.mapper(id: Long): MusicModel =
    MusicModel(
        id = id,
        streamUrl = this.streamUrl,
        artist = this.artist,
        coverUrl = this.coverUrl,
        track = this.track
    )