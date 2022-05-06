# Chapter02 - Music Player
## Exoplayer
- Custom Controller 사용
- 버튼으로 Start, Pause, Skip Next, Skip Prev 관리함
```kotlin
fragmentPlayerBinding.playControlImageView.setOnClickListener {
    val player = this.player ?: return@setOnClickListener

    if (player.isPlaying) {
        player.pause()
    } else {
        player.play()
    }
}

fragmentPlayerBinding.skipNextImageView.setOnClickListener {
    val nextMusic = model.nextMusic() ?: return@setOnClickListener
    playMusic(nextMusic)
}

fragmentPlayerBinding.skipPrevImageView.setOnClickListener {
    val prevMusic = model.prevMusic() ?: return@setOnClickListener
    playMusic(prevMusic)
}
```

## Group
- 그룹화를 통해 레퍼런스 view를을 속성을 정의할 수 있음
```xml
<androidx.constraintlayout.widget.Group
    android:id="@+id/playerViewGroup"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="gone"
    app:constraint_referenced_ids="trackTextView, artistTextView, coverImageCardView, bottomBackgroundView, playerSeekBar, playTimeTextView, totalTimeTextView"
    tools:visibility="gone" />
```

## Constraint 속성들
- layout_constraintVertical_weight : 비율대로 뷰 높이를 지정
- layout_constraintHorizontal_bias : 위치의 비율을 지정 (0:왼쪽 view와 붙음, 1:오른쪽 뷰와 붙음)
- layout_constraintDimensionRatio : view의 크기를 높이, 넓이 비율로 지정함 (H, 1:1 - 1:1비율)
- translationY : y축 기준으로 view를 이동시킴
- cardElevation : cardView를 살짝 띄우는 느낌을 줌

