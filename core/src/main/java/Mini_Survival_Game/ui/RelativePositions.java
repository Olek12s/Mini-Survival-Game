package Mini_Survival_Game.ui;

import com.badlogic.gdx.utils.Align;

public enum  RelativePositions {
    TOP_LEFT, TOP, TOP_RIGHT,
    MID_LEFT, MID, MID_RIGHT,
    BOT_LEFT, BOT, BOT_RIGHT;

    public int getHorizontalAlign() {
        if (this == TOP_LEFT || this == MID_LEFT || this == BOT_LEFT) return Align.left;
        if (this == TOP_RIGHT || this == MID_RIGHT || this == BOT_RIGHT) return Align.right;
        return Align.center;
    }

    public float calculateTextY(float baseY, float innerHeight, float textHeight) {
        if (this == TOP_LEFT || this == TOP || this == TOP_RIGHT) {
            return baseY + innerHeight;
        }
        if (this == MID_LEFT || this == MID || this == MID_RIGHT) {
            return baseY + (innerHeight + textHeight) / 2f;
        }
        return baseY + textHeight;
    }
}