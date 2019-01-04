package com.kizzington.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimation {

    private Texture spriteSheet;
    private int length;

    private Animation<TextureRegion>[] animations;

    public PlayerAnimation(Texture spriteSheet, int length){
        this.spriteSheet = spriteSheet;
        this.length = length;

        TextureRegion[][] split = TextureRegion.split(spriteSheet, 32, 48);
        animations = new Animation[4];

        for(int i = 0; i < animations.length; i++) {
            animations[i] = new Animation<>(0.25f, split[i]);
        }
    }

    public Sprite getDownSprite(float state){
        return new Sprite(animations[0].getKeyFrame(state, true));
    }
    public Sprite getLeftSprite(float state){
        return new Sprite(animations[0].getKeyFrame(state, true));
    }
    public Sprite getRightSprite(float state){
        return new Sprite(animations[0].getKeyFrame(state, true));
    }
    public Sprite getUpSprite(float state){
        return new Sprite(animations[3].getKeyFrame(state, true));
    }
    public Sprite getSprite(int direction, float state){ return new Sprite(animations[direction].getKeyFrame(state, true)); }
}
