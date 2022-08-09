package me.jhonn.game.scenes

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor

class SceneSegment(val actor: Actor, val action: Action) {
    fun start() {
        actor.clearActions()
        actor.addAction(action)
    }

    fun isFinished(): Boolean = (actor.actions.size == 0)
    fun finish(){
        if (actor.hasActions()){
            actor.actions.first().act(100000f)
            actor.clearActions()
        }
    }
}