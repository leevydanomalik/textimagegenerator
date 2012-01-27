package nl.jamiecraane.imagegenerator.examples;

import nl.jamiecraane.imagegenerator.Alignment;
import nl.jamiecraane.imagegenerator.Margin;
import nl.jamiecraane.imagegenerator.TextImage;
import nl.jamiecraane.imagegenerator.imageexporter.ImageType;
import nl.jamiecraane.imagegenerator.imageexporter.ImageWriter;
import nl.jamiecraane.imagegenerator.imageexporter.ImageWriterFactory;
import nl.jamiecraane.imagegenerator.impl.GreedyTextWrapper;
import nl.jamiecraane.imagegenerator.impl.TextImageImpl;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

public class TextWrapContinuousText {
	public static void main(String[] args) throws Exception {
		new TextWrapContinuousText().runExample();
	}

	private void runExample() throws Exception {
        TextImage textImage = new TextImageImpl(300, 550, new Margin(25, 5, 50, 0));

        InputStream is = this.getClass().getResourceAsStream(
				"/nl/jamiecraane/imagegenerator/examples/fonts/cour.ttf");
        Font usedFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(11.0f);

        textImage.useTextWrapper(new GreedyTextWrapper());
        textImage.setTextAligment(Alignment.LEFT);
        textImage.useFont(usedFont);

        textImage
				.wrap(true)
                .write(
						"Thisisalonglineofcontinuoustexttodemonstratehowthegreedytextwrapperworksinthiscase.").newLine();

        ImageWriter imageWriter = ImageWriterFactory.getImageWriter(ImageType.PNG);
        imageWriter.writeImageToFile(textImage, new File("textwrap-continuous.png"));
	}
}
