package nl.jamiecraane.imagegenerator.examples;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;

import nl.jamiecraane.imagegenerator.Margin;
import nl.jamiecraane.imagegenerator.TextImage;
import nl.jamiecraane.imagegenerator.TextImageCallback;
import nl.jamiecraane.imagegenerator.Style;
import nl.jamiecraane.imagegenerator.impl.TextImageImpl;

/**
 * Simple example to demonstrate the creation of simple text-based images.
 */
public class Simple {
	public static void main(String[] args) throws Exception {
		new Simple().runExample();
	}

	private void runExample() throws Exception {
		// 1. create a new TextImageImpl with a size of 300x300 pixels
		// and a left and top margin of 5 pixels. The default font is SansSerif,
		// PLAIN,12
		TextImage textImage = new TextImageImpl(300, 300, new Margin(0, 0));

		// 2. These methods add text and a newline
		textImage.writeLine("This is a simple example");
		textImage.writeLine("of creating a textimage.");

		// 3. Add explicit newlines. All methods can be chained for convenience.
		textImage.newLine().newLine();

		// 4. Add other text
		textImage.useFontStyle(Style.UNDERLINED).write("Hello world!");

		// 5. Write the image as a png to a file
        textImage.createPng(new File("example1.png"));
	}
}
