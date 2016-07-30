package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Player extends Entity {

	private static final float RUN_SPEED = 50;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -70;
	private static final float JUMP_SPEED = 40;
	private static final float TERRAIN_HEIGHT = 0;

	private float currRunSpeed = 0;
	private float currTurnSpeed = 0;
	private float upwardSpeed = 0;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move() {
		checkInputs();
		float dt = DisplayManager.getFrameTimeSeconds();
		float distance = currRunSpeed*dt;
		float dx = (float)(distance*Math.sin(Math.toRadians(this.getRotY())));
		float dz = (float)(distance*Math.cos(Math.toRadians(this.getRotY())));
		if(this.getPosition().getY()<TERRAIN_HEIGHT){
			upwardSpeed = 0;
			this.getPosition().setY(0);
		}else{
			upwardSpeed += GRAVITY*dt;
			float dy = upwardSpeed * dt;
			super.increasePosition(dx, dy, dz);
			
		}
		float rotation = currTurnSpeed*DisplayManager.getFrameTimeSeconds();
		super.increaseRotation(0, rotation, 0);

	}

	private void jump(){
		if(upwardSpeed==0){
			upwardSpeed = JUMP_SPEED;
		}
	}
	
	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currRunSpeed = RUN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currRunSpeed = -RUN_SPEED;
		} else {
			this.currRunSpeed = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currTurnSpeed = TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currTurnSpeed = -TURN_SPEED;
		} else {
			this.currTurnSpeed = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}
}
