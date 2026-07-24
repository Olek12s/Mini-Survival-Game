package Mini_Survival_Game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontManager {
    public static final GlyphLayout LAYOUT = new GlyphLayout(); // object needed to measure width and height of the text (glyphs)


    public static BitmapFont DEFAULT;
    public static BitmapFont TITLE;

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Monocraft.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśźżĄĆĘŁŃÓŚŹŻ";

        // basic default font
        parameter.size = 24;
        DEFAULT = generator.generateFont(parameter);

        // bigger default font
        parameter.size = 48;
        TITLE = generator.generateFont(parameter);

        generator.dispose();
    }

    public static void dispose() {
        if (DEFAULT != null) {
            DEFAULT.dispose();
        }

        if (TITLE != null) {
            TITLE.dispose();
        }
    }
}