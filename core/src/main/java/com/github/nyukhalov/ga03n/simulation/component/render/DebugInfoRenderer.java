package com.github.nyukhalov.ga03n.simulation.component.render;

import com.badlogic.gdx.Gdx;
import com.github.nyukhalov.ga03n.engine.Component;
import com.github.nyukhalov.ga03n.engine.World;
import com.github.nyukhalov.ga03n.simulation.component.DebugInfo;

public class DebugInfoRenderer extends Component {

    private DebugInfo info;

    @Override
    protected void start() {
        info = getComponent(DebugInfo.class);
    }

    @Override
    protected void onGUI() {
        int height = Gdx.graphics.getHeight();

        for (int i=0; i<info.info.size(); i++) {
            World.current.font.draw(World.current.batch, info.info.get(i), 20, height - 20 - (i * 20));
        }
    }
}
