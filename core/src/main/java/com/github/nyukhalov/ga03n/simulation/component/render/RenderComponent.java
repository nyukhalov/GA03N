package com.github.nyukhalov.ga03n.simulation.component.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.World;

public class RenderComponent extends Component {
    public Color color;

    @Override
    protected void onRender() {
        World.current.shapeRenderer.setColor(color);
        World.current.shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        float hs = gameObject.size / 2f;
        World.current.shapeRenderer.rect(
                gameObject.position.x - hs,
                gameObject.position.y - hs,
                gameObject.size,
                gameObject.size
        );
    }
}
