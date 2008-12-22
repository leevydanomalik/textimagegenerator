package nl.jamiecraane.imagegenerator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;

/**
 * AuthorizationImage interface containing all methods for constructing images
 * with text based content.
 */
public interface TextImage {
	/**
	 * 
	 */
	TextImage useTextWrapper(TextWrapper wrapper);
	/**
	 * Method which accepts a TextImageCallback which makes the graphics object available for custom processing.
	 * @param callback
	 */
	TextImage performAction(TextImageCallback callback);
	/**
	 * Adds the specified horizontal space in pixels. Can be used for the
	 * aligment of elements when you need precise control over the positioning
	 * of elements.
	 * 
	 * @param space
	 *            The horizontal space in pixels.
	 * @return
	 */
	TextImage addHSpace(int space);

	/**
	 * Enables/disables the wrapping of text at the right end margin when the
	 * methods are used with no explicit newline, like {@link #write(String)}.
	 * 
	 * @param enable
	 *            true enables text wrapping, false disables text wrapping.
	 */
	TextImage wrap(boolean enable);

	/**
	 * @return The height of the image in pixels.
	 */
	int getHeight();

	/**
	 * @return The width of the image in pixels.
	 */
	int getWidth();

	/**
	 * Add text, in the image current font and color to the image.
	 * 
	 * @param text
	 *            The text to render.
	 * @return SignImage, to conveniently chain methods.
	 */
	TextImage write(String text);

	/**
	 * Add text, in the image current font and color to the image. The specified
	 * yAligment aligns the text.
	 * 
	 * @param text
	 *            The text to render.
	 * @param yOffset
	 * @return SignImage, to conveniently chain methods.
	 */
	TextImage write(String text, int yOffset);

	/**
	 * Add text, in the image current font and color to the image. Inserts a
	 * newline.
	 * 
	 * @param text
	 *            The text to render.
	 * @return SignImage, to conveniently chain methods.
	 */
	TextImage writeLine(String text);

	/**
	 * Adds the specified image, aligned with the text, to the image. The bottom
	 * of the the image is aligned with the beginning of the ascent of the font.
	 * 
	 * @param image
	 * @return
	 */
	TextImage write(Image image);

	/**
	 * Adds the specified image, aligned with the text, to the image. The bottom
	 * of the the image is aligned with the beginning of the ascent of the font.
	 * Aligns the image at the specified yOffset.
	 * 
	 * @param image
	 * @param yOffset
	 * @return
	 */
	TextImage write(Image image, int yOffset);

	/**
	 * Adds the specified image, aligned with the text, to the image. The bottom
	 * of the the image is aligned with the beginning of the ascent of the font.
	 * Adds a newline.
	 * 
	 * @param image
	 * @return
	 */
	TextImage writeLine(Image image);

	/**
	 * Adds an image at the specified x and y positions (absolute).
	 * 
	 * @param image
	 *            The image to be inserted.
	 * @param x
	 *            left position of teh image.
	 * @param y
	 *            top position of the image.
	 * @return SignImage, to conveniently chain methods.
	 */
	TextImage write(Image image, int x, int y);

	/**
	 * Inserts a newline based on the previously used dont. The previously used
	 * font is determined by the last call to the addText* methods with the
	 * given font. If none of these methods are used, no font is available yet,
	 * you can set one with the {@link #useFont(Font)} and
	 * {@link #useFontAndColor(Font, Color)} methods.
	 * 
	 * @return An instance of SignImage
	 */
	TextImage newLine();

	/**
	 * Inserts a newline based the given height.
	 * 
	 * @param heigth
	 *            The height of the newline.
	 * @return An instance of SignImage
	 */
	TextImage newLine(int height);

	/**
	 * Use the specified Font in all subsequent calls.
	 * 
	 * @param font
	 * @return SignImage, to conveniently chain methods.
	 */
	TextImage useFont(Font font);

	/**
	 * Set the fontstyle to be used.
	 * 
	 * @param style
	 * @return
	 */
	TextImage useFontStyle(Style style);

	/**
	 * Use the specified Font and color in all subsequent calls.
	 * 
	 * @param font
	 * @param color
	 * @return SignImage, to conveniently chain methods.
	 */
	TextImage useFontAndColor(Font font, Color color);

	/**
	 * Use the specified color in all subsequent calls.
	 * 
	 * @param color
	 * @return SignImage, to conveniently chain methods.
	 */
	TextImage useColor(Color color);
	/**
	 * @return The currently used font.
	 */
	Font getCurrentFont();
	/**
	 * @return The currently used color. 
	 */
	Color getCurrentColor();

    /**
     * Sets the text aligment.
     * @param alignment
     * @return
     */
    TextImage setTextAligment(Alignment alignment);
    /**
	 * Writes the content of the image, as png, to the specified
	 * {@link OutputStream}
	 * 
	 * @param outputStream
	 */
	void createPng(OutputStream outputStream) throws IOException;

	/**
	 * Writes the content of the image, as jpg, to the specified
	 * {@link OutputStream}
	 * 
	 * @param outputStream
	 */
	void createJpg(OutputStream outputStream) throws IOException;
}
