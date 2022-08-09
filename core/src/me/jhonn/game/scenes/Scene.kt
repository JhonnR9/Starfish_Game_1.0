package me.jhonn.game.scenes

import com.badlogic.gdx.scenes.scene2d.Actor

class Scene : Actor() {
    private val segmentList: ArrayList<SceneSegment> = ArrayList()
    private var index: Int = -1

    val isSegmentFinished: Boolean
        get() = segmentList[index].isFinished()
    val isLastSegment: Boolean
        get() = index >= segmentList.size - 1

    val isSceneFinished: Boolean
        get() = isLastSegment && isSegmentFinished

    fun addSegment(segment: SceneSegment) {
        segmentList.add(segment)
    }

    fun clearSegments() {
        segmentList.clear()
    }

    fun start() {
        index = 0
        segmentList[index].start()
    }
    fun loadNextSegment() {
        if (isLastSegment) return
        segmentList[index].finish()
        index++
        segmentList[index].start()
    }

    override fun act(dt: Float) {
        if (isSegmentFinished && !isLastSegment) loadNextSegment()
    }

}