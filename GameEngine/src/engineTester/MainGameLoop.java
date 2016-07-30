package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Random random = new Random();

		// begin------------------------------prepare terrain
		// pack------------------------------------------
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack terrainTexturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// end
		// ---------------------------------------------------------------------------------------------
		TexturedModel playerModel = new TexturedModel(
				loader.loadObj("bunny"),
				new ModelTexture(loader.loadTexture("white")));

		Player player = new Player(playerModel, new Vector3f(0,0,-100), 0, 0, 0, 0.3f);
		

		RawModel flowerRawModel = loader.loadObj("grassModel");
		ModelTexture flowerTexture = new ModelTexture(loader.loadTexture("flower"));
		flowerTexture.setHasTransparency(true);
		flowerTexture.setUseFakeLighting(true);
		TexturedModel flowerModel = new TexturedModel(flowerRawModel, flowerTexture);

		RawModel fernRawModel = loader.loadObj("fern");
		ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));
		fernTexture.setHasTransparency(true);
		TexturedModel fernModel = new TexturedModel(fernRawModel, fernTexture);

		RawModel grassRawModel = loader.loadObj("grassModel");
		ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
		grassTexture.setHasTransparency(true);
		grassTexture.setUseFakeLighting(true);
		TexturedModel grassModel = new TexturedModel(grassRawModel, grassTexture);

		RawModel treeRawModel = loader.loadObj("tree");
		TexturedModel treeModel = new TexturedModel(treeRawModel, new ModelTexture(loader.loadTexture("tree")));

		RawModel lowPolyTreeRawModel = loader.loadObj("lowPolyTree");
		TexturedModel lowPolyTreeModel = new TexturedModel(lowPolyTreeRawModel, new ModelTexture(loader.loadTexture("lowPolyTree")));

		RawModel dragonRawModel = loader.loadObj("dragon");
		TexturedModel dragonModel = new TexturedModel(dragonRawModel, new ModelTexture(loader.loadTexture("white")));

		Terrain grassTerrain1 = new Terrain(0, -1, loader, terrainTexturePack, blendMap, "heightmap");
		Terrain grassTerrain2 = new Terrain(-1, -1, loader, terrainTexturePack, blendMap, "heightmap");
		List<Entity> ferns = new ArrayList<>();
		List<Entity> grasses = new ArrayList<>();
		List<Entity> flowers = new ArrayList<>();
		List<Entity> trees = new ArrayList<>();
		List<Entity> lowPolyTrees = new ArrayList<>();

		Entity dragon = new Entity(dragonModel,
				new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -800 - 10),
				0, random.nextFloat() * 180, 0, 4);
		for (int i = 0; i < 40; i++) {
			if (i < 100) {
				Entity fern = new Entity(fernModel,
						new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -800 - 10),
						0, random.nextFloat() * 180, 0, 2);
				ferns.add(fern);
			}
			if (i < 100) {
				Entity flower = new Entity(flowerModel,
						new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -800 - 10),
						0, random.nextFloat() * 180, 0, 2);
				flowers.add(flower);
			}
			Entity grass = new Entity(grassModel,
					new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -800 - 10),
					0, random.nextFloat() * 180, 0, 3f);
			grasses.add(grass);
			if (i < 4) {
				Entity tree = new Entity(treeModel,
						new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -800 - 10),
						0, random.nextFloat() * 180, 0, 60);
				trees.add(tree);
			}
			if (i < 4) {
				Entity lowPolyTree = new Entity(lowPolyTreeModel,
						new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -800 - 10),
						0, random.nextFloat() * 180, 0, 3);
				lowPolyTrees.add(lowPolyTree);
			}
		}

		Camera camera = new Camera(player);
		Light light = new Light(new Vector3f(3000, 3000, 2000), new Vector3f(1, 1, 1));

		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested()) {
			// game logic
			camera.move();
			player.move();
			renderer.processTerrain(grassTerrain1);
			renderer.processTerrain(grassTerrain2);
			// renderer.processEntity(dragon);
			 renderer.processEntity(player);
			for (Entity fern : ferns) {
				renderer.processEntity(fern);
			}
			for (Entity grass : grasses) {
				// grass.increaseRotation(0, 0.1f, 0);

				renderer.processEntity(grass);
			}
			for (Entity flower : flowers) {
				// flower.increaseRotation(0, 0.1f, 0);

				renderer.processEntity(flower);
			}
			for (Entity tree : trees) {
				// tree.increaseRotation(0, 0.1f, 0);
				renderer.processEntity(tree);
			}
			for (Entity lowPolyTree : lowPolyTrees) {
				// lowPolyTree.increaseRotation(0, 0.1f, 0);
				renderer.processEntity(lowPolyTree);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
