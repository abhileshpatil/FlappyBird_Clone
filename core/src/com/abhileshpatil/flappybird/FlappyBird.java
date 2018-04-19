package com.abhileshpatil.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import sun.rmi.runtime.Log;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
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

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		bird[0]=new Texture("bird.png");
		bird[1]=new Texture("bird2.png");
        gap=400;
		birdstate=0;
		generator = new Random();
		for(int i=0;i<tUpperTube.length;i++)
		{
			tUpperTube[i]=new Texture("toptube.png");
			tDownTube[i]=new Texture("bottomtube.png");
            tubeOffset[i]=(generator.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()-gap-200);
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
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(Gdx.input.justTouched())
		{
			velocity=-30;
			firstTouch=true;

			for(int i=0;i<tUpperTube.length;i++)
			{
				pipeSpeed[i]=pipeSpeed[i]-10;
				if(pipeSpeed[i]+(tDownTube[0].getWidth()/2)<0)
				{
					pipeSpeed[i]=pipeSpeed[i]+Gdx.graphics.getWidth()*3;
					tubeOffset[i]=(generator.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()-gap-200);
				}
				batch.draw(tUpperTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(tDownTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i]);
			}
		}
		else if(firstTouch==true)
		{
			velocity=velocity+gravity;

			for(int i=0;i<tUpperTube.length;i++) {
				pipeSpeed[i] = pipeSpeed[i] - 5;
				if(pipeSpeed[i]+(tDownTube[0].getWidth()/2)<0)
				{
					pipeSpeed[i]=pipeSpeed[i]+Gdx.graphics.getWidth()*3;
					tubeOffset[i]=(generator.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()-gap-200);
				}
				batch.draw(tUpperTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(tDownTube[i], (pipeSpeed[i]), Gdx.graphics.getHeight() / 2 - gap / 2 - tDownTube[0].getHeight() + tubeOffset[i]);
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
		batch.draw(bird[birdstate],(Gdx.graphics.getWidth()/2-(bird[birdstate].getWidth()/2)),birdspeed-(bird[birdstate].getHeight()/2));
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
