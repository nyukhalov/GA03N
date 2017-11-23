package com.github.nyukhalov.ga03n;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.github.nyukhalov.ga03n.engine.Entity;
import com.github.nyukhalov.ga03n.engine.World;
import com.github.nyukhalov.ga03n.simulation.prefab.SimulationPrefab;

public class MainScreen implements Screen {
    private static final float CAMERA_WIDTH_METERS = 50;

    private final OrthographicCamera camera;
    private final Matrix4 uiMatrix;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final World world;

    public MainScreen() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(
                false,
                CAMERA_WIDTH_METERS,
                Gdx.graphics.getHeight() * CAMERA_WIDTH_METERS / Gdx.graphics.getWidth()
        );
        this.uiMatrix = camera.combined.cpy();
        this.uiMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.world = new World(shapeRenderer, batch, font);

        // initialize
        SimulationPrefab simulationPrefab = new SimulationPrefab();
        Entity.instantiate(simulationPrefab);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        world.update(delta);

        render();
        renderGUI();
    }

    private void render() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();

        world.render();

        shapeRenderer.end();
    }

    private void renderGUI() {
        shapeRenderer.setProjectionMatrix(uiMatrix);
        batch.setProjectionMatrix(uiMatrix);
        shapeRenderer.begin();
        batch.begin();

        world.renderUI();

        batch.end();
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        uiMatrix.setToOrtho2D(0, 0, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
