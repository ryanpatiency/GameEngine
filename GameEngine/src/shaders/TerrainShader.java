package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class TerrainShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";

	private int transformationMatrix_location;
	private int projectionMatrix_location;
	private int viewMatrix_location;
	private int lightPosition_location;
	private int lightColor_location;
	private int shineDamper_location;
	private int reflectivity_location;
	private int skyColor_location;
	private int backgroundTexture_location;
	private int rTexture_location;
	private int gTexture_location;
	private int bTexture_location;
	private int blendMap_location;
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
		
	}

	@Override
	protected void getAllUniformLocations() {
		transformationMatrix_location = super.getUniformLocation("transformationMatrix");
		projectionMatrix_location = super.getUniformLocation("projectionMatrix");
		viewMatrix_location = super.getUniformLocation("viewMatrix");
		lightPosition_location = super.getUniformLocation("lightPosition");
		lightColor_location = super.getUniformLocation("lightColor");
		shineDamper_location = super.getUniformLocation("shineDamper");
		reflectivity_location = super.getUniformLocation("reflectivity");
		skyColor_location = super.getUniformLocation("skyColor");
		backgroundTexture_location = super.getUniformLocation("backgroundTexture");
		rTexture_location = super.getUniformLocation("rTexture");
		gTexture_location = super.getUniformLocation("gTexture");
		bTexture_location = super.getUniformLocation("bTexture");
		blendMap_location = super.getUniformLocation("blendMap");
	}
	public void connectTextureUnits(){
		super.loadInt(backgroundTexture_location, 0);
		super.loadInt(rTexture_location, 1);
		super.loadInt(gTexture_location, 2);
		super.loadInt(bTexture_location, 3);
		super.loadInt(blendMap_location, 4);
	}
	public void loadSkyColor(Vector3f skyColor){
		super.loadVector(skyColor_location, skyColor);
	}
	public void loadShineVariable(float reflectivity, float shineDamper){
		super.loadFloat(shineDamper_location, shineDamper);
		super.loadFloat(reflectivity_location, reflectivity);
	}
	public void loadLight(Light light){
		super.loadVector(lightPosition_location, light.getPosition());
		super.loadVector(lightColor_location, light.getColor());
	}
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(transformationMatrix_location, matrix);
	}
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(projectionMatrix_location, matrix);
	}
	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Maths.createViewMatrix(camera);
		super.loadMatrix(viewMatrix_location, matrix);
	}
	

}
