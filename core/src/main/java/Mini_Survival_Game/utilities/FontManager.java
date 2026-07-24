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


    // Enablink markups for fonts enable making colorful text via markups such as:
    // .setText("[RED]S[GREEN]t[BLUE]a[YELLOW]r[CYAN]t");
    // Text above will appear with 5 different colors given to all the selected letters
    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Monocraft.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśźżĄĆĘŁŃÓŚŹŻ";

        // basic default font
        parameter.size = 24;
        DEFAULT = generator.generateFont(parameter);
        DEFAULT.getData().markupEnabled = true;
        // bigger default font
        parameter.size = 48;
        TITLE = generator.generateFont(parameter);
        TITLE.getData().markupEnabled = true;

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