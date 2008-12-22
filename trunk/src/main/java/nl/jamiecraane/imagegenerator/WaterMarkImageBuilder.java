package nl.jamiecraane.imagegenerator;

import java.awt.image.BufferedImage;

import nl.jamiecraane.imagegenerator.impl.TextImageImpl;

/**
 * Builder for creating image instances with a watermark.
 */
public class WaterMarkImageBuilder {
	private final int width;

	private final int heigth;

	private final Margin margin;

	public WaterMarkImageBuilder(int width, int heigth, Margin margin) {
		this.width = width;
		this.heigth = heigth;
		this.margin = margin;
	}

	/**
	 * Creates an image with a watermark.
	 * 
	 * @param waterMark The watermark used for the image.
	 * @return Watermarked image.
	 */
	public TextImage build(BufferedImage waterMark) {
		TextImageImpl image = new TextImageImpl(width, heigth);

		for (int x = 0; x < this.width; x += waterMark.getWidth()) {
			for (int y = 0; y < this.heigth; y += waterMark.getHeight()) {
				image.write(waterMark, x, y);
			}
		}

		image.setMargin(margin);

		return image;
	}

}
