package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

	private int transformationMatrix_location;
	private int projectionMatrix_location;
	private int viewMatrix_location;
	private int lightPosition_location;
	private int lightColor_location;
	private int shineDamper_location;
	private int reflectivity_location;
	private int useFakeLighting_location;
	private int skyColor_location;
	
	public StaticShader() {
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
		useFakeLighting_location = super.getUniformLocation("useFakeLighting");
		skyColor_location = super.getUniformLocation("skyColor");
	}
	public void loadSkyColor(Vector3f skyColor){
		super.loadVector(skyColor_location, skyColor);
	}
	public void loadUseFakeLighting(boolean isUse){
		super.loadBoolean(useFakeLighting_location, isUse);
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
