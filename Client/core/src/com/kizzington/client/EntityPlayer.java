package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Timer;
import java.util.TimerTask;

public class EntityPlayer extends Entity {

    private Texture spriteSheet;
    private PlayerAnimation animation;
    private BitmapFont font;
    private GlyphLayout layout;

    private int direction = 0;
    private float animationState = 0;

    private String username;
    private int id;

    private float xDiff, yDiff;
    private boolean moving;

    public EntityPlayer(float x, float y){
        super(x, y);

        spriteSheet = new Texture(Gdx.files.internal("entities/1.png"));
        animation = new PlayerAnimation(spriteSheet, 4);

        font = MainClient.getFont(12, Color.WHITE);
        layout = new GlyphLayout();

        username = "";

        layout.setText(font, username);
    }

    public EntityPlayer(float x, float y, String username, int id){
        super(x, y);

        spriteSheet = new Texture(Gdx.files.internal("entities/1.png"));
        animation = new PlayerAnimation(spriteSheet, 4);

        font = MainClient.getFont(12, Color.WHITE);
        layout = new GlyphLayout();

        this.username = username;
        this.id = id;

        layout.setText(font, username);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        if(moving) {
            animationState += delta;

            setX(getX() + xDiff * delta * 2);
            setY(getY() + yDiff * delta * 2);
        }else{
            setX(Math.round(getX()));
            setY(Math.round(getY()));
        }

        setSprite(animation.getSprite(direction, animationState));

        int textWidth = (int)layout.width;
        int textHeight = (int)layout.height;

        font.getData().setScale(1, -1);
        font.setColor(Color.WHITE);
        font.draw(batch, username, getScreenX() + 32/2 - textWidth/2, getScreenY() - textHeight - 2);

        super.render(batch, delta);
    }

    public void move(int newX, int newY, int direction) {
        moving = true;

        this.xDiff = newX - getX();
        this.yDiff = newY - getY();

        this.direction = direction;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                moving = false;
            }
        }, 500);
    }

    public void setUsername(String username) {
        this.username = username;
        layout.setText(font, username);
    }
    public String getUsername() { return username; }

    public void setID(int id) { this.id = id; }
    public int getID() { return  id; }
}
