package renderEngine;

import java.util.List;
import java.util.Map;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;
import entities.Entity;

public class EntityRenderer {
	
	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities){
		for(TexturedModel texturedModel: entities.keySet()){
			//begin prepare TexturedModel
			RawModel model = texturedModel.getRawModel();
			ModelTexture texture = texturedModel.getTexture();
			GL30.glBindVertexArray(model.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			if(texture.isHasTransparency()){
				MasterRenderer.disableCulling();
			}
			shader.loadUseFakeLighting(texture.isUseFakeLighting());
			shader.loadShineVariable(texture.getReflectivity(), texture.getShineDamper());
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
			//end
			for(Entity entity: entities.get(texturedModel)){
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
						entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
				shader.loadTransformationMatrix(transformationMatrix);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			//begin unbind TextureModel
			MasterRenderer.enableCulling();
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
			//end
		}
	}
	
	
	
}
