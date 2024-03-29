package com.pizzaMakerApp.utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * FontLoader is a utility class for loading custom fonts into a Java Swing application.
 * It provides methods to load a font from the resources directory.
 */
public class FontLoader {
    // Path to the directory where font files are stored
    private static final String FONTS_DIRECTORY = "/fonts/";

    /**
     * Loads a font from the specified file name within the font directory.
     * The font file should be located in the src/main/resources/fonts directory of the project.
     *
     * @param fontFileName The name of the font file (e.g., "MyFont.ttf")
     * @param size         The size of the font
     * @return The loaded Font object, or null if the font could not be loaded
     */
    public static Font loadFont(String fontFileName, float size) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream(FONTS_DIRECTORY + fontFileName);
            if (is == null) {
                throw new IOException("Font file not found: " + fontFileName);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font.deriveFont(size);
        } catch (IOException | FontFormatException e) {
            System.err.println("Could not load font " + fontFileName + ": " + e.getMessage());
        }
        return null; // Return null if font loading fails
    }
}
