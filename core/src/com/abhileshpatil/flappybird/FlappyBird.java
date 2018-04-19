package com.abhileshpatil.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Shape;

import java.util.Random;

import sun.rmi.runtime.Log;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture gameover;
	Texture[] bird=new Texture[2];
	Texture []tUpperTube=new Texture[4];
	Texture []tDownTube=new Texture[4];
	int birdstate;
	float birdspeed=0;
	Boolean firstTouch;
	float gap=0;
	float velocity;
	float gravity=2;
	Random generator;
	float maxTubeOffset;
	float []tubeOffset =new float[4];
	float []pipeSpeed = new float[4];
	Circle circle;
	Rectangle []rectangle_down=new Rectangle[4];
	Rectangle []rectangle_up=new Rectangle[4];
	ShapeRenderer shapeRenderer;
	int score=0;
	Boolean []scoreTracker=new Boolean[4];
	Boolean playerStatus;
	BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		circle=new Circle();
		shapeRenderer=new ShapeRenderer();
		background = new Texture("bg.png");
		bird[0]=new Texture("bird.png");
		bird[1]=new Texture("bird2.png");
		gameover=new Texture("gameover.png");
        gap=500;
		birdstate=0;
		playerStatus=false;
		generator = new Random();
		font=new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(10);

		for(int i=0;i<tUpperTube.length;i++)
		{
			tUpperTube[i]=new Texture("toptube.png");
			tDownTube[i]=new Texture("bottomtube.png");
            tubeOffset[i]=(generator.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()-gap-200);
			rectangle_down[i]=new Rectangle();
			rectangle_up[i]=new Rectangle();
			scoreTracker[i]=false;
		}

		pipeSpeed[0]=Gdx.graphics.getWidth();
		for(int i=0;i<tUpperTube.length-1;i++)
		{
			pipeSpeed[i+1]=pipeSpeed[i]+Gdx.graphics.getWidth()/2 +Gdx.graphics.getWidth()/4 ;
		}

		birdspeed=(Gdx.graphics.getHeight()/2);

        maxTubeOffset=Gdx.graphics.getHeight()/2 - gap/2 -100;
		firstTouch=false;
	}

	@Override
	public void render () {
		batch.begin();
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(playerStatus==false) {
			if (Gdx.input.justTouched()) {
				velocity = -30;
				firstTouch = true;

				for (int i = 0; i < tUpperTube.length; i++) {
					pipeSpeed[i] = pipeSpeed[i] - 10;
					if ((pipeSpeed[i] + (tDownTube[0].getWidth())) < (Gdx.graphics.getWidth() / 2) && scoreTracker[i] == false && playerStatus == false) {
						score = score + 1;
						scoreTracker[i] = true;
					}
					if ((pipeSpeed[i] + (tDownTube[0].getWidth() / 2)) < 0) {
						scoreTracker[i] = false;
						pipeSpeed[i] = pipeSpeed[i] + Gdx.graphics.getWidth() * 3;
						tubeOffset[i] = (generator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
					}
					batch.draw(tUpperTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
					batch.draw(tDownTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i]);

					rectangle_up[i].set((pipeSpeed[i]), Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], tUpperTube[0].getWidth(), tUpperTube[0].getHeight());
					rectangle_down[i].set((pipeSpeed[i]), Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i], tDownTube[0].getWidth(), tDownTube[0].getHeight());
//				shapeRenderer.rect((pipeSpeed[i]),Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],tUpperTube[0].getWidth(),tUpperTube[0].getHeight());
//				shapeRenderer.rect((pipeSpeed[i]),Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i],tDownTube[0].getWidth(),tDownTube[0].getHeight());
					if (Intersector.overlaps(circle, rectangle_up[i])) {
						playerStatus = true;
					}
					if (Intersector.overlaps(circle, rectangle_down[i])) {
						playerStatus = true;
					}
				}
			} else if (firstTouch == true) {
				velocity = velocity + gravity;

				for (int i = 0; i < tUpperTube.length; i++) {
					pipeSpeed[i] = pipeSpeed[i] - 5;
					if ((pipeSpeed[i] + (tDownTube[0].getWidth())) < (Gdx.graphics.getWidth() / 2) && scoreTracker[i] == false && playerStatus == false) {
						score = score + 1;
						scoreTracker[i] = true;
					}
					if ((pipeSpeed[i] + (tDownTube[0].getWidth() / 2)) < 0) {
//					score=score+1;
						scoreTracker[i] = false;
						pipeSpeed[i] = pipeSpeed[i] + Gdx.graphics.getWidth() * 3;
						tubeOffset[i] = (generator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
					}
					batch.draw(tUpperTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
					batch.draw(tDownTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i]);

					rectangle_up[i].set((pipeSpeed[i]), Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], tUpperTube[0].getWidth(), tUpperTube[0].getHeight());
					rectangle_down[i].set((pipeSpeed[i]), Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i], tDownTube[0].getWidth(), tDownTube[0].getHeight());
//				shapeRenderer.rect((pipeSpeed[i]),Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],tUpperTube[0].getWidth(),tUpperTube[0].getHeight());
//				shapeRenderer.rect((pipeSpeed[i]),Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i],tDownTube[0].getWidth(),tDownTube[0].getHeight());
					if (Intersector.overlaps(circle, rectangle_up[i])) {
						playerStatus = true;
					}
					if (Intersector.overlaps(circle, rectangle_down[i])) {
						playerStatus = true;
					}
				}
			}
		}
		else
		{
			batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth(), Gdx.graphics.getHeight() / 2 - gameover.getHeight());
			while ((birdspeed-(bird[birdstate]).getHeight()/2) < 0)
			{
				velocity=velocity+gravity;
				birdspeed=birdspeed+velocity;
			}
		}
		if(birdstate==0)
		{
			birdstate=1;
		}
		else
		{
			birdstate=0;
		}
		birdspeed=birdspeed-velocity;
		if((birdspeed-(bird[birdstate]).getHeight()/2) < 0)
		{
			birdspeed=(bird[birdstate]).getHeight()/2;
		}
		batch.draw(bird[birdstate],(Gdx.graphics.getWidth()/2-(bird[birdstate].getWidth()/2)),birdspeed-(bird[birdstate].getHeight()/2));
		font.draw(batch,""+score,Gdx.graphics.getWidth()/2,(Gdx.graphics.getHeight()/2 +Gdx.graphics.getHeight()/4));
		batch.end();
		circle.set(Gdx.graphics.getWidth()/2,(birdspeed-(bird[birdstate].getHeight()/2))+bird[birdstate].getWidth()/2,bird[birdstate].getWidth()/2);


		//        Gdx.app.log("Score",""+score);
//		shapeRenderer.setColor(Color.BLUE);
//		shapeRenderer.circle(circle.x,circle.y,circle.radius);

//		shapeRenderer.end();

	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
