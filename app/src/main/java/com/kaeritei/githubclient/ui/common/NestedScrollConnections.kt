package com.kaeritei.githubclient.ui.common

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

object NestedScrollConnections {
    fun hideFixedHeaderContent(
        wrapperScrollableState: ScrollableState,
        contentLazyListState: ScrollableState,
    ): NestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                // 親がスクロールできるうちの処理
                if (wrapperScrollableState.canScrollForward) {
                    // 上方向のスクロールなら特別な処理はしない
                    if (available.y > 0f) {
                        return super.onPreScroll(available, source)
                    }

                    // 子側をタップしてスクロールをした時も親のスクロールを優先する
                    wrapperScrollableState.dispatchRawDelta(-available.y)
                    return Offset(0f, available.y)
                }

                // 親と子の境界かつかつ上方向スクロールなら、自動で親側のスクロールとして消費されるため特別は処理はしない
                if (!contentLazyListState.canScrollBackward && available.y > 0f) {
                    return super.onPreScroll(available, source)
                }

                // 一番下かつ下方向のスクロールなら特別な処理はしない
                if (!contentLazyListState.canScrollForward && available.y < 0f) {
                    return super.onPreScroll(available, source)
                }

                // 子をスクロール
                contentLazyListState.dispatchRawDelta(-available.y)
                return available
            }
        }
}
