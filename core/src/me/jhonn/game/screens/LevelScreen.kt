package me.jhonn.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import me.jhonn.game.BaseGame
import me.jhonn.game.entities.*


class LevelScreen(game: BaseGame) : BaseScreen(game) {
    private val turtle: Turtle
    private var win: Boolean = false
    private var ocean: BaseActor = BaseActor(0f, 0f, mainStage)
    private var starfishLabel: Label
    private val dialogBox = DialogBox(0f, 0f, uiStage, game)


    private val waterDrop: Sound
    private val instrumental: Music
    private val oceanSurf: Music

    private val restartButton: Button
   private val volumeButton: Button


    init {

        with(Gdx.audio) {

            var path: String = "Water_Drop.ogg"
            waterDrop = newSound(Gdx.files.internal(path))

            path = "Master_of_the_Feast.ogg"
            instrumental = newMusic(Gdx.files.internal(path)).apply {
                isLooping = true
                volume = game.audioVolume
                play()
            }

            path = "Ocean_Waves.ogg"
            oceanSurf = newMusic(Gdx.files.internal(path)).apply {
                isLooping = true
                volume = game.audioVolume
                play()
            }
        }

        starfishLabel = Label("Star Left: ", game.getLabelStyle())
        starfishLabel.color = Color.CYAN

        val restartButtonStyle = ButtonStyle().apply {
            val buttonTexture = Texture(Gdx.files.internal("undo.png"))
            val buttonRegion = TextureRegion(buttonTexture)
            up = TextureRegionDrawable(buttonRegion)

        }

        restartButton = Button(restartButtonStyle).apply {
            color = Color.CYAN
            isTransform = true
            addListener { e: Event ->
                if (!isTouchDownEvent(e)) {
                    return@addListener false
                }

                game.screen = LevelScreen(game)

                instrumental.dispose()
                oceanSurf.dispose()

                return@addListener false
            }
        }
        val volumeButtonStyle = ButtonStyle().apply {
            val buttonTexture = Texture(Gdx.files.internal("audio.png"))
            val buttonRegion = TextureRegion(buttonTexture)
            up = TextureRegionDrawable(buttonRegion)
        }
        volumeButton = Button(volumeButtonStyle).apply {
            color = Color.CYAN
            addListener { e: Event ->
                if (!isTouchDownEvent(e)) {
                    return@addListener false
                }
                game.audioVolume = if (game.audioVolume == game.maxVolume) {
                    color = Color.GRAY
                    0f
                } else {
                    color = Color.CYAN
                    game.maxVolume
                }
                instrumental.volume = game.audioVolume
                oceanSurf.volume = game.audioVolume

                return@addListener false
            }
        }

        ocean.apply {
            loadTexture("water-border.jpg")
            setSize(1200f, 900f)
            BaseActor.createWorldBounds(ocean)
            mainStage.addActor(this)
        }



        Starfish(200f, 800f, mainStage)
        Starfish(600f, 100f, mainStage)
        Starfish(950f, 700f, mainStage)
        Starfish(600f, 800f, mainStage)
        Starfish(500f, 40f, mainStage)
        Starfish(28f, 450f, mainStage)

        Rock(500f, 527f, mainStage)
        Rock(150f, 680f, mainStage)
        Rock(60f, 39f, mainStage)
        Rock(200f, 240f, mainStage)

        val sign1 = Sign(20f, 400f, mainStage)
        sign1.text = "West Starfish Bay"
        mainStage.addActor(sign1)
        val sign2 = Sign(600f, 300f, mainStage)
        sign2.text = "East Starfish Bay"
        mainStage.addActor(sign2)

        dialogBox.apply {
            setBackgroundColor(Color.TAN)
            setFontColor(Color.BROWN)
            setDialogSize(600f, 100f)
            setFontScale(0.80f)
            alignCenter()
            isVisible = false
        }

        turtle = Turtle(300f, 400f, mainStage)
        mainStage.addActor(turtle)
        uiTable.apply {
            pad(10f)
            add(starfishLabel).top()
            add().expandX().expandY()
            add(volumeButton).top()
            add(restartButton).top()
            uiTable.row()
            uiTable.add(dialogBox).colspan(3)

        }
    }

    override fun update(deltaTime: Float) {
        starfishLabel.setText("Star Left: ${BaseActor.count(mainStage, "Starfish")}")

        for (sign in BaseActor.getList(mainStage, "Sign")) {
            if (sign is Sign) {
                turtle.preventOverlap(sign)
                val nearby = turtle.isWithinDistance(5f, sign)
                if (nearby && !sign.isViewing) {
                    dialogBox.setText(sign.text)
                    dialogBox.isVisible = true
                    sign.isViewing = true
                }
                if (sign.isViewing && !nearby) {
                    dialogBox.setText("")
                    dialogBox.isVisible = false
                    sign.isViewing = false
                }
            }
        }
        for (rockActor in BaseActor.getList(mainStage, "Rock")) {
            turtle.preventOverlap(rockActor)
        }
        for (starfishActor in BaseActor.getList(mainStage, "Starfish")) {
            if (starfishActor is Starfish) {
                if (turtle.overlaps(starfishActor) && !starfishActor.collected) {
                    with(starfishActor) {
                        collected = true
                        waterDrop.play(game.audioVolume)
                        clearActions()
                        addAction(Actions.fadeOut(1f))
                        addAction(Actions.after(Actions.removeActor()))
                        Whirlpool(0f, 0f, mainStage).apply {
                            centerAtActor(starfishActor)
                            setOpacity(0.25f)
                        }
                    }
                }
            }


        }

        if (BaseActor.count(mainStage, "Starfish") == 0 && !win) {
            win = true
            BaseActor(0f, 0f, uiStage).apply {
                loadTexture("you-win.png")
                centerAtPosition(400f, 300f)
                setOpacity(0f)
                addAction(Actions.delay(1f))
                addAction(Actions.after(Actions.fadeIn(1f)))
                uiStage.addActor(this)
            }

        }
    }

    override fun resume() {
        game.screen = MenuScreen(game)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.E) {
            game.screen = LevelBonus(game)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.screen = MenuScreen(game)
        }
        return false
    }
}