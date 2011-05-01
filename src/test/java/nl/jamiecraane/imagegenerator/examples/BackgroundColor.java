package nl.jamiecraane.imagegenerator.examples;

import nl.jamiecraane.imagegenerator.Margin;
import nl.jamiecraane.imagegenerator.Style;
import nl.jamiecraane.imagegenerator.TextImage;
import nl.jamiecraane.imagegenerator.TextImageCallback;
import nl.jamiecraane.imagegenerator.impl.TextImageImpl;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * User: Jamie Craane
 */
public class BackgroundColor {
    public static void main(String[] args) throws Exception {
        new BackgroundColor().runExample();
    }

    private void runExample() throws Exception {
        // 1. create a new TextImageImpl with a size of 300x300 pixels
		// and a left and top margin of 5 pixels. The default font is SansSerif,
		// PLAIN,12
		final TextImage textImage = new TextImageImpl(300, 300, new Margin(0, 0));

        textImage.performAction(new TextImageCallback() {
            public void doWithGraphics(Graphics2D graphics) {
                // Set the color for the background
                graphics.setColor(Color.CYAN);
                // Draw a rectangle to apply the given background
                graphics.fillRect(0, 0, textImage.getWidth(), textImage.getHeight());
                // set the color back to black for the text
                graphics.setColor(Color.BLACK);
            }
        });

		// 2. These methods add text and a newline
		textImage.writeLine("This is an example");
		textImage.writeLine("of creating a textimage");
        textImage.writeLine("with an alternative backgroundcolor.");

		// 3. Add explicit newlines. All methods can be chained for convenience.
		textImage.newLine().newLine();

		// 4. Add other text
		textImage.useFontStyle(Style.UNDERLINED).write("Hello world!");

		// 5. Write the image as a png to an outputstream
		FileOutputStream fos = new FileOutputStream(new File("background-color.png"));
		try {
			textImage.createPng(fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
    }
}
