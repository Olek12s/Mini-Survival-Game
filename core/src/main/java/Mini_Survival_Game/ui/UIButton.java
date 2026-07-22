package Mini_Survival_Game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIButton extends UIElement {
    private String text;
    private BitmapFont font;
    private ClickCallback callback;

    public interface ClickCallback {
        void onClick();
    }

    public UIButton(float x, float y, float width, float height, String text, BitmapFont font, ClickCallback callback) {
        super(x, y, width, height);
        this.text = text;
        this.font = font;
        this.callback = callback;
    }

    public UIButton(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.text = "";
        this.font = new BitmapFont(Gdx.files.internal("monocraft.fnt"));
        this.callback = null;
    }

    @Override
    protected void renderElement(SpriteBatch batch) {

    }

    @Override
    protected boolean onClick(int button) {
        if (callback != null) {
            callback.onClick();
            return true; // click consumed by button
        }
        return false;
    }
}
