package me.jhonn.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import me.jhonn.game.BaseGame
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.utils.viewport.FitViewport


abstract class BaseScreen(var game: BaseGame) : Screen, InputProcessor {
    private val viewport = FitViewport(800f, 600f)
    private val uiViewport = FitViewport(800f, 600f)
    var mainStage: Stage = Stage(viewport)
    var uiStage: Stage = Stage(uiViewport)
    var uiTable = Table()

    init {

        if (game.inputMultiplexer.size() == 0) {
            addInputs()
        }
        uiTable.apply {
            setFillParent(true)
            uiStage.addActor(this)
        }
    }

    fun isTouchDownEvent(e: Event): Boolean {
        return if (e is InputEvent) {
            e.type.equals(Type.touchDown)
        } else false
    }

    abstract fun update(deltaTime: Float)
    private fun addInputs() {
        game.inputMultiplexer.apply {
            addProcessor(this@BaseScreen)
            addProcessor(uiStage)
            addProcessor(mainStage)
        }
    }

    override fun show() {
        addInputs()
    }

    override fun hide() {
        game.inputMultiplexer.apply {
            removeProcessor(this@BaseScreen)
            removeProcessor(uiStage)
            removeProcessor(mainStage)
        }
    }

    override fun render(delta: Float) {

        uiStage.act(delta)
        mainStage.act(delta)
        update(delta)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        mainStage.draw()
        uiStage.draw()

    }


    override fun resize(width: Int, height: Int) {
        mainStage.viewport.update(width, height, true)
        uiStage.viewport.update(width, height, true)

    }

    override fun pause() {}

    override fun resume() {

    }


    override fun dispose() {}


    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(c: Char): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }
}