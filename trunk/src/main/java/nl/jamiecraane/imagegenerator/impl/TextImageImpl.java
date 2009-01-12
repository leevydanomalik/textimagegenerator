package nl.jamiecraane.imagegenerator.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

import nl.jamiecraane.imagegenerator.*;

/**
 * Implementation of the {@link TextImage} interface. The default font is
 * SansSerif, PLAIN, 12. The default color is black. Wrapping of text at the
 * right margin defaults to false.
 * The {@link GreedyTextWrapper} is used as the default text wrapping algorithm.
 * <p/>
 * Note: At the moment text wrapping is only implemented for the write methods, not the writeLine methods.
 * The default text aligment is RIGHT.
 * <p/>
 * This class is NOT threadsafe.
 */
public class TextImageImpl implements TextImage {
    private final int width;

    private final int height;

    private int xPos = 0;

    private int yPos = 0;

    private Style style = Style.PLAIN;

    private Alignment alignment = Alignment.LEFT;

    private Margin margin = new Margin();

    private boolean wrap = false;

    private TextWrapper wrapper = new GreedyTextWrapper();

    private final BufferedImage image;

    private final Graphics2D graphics;

    private Font previouslyUsedFont = new Font("SansSerif", Font.PLAIN, 12);

    private Color previouslyUsedColor = Color.BLACK;

    // Map containing the aligment algorithms
    private final Map<Alignment, Align> alignments;

    {
        alignments = new HashMap<Alignment, Align>();
        alignments.put(Alignment.LEFT, new LeftAlign());
        alignments.put(Alignment.RIGHT, new RightAlign());
        alignments.put(Alignment.CENTER, new Center());
        alignments.put(Alignment.JUSTIFY, new Justify());
    }

    public TextImageImpl(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.graphics = image.createGraphics();

        graphics.setBackground(new Color(255, 255, 255));
        graphics.setColor(new Color(0, 0, 0));

        graphics.clearRect(0, 0, width, height);
    }

    public TextImageImpl(int width, int height, Margin margin) {
        this(width, height);
        this.margin = margin;
        this.yPos = this.margin.getTop();
        this.xPos = this.margin.getLeft();
    }

    public TextImage useTextWrapper(TextWrapper wrapper) {
        this.wrapper = wrapper;
        return this;
    }

    public TextImage performAction(TextImageCallback callback) {
        callback.doWithGraphics(this.graphics);
        return this;
    }

    public TextImage addHSpace(int space) {
        this.xPos += space;
        return this;
    }

    /**
     * @see TextImage#wrap(boolean)
     */
    public TextImage wrap(boolean enable) {
        this.wrap = enable;
        return this;
    }

    public TextImage write(String text) {
        FontMetrics fm = getFontMetrics();

        if (this.wrap) {
            int lineWidth = this.width - this.margin.getLeft() - this.margin.getRight();
            List<String> lines = this.wrapper.doWrap(text, lineWidth, fm);
            for (String line : lines) {
                this.writeText(fm, line);
                this.applyStyle(fm, line);
                this.newLine();
            }
        } else {
            this.writeText(fm, text);
            this.applyStyle(fm, text);
        }

        return this;
    }

    private void applyStyle(FontMetrics fm, String line) {
        if (this.style.equals(Style.UNDERLINED)) {
            int y = this.yPos + fm.getHeight() + fm.getDescent() - 1;
            this.graphics.drawLine(this.xPos - fm.stringWidth(line), y, this.xPos, y);
        }
    }

    /**
     * Writes the given text on the Graphics object.
     *
     * @param fm   FontMetrics used to calculate the width of the text based on
     *             the font.
     * @param text The text to draw.
     */
    private void writeText(FontMetrics fm, String text) {
        int linewidth = this.width - this.margin.getLeft() - this.margin.getRight();
        List<DrawableText> words = this.alignments.get(this.alignment).align(text, fm, linewidth);
        for (DrawableText word : words) {
            graphics.drawString(word.getText(), this.xPos + word.getXPos(), this.yPos + fm.getHeight());
        }
        this.xPos = this.xPos + fm.stringWidth(text);
    }

    public TextImage write(String text, int yOffset) {
        int oldY = this.yPos;
        this.yPos += yOffset;
        this.write(text);

        // The specified yOffset is only temporary
        this.yPos = oldY;

        return this;
    }

    public TextImage writeLine(String text) {
        graphics.drawString(text, this.xPos, this.yPos + this.getFontMetrics().getHeight());
        this.newLine();

        return this;
    }

    private FontMetrics getFontMetrics() {
        return this.graphics.getFontMetrics(this.previouslyUsedFont);
    }

    public TextImage newLine() {
        this.yPos = this.yPos + getFontMetrics().getHeight();
        this.xPos = this.margin.getLeft();

        return this;
    }

    public TextImage newLine(int times) {
        this.yPos = this.yPos + (times * getFontMetrics().getHeight());
        this.xPos = this.margin.getLeft();

        return this;
    }

    public TextImage useFont(Font font) {
        this.previouslyUsedFont = font;

        this.graphics.setFont(font);

        return this;
    }

    public TextImage setTextAligment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public TextImage useFontStyle(Style style) {
        this.style = style;
        return this;
    }

    public TextImage useColor(Color color) {
        this.previouslyUsedColor = color;

        this.graphics.setColor(color);

        return this;
    }

    public TextImage useFontAndColor(Font font, Color color) {
        this.previouslyUsedFont = font;
        this.previouslyUsedColor = color;

        graphics.setFont(font);
        graphics.setColor(color);

        return this;
    }

    public TextImage write(Image image) {
        int iWidth = ((BufferedImage) image).getWidth();
        int iHeight = ((BufferedImage) image).getHeight();

        int y = this.yPos + getFontMetrics().getHeight() - iHeight;

        this.graphics.drawImage(image, this.xPos, y, null);
        this.xPos = this.xPos + iWidth;

        return this;
    }

    public TextImage write(Image image, int yOffset) {
        int oldY = this.yPos;
        this.yPos += yOffset;
        this.write(image);

        // The specified yOffset is only temporary
        this.yPos = oldY;

        return this;
    }

    public TextImage writeLine(Image image) {
        int iHeight = ((BufferedImage) image).getHeight();

        int y = this.yPos + getFontMetrics().getHeight() - iHeight;

        this.graphics.drawImage(image, this.xPos, y, null);
        this.newLine();
        return this;
    }

    public TextImage write(Image image, int x, int y) {
        this.graphics.drawImage(image, x, y, null);

        return this;
    }

    public void setMargin(Margin margin) {
        // TODO Should we allow to set the margins after a fully constructed textimage?
        this.xPos = margin.getTop();
        this.yPos = margin.getLeft();
        this.margin = margin;
    }

    public Font getCurrentFont() {
        return this.previouslyUsedFont;
    }

    public Color getCurrentColor() {
        return this.previouslyUsedColor;
    }

    public void createPng(OutputStream outputStream) throws IOException {
        ImageIO.write(this.image, "png", outputStream);
    }

    public void createJpg(OutputStream outputStream) throws IOException {
        ImageIO.write(this.image, "jpeg", outputStream);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
	}
}
