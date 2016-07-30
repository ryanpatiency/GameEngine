package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	private Player player;
	private float distanceFromPlayer = 80;
	private float zoomLevel = 0;
	private float angleAroundPlayer = 0;
	
	public Camera(Player player){
		this.player = player;
		this.yaw = player.getRotY();
		this.pitch=20;
		this.yaw=0;
	}
	
	public void move(){
		calculateZoom();
		calculatePitchAndAngleAroundPlayer();
		calculatePosition();
		
	}
	public void calculatePitchAndAngleAroundPlayer(){
		//0 left click
		//1 right click
		//2 middle click
		if(Mouse.isButtonDown(2)){
			angleAroundPlayer -= Mouse.getDX()/10f;
			pitch -= Mouse.getDY()/10f;
		}
	}
	public void calculateZoom(){
		zoomLevel = Mouse.getDWheel()/30f;
		distanceFromPlayer-=zoomLevel;
	}
		
	public void calculatePosition(){
		float horizontalDistance = distanceFromPlayer * (float)Math.cos(Math.toRadians(pitch));
		float verticalDistance = distanceFromPlayer * (float)Math.sin(Math.toRadians(pitch));
		float theta = player.getRotY()/2f+angleAroundPlayer;
		float dx = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float dz = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		this.position.x = player.getPosition().getX()-dx;
		this.position.y = player.getPosition().getY()+verticalDistance;
		this.position.z = player.getPosition().getZ()-dz;
		this.yaw = 180-theta;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public void setYaw(int yaw) {
		this.yaw = yaw;
	}
	public float getRoll() {
		return roll;
	}
	public void setRoll(int roll) {
		this.roll = roll;
	}
	
	
}
