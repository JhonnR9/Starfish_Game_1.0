package me.jhonn.game

import com.badlogic.gdx.Gdx
import me.jhonn.game.screens.MenuScreen
import me.jhonn.game.screens.StoryScreen

class StarfishGame : BaseGame() {
    override fun create() {
        screen = (MenuScreen(this))
    }

}