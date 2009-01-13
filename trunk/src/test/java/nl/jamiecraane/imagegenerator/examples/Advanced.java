package nl.jamiecraane.imagegenerator.examples;

import nl.jamiecraane.imagegenerator.*;
import nl.jamiecraane.imagegenerator.impl.TextImageImpl;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * Author: Jamie Craane
 * Date: 21-dec-2008
 * Time: 22:12:02
 */
public class Advanced {
    public static void main(String[] args) throws Exception {
        Advanced advanced = new Advanced();
        advanced.runExample();
    }

    private void runExample() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(
				"/nl/jamiecraane/imagegenerator/resources/images/warning2.gif");
		BufferedImage warningSmall = ImageIO.read(is);
        is = this.getClass().getResourceAsStream(
				"/nl/jamiecraane/imagegenerator/resources/images/exclamation_triangle_green.png");
		BufferedImage warningBig = ImageIO.read(is);

        TextImage textImage = new TextImageImpl(450, 400, new Margin(5, 5));

        textImage.wrap(true).write("This is an example to show some of the more advanced capabilities which you can do with the text image generator.");
        textImage.wrap(false).write("Here we embed an image ").write(warningSmall).writeLine(" embedded in the text.");
        textImage.write("The following image has a vertical offset ").write(warningSmall, 2).newLine();
        textImage.write(warningBig, 50, 80);
        textImage.newLine(10);
        textImage.wrap(true).setTextAligment(Alignment.RIGHT).write("Here we set wrap back to true and specified a textaligment of right. This has the effect that this text is aligned right. Also note that the newLine method accept a number of times that this method must execute a newline. The following demonstrates the use of the TextImageCallback interface which draws an arc.").newLine(4);

        textImage.performAction(new TextImageCallback() {
            public void doWithGraphics(Graphics2D graphics) {
                graphics.drawArc(100, 300, 200, 100, 0, 180);
            }
        });

        FileOutputStream fos = new FileOutputStream(new File("advanced.png"));
		try {
			textImage.createPng(fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
    }
}
