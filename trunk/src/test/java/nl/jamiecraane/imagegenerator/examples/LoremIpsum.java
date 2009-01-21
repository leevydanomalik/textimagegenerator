package nl.jamiecraane.imagegenerator.examples;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;

import nl.jamiecraane.imagegenerator.Margin;
import nl.jamiecraane.imagegenerator.Style;
import nl.jamiecraane.imagegenerator.TextImage;
import nl.jamiecraane.imagegenerator.impl.TextImageImpl;

public class LoremIpsum {
	public static void main(String[] args) throws Exception {
		new LoremIpsum().runExample();
	}
	
	private void runExample() throws Exception {
		// 1. create the fonts used
		Font SansSerifBoldBig = new Font("SansSerif", Font.BOLD, 20);
		Font SansSerifBoldNormal = new Font("SansSerif", Font.BOLD, 12);
		Font SansSerifPlainNormal = new Font("SansSerif", Font.PLAIN, 12);

		// 2. Create a new textimage of 450x300 pixels
		TextImage textImage = new TextImageImpl(450, 300, new Margin(15, 0));

		// 3. Specify fonts, text and color (last line)
		textImage.useFont(SansSerifBoldBig).writeLine("What is Lorem Ipsum?");
		textImage.useFont(SansSerifBoldNormal).write("Lorem Ipsum ").useFont(SansSerifPlainNormal).writeLine(
				"is simply dummy text of the printing and typesetting industry.");
		textImage.writeLine("Lorem Ipsum has been the industry's standard dummy text ever since");
		textImage.writeLine("the 1500s, when an unknown printer took a galley of type and");
		textImage.writeLine("scrambled it to make a type specimen book. It has survived not only");
		textImage.writeLine("five centuries, but also the leap into electronic typesetting,");
		textImage.writeLine("remaining essentially unchanged. It was popularised in the 1960s with");
		textImage.writeLine("the release of Letraset sheets containing Lorem Ipsum passages, and");
		// Underline
		textImage.useFontStyle(Style.UNDERLINED).write("more recently with desktop publishing software like Aldus PageMaker").newLine();
		// Use a red color
		textImage.useFontStyle(Style.PLAIN).useColor(Color.RED).writeLine("including versions of Lorem Ipsum.");

		// 5. Write the image as a png to an outputstream
		FileOutputStream fos = new FileOutputStream(new File("loremipsum.png"));
		try {
			textImage.createPng(fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
}
