package me.jhonn.game.entities

import com.badlogic.gdx.scenes.scene2d.Stage

class Sign(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {
    var text: String = " "
    var isViewing = false

    init {
        loadTexture("sign.png")
        boundaryPolygon = createBoundaryPolygon(8)
    }

}