package me.jhonn.game.scenes

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import me.jhonn.game.entities.BaseActor

object SceneActions : Actions() {
    fun setText(s: String): Action {
        return SetTextAction(s)
    }

    fun pause(): Action {
        return forever(delay(1f))
    }

    fun moveToScreenLeft(duration: Float): Action {
        return moveToAligned(0f, 0f, Align.bottomLeft, duration)
    }

    fun moveToScreenRight(duration: Float): Action {
        return moveToAligned(
            BaseActor.worldBounds.width, 0f,
            Align.bottomRight, duration
        )
    }

    fun moveToScreenCenter(duration: Float): Action {
        return moveToAligned(
            BaseActor.worldBounds.width / 2, 0f,
            Align.bottom, duration
        )
    }

    fun moveToOutsideLeft(duration: Float): Action {
        return moveToAligned(0f, 0f, Align.bottomRight, duration)
    }

    fun moveToOutsideRight(duration: Float): Action {
        return moveToAligned(
            BaseActor.worldBounds.width, 0f,
            Align.bottomLeft, duration
        )
    }
}