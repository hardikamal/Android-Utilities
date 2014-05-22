package treeset.extensions.loaders;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for caching and loading fonts from assets directory.
 *
 * Created by daemontus on 03/04/14.
 */
public class TypefaceLoader {

    private static Map<String, Typeface> typefaces = new HashMap<String, Typeface>();

    /**
     * Get font from assets. Fonts are initialization is lazy, so font's are loaded only once during app lifecycle.
     *
     * @param name Filename of the font.
     * @param ctx Context providing assets.
     * @return Typeface from assets.
     */
    public static Typeface getTypeface(String name, Context ctx) {
        Typeface result = typefaces.get(name);
        if (result == null) {
            result = Typeface.createFromAsset(ctx.getAssets(), name);
            typefaces.put(name, result);
        }
        return result;
    }
}
