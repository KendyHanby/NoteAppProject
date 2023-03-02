package com.sxi.notes.editor;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;

public class TextStyles {
    private SpannableString string;
    private int length;
    public static final int
            NORMAL = 0,
            BOLD = 1,
            ITALIC = 2,
            BOLDITALIC = 3,
            UNDER = 4,
            STRIKE = 5,
            FORE = 6,
            BACK = 7;

    public TextStyles() {
    }

    public TextStyles(String text) {
        string = new SpannableString(text);
        length = text.length();
    }

    public SpannableString getText() {
        return string;
    }

    public void setStyle(int style, int start, int end) {
        string.setSpan(new StyleSpan(style), start, end, 0);
    }

    public void setUnderStrike(int type, int start, int end) {
        switch (type) {
            case UNDER: {
                string.setSpan(new UnderlineSpan(), start, end, 0);
                break;
            }
            case STRIKE: {
                string.setSpan(new StrikethroughSpan(), start, end, 0);
                break;
            }
        }
    }

    public void setForeBack(int type, int color, int start, int end) {
        switch (type) {
            case FORE: {
                string.setSpan(new ForegroundColorSpan(color), start, end, 0);
                break;
            }
            case BACK: {
                string.setSpan(new BackgroundColorSpan(color), start, end, 0);
                break;
            }
        }
    }
}
