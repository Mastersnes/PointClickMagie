package com.bebel.game.components.refound;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS;

/**
 * Extention de FreeTypeFontParameter
 */
public class FontParameter extends FreeTypeFontGenerator.FreeTypeFontParameter {
    public FontParameter(){
        final StringBuilder allChars = new StringBuilder(DEFAULT_CHARS);
        allChars.append("ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸ");
        allChars.append("àáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüýÿ");
        allChars.append("¢ß¥£™©®ª×÷±²³¼½¾µ¿¶·¸º°¯§…¤¦≠¬ˆ¨‰");
        allChars.append("$€@%£¤");
        allChars.append("ĴĵĜĝŜŝĤĥŬŭĈĉû");
        characters = allChars.toString();
    }
    public FontParameter(final int size, final Color color) {
        this();
        this.size = size;
        if (color == null) this.color = Color.BLACK.cpy();
        else this.color = color;
    }
    public FontParameter(final int size, final Color color, final int borderWith, final Color borderColor) {
        this(size, color);
        this.borderWidth = borderWith;
        if (borderColor == null) this.borderColor = Color.BLACK.cpy();
        else this.borderColor = borderColor;
    }
    public FontParameter(final int size, final Color color, final int shadowX, final int shadowY, final Color shadowColor) {
        this(size, color);
        this.shadowOffsetX = shadowX;
        this.shadowOffsetY = shadowY;
        this.shadowColor = shadowColor;
    }
    public FontParameter(final int size, final Color color, final int borderWith, final Color borderColor, final int shadowX, final int shadowY, final Color shadowColor) {
        this(size, color, borderWith, borderColor);
        this.shadowOffsetX = shadowX;
        this.shadowOffsetY = shadowY;
        this.shadowColor = shadowColor;
    }

    /**
     * Renvoi un code en fonction des attributs
     * @return
     */
    public String getCode() {
        final StringBuilder builder = new StringBuilder();
        builder.append(size);
        builder.append(color);

        builder.append(borderWidth);
        builder.append(borderColor);

        builder.append(shadowOffsetX);
        builder.append(shadowOffsetY);
        builder.append(shadowColor);
        return builder.toString();
    }
}
