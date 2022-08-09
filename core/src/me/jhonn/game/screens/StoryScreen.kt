package me.jhonn.game.screens

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import me.jhonn.game.BaseGame
import me.jhonn.game.entities.BaseActor
import me.jhonn.game.entities.DialogBox
import me.jhonn.game.scenes.Scene
import me.jhonn.game.scenes.SceneActions
import me.jhonn.game.scenes.SceneSegment

class StoryScreen(game: BaseGame) : BaseScreen(game) {
    var scene: Scene = Scene()
    var continueKey: BaseActor = BaseActor(0f, 0f, uiStage)

    init {
        continueKey.apply {
            loadTexture("key-C.png")
            setSize(32f, 32f)
            isVisible = false
        }
        val background = BaseActor(0f, 0f, mainStage).apply {
            loadTexture("oceanside.png")
            setSize(800f, 600f)
            setOpacity(0f)
            BaseActor.createWorldBounds(this)
            mainStage.addActor(this)
        }
        val turtle = BaseActor(0f, 0f, mainStage).apply {
            loadTexture("turtle-big.png")
            setPosition(-this.width, 0f)
            mainStage.addActor(this)
        }
        val dialogBox = DialogBox(0f, 0f, uiStage, game).apply {
            setDialogSize(600f, 200f)
            setBackgroundColor(Color(0.6f, 0.6f, 0.8f, 1f))
            setFontScale(0.75f)
            isVisible = false
            addActor(continueKey)
            continueKey.setPosition(this.width - continueKey.width, 0f)
        }
        uiTable.add(dialogBox).expandX().expandY().bottom()

        scene.apply {
            fun continueScene() {
                addSegment(SceneSegment(continueKey, Actions.show()))
                addSegment(SceneSegment(background, SceneActions.pause()))
                addSegment(SceneSegment(continueKey, Actions.hide()))
            }
            mainStage.addActor(this)
            addSegment(SceneSegment(background, Actions.fadeIn(1f)))
            addSegment(SceneSegment(turtle, SceneActions.moveToScreenCenter(2f)))
            addSegment(SceneSegment(dialogBox, Actions.show()))
            addSegment(
                SceneSegment(
                    dialogBox,
                    SceneActions.setText("I want to be the very best . . . Starfish Collector!")
                )
            )

            continueScene()

            addSegment(
                SceneSegment(
                    dialogBox,
                    SceneActions.setText("I've got to collect them all!")
                )
            )

            continueScene()
            addSegment(
                SceneSegment(
                    dialogBox,
                    SceneActions.setText("Nice place this one!!")
                )
            )
            continueScene()

            addSegment(SceneSegment(dialogBox, Actions.hide()))
            addSegment(SceneSegment(turtle, SceneActions.moveToOutsideRight(1f)))
            addSegment(SceneSegment(background, Actions.fadeOut(1f)))


        }
        scene.start()


    }

    override fun update(deltaTime: Float) {
        if (scene.isSceneFinished) game.screen = LevelScreen(game)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode== Input.Keys.SPACE){
            game.screen = LevelScreen(game)
        }
        if (keycode == Input.Keys.C && continueKey.isVisible) {
            scene.loadNextSegment()
        }
        return super.keyDown(keycode)
    }
}