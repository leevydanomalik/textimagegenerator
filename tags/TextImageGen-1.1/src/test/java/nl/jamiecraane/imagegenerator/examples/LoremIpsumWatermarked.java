package nl.jamiecraane.imagegenerator.examples;

import nl.jamiecraane.imagegenerator.Margin;
import nl.jamiecraane.imagegenerator.Style;
import nl.jamiecraane.imagegenerator.TextImage;
import nl.jamiecraane.imagegenerator.WaterMarkImageBuilder;
import nl.jamiecraane.imagegenerator.imageexporter.ImageType;
import nl.jamiecraane.imagegenerator.imageexporter.ImageWriter;
import nl.jamiecraane.imagegenerator.imageexporter.ImageWriterFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LoremIpsumWatermarked {
	public static void main(String[] args) throws IOException {
		new LoremIpsumWatermarked().runExample();
	}

	private void runExample() throws IOException {
		// 1. create the nl.jamiecraane.imagegenerator.examples.fonts used
		Font SansSerifBoldBig = new Font("SansSerif", Font.BOLD, 20);
		Font SansSerifBoldNormal = new Font("SansSerif", Font.BOLD, 12);
		Font SansSerifPlainNormal = new Font("SansSerif", Font.PLAIN, 12);
		
		// 2. Load in the image which is used as watermark.
		InputStream is = this.getClass().getResourceAsStream(
				"/nl/jamiecraane/imagegenerator/resources/images/watermark.png");
		BufferedImage watermark = ImageIO.read(is);
		
		// 3. Use the WaterMarkImageBuilder to create a textimage with the specified image as a watermark
		TextImage waterMarked = new WaterMarkImageBuilder(450, 300, new Margin(5, 5)).build(watermark);
		
		// 4. Specify nl.jamiecraane.imagegenerator.examples.fonts, text and color (last line)
		waterMarked.useFont(SansSerifBoldBig).writeLine("What is Lorem Ipsum?");
		waterMarked.useFont(SansSerifBoldNormal).write("Lorem Ipsum ").useFont(SansSerifPlainNormal).writeLine(
				"is simply dummy text of the printing and typesetting industry.");
		waterMarked.writeLine("Lorem Ipsum has been the industry's standard dummy text ever since");
		waterMarked.writeLine("the 1500s, when an unknown printer took a galley of type and");
		waterMarked.writeLine("scrambled it to make a type specimen book. It has survived not only");
		waterMarked.writeLine("five centuries, but also the leap into electronic typesetting,");
		waterMarked.writeLine("remaining essentially unchanged. It was popularised in the 1960s with");
		waterMarked.writeLine("the release of Letraset sheets containing Lorem Ipsum passages, and");
		// Underline
		waterMarked.useFontStyle(Style.UNDERLINED).write("more recently with desktop publishing software like Aldus PageMaker").newLine();
		// Use a red color
		waterMarked.useFontStyle(Style.PLAIN).useColor(Color.RED).writeLine("including versions of Lorem Ipsum.");

        ImageWriter imageWriter = ImageWriterFactory.getImageWriter(ImageType.PNG);
        imageWriter.writeImageToFile(waterMarked, new File("loremipsum-watermarked.png"));
	}
}
