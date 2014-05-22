package treeset.extensions.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import sk.dokostola.R;
import treeset.extensions.loaders.TypefaceLoader;

public class FontTextView extends TextView {

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, String font) {
        super(context);
        if (!isInEditMode()) {
            if (font != null && !font.isEmpty()) {
                this.setTypeface(TypefaceLoader.getTypeface(font + ".ttf", context));
            }
        }
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(attrs, context);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initFont(attrs, context);
    }

    private void initFont(AttributeSet attrs, Context ctx) {
        if(!isInEditMode()) {
            TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.FontTextView);
            if (a != null) {
                String font = a.getString(R.styleable.FontTextView_font);
                if (font != null && !font.isEmpty()) {
                    this.setTypeface(TypefaceLoader.getTypeface(font + ".ttf", ctx));
                }
                a.recycle();
            }
        }
    }
}
