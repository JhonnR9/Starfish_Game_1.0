package me.jhonn.game.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import me.jhonn.game.BaseGame

class DialogBox(x: Float, y: Float, stage: Stage, game: BaseGame) : BaseActor(x, y, stage) {
    private val dialogLabel: Label
    private val padding = 16f

    init {
        loadTexture("dialog-translucent.png")
        dialogLabel = Label(" ", game.getLabelStyle()).apply {
            wrap = true
            setAlignment(Align.topLeft)
            setPosition(padding, padding)
        }
        setDialogSize(width, height)
        addActor(dialogLabel)
    }

    fun setDialogSize(width: Float, height: Float) {
        setSize(width, height)
        dialogLabel.width = width - 2 * padding
        dialogLabel.height = height - 2 * padding
    }

    fun setText(text: String) {
        dialogLabel.setText(text)
    }

    fun setFontScale(scale: Float) {
        dialogLabel.setFontScale(scale)
    }

    fun setFontColor(color: Color) {
        dialogLabel.color = color
    }

    fun setBackgroundColor(color: Color) {
        this.color = color
    }

    fun alignTopLeft() {
        dialogLabel.setAlignment(Align.topLeft)
    }

    fun alignCenter() {}
}