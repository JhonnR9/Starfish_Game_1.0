package me.jhonn.game.scenes

import com.badlogic.gdx.scenes.scene2d.Action
import me.jhonn.game.entities.DialogBox

class SetTextAction(val textToDisplay: String) : Action() {
    override fun act(delta: Float): Boolean {
        val dialogBox: DialogBox = target as DialogBox
        dialogBox.setText(textToDisplay)
        return true
    }
}