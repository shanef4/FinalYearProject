import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageMagicianOG {

	public static void main(String[] args) {

		int width = 1280;    //width of the image
		int height = 720;   //height of the image
		BufferedImage image = null;
		File f = null;

		//read image
		try{
			f = new File("uploaded_image.png"); //image file path
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(f);
		}catch(IOException e){
			System.out.println("Error: "+e);
		}

		//get pixel value
		Color mycolor = new Color(image.getRGB(1, 1));

		//get alpha
		int a = mycolor.getAlpha();

		//get red
		int refR = 190;

		//get green
		int refG = 200;

		//get blue
		int refB = 240;

		//get red
		int newR = mycolor.getRed();

		//get green
		int newG = mycolor.getGreen();

		//get blue
		int newB = mycolor.getBlue();

		BigDecimal divR = BigDecimal.valueOf(refR).divide(BigDecimal.valueOf(newR), 2, RoundingMode.HALF_EVEN);
		BigDecimal divG = BigDecimal.valueOf(refG).divide(BigDecimal.valueOf(newG), 2, RoundingMode.HALF_EVEN);
		BigDecimal divB = BigDecimal.valueOf(refB).divide(BigDecimal.valueOf(newB), 2, RoundingMode.HALF_EVEN);

		BigDecimal r = BigDecimal.valueOf(0.0d);
		BigDecimal g = BigDecimal.valueOf(0.0d);
		BigDecimal b = BigDecimal.valueOf(0.0d);

		int rFin = 0, gFin = 0, bFin = 0;

		//Loop through the image and set the color to red
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){

				//get pixel value
				Color mycolornew = new Color(image.getRGB(x, y));

				r = BigDecimal.valueOf(mycolornew.getRed());
				g = BigDecimal.valueOf(mycolornew.getGreen());
				b = BigDecimal.valueOf(mycolornew.getBlue());

				r = r.multiply(divR);
				g = g.multiply(divG);
				b = b.multiply(divB);

				BigDecimal rFract = r.remainder(BigDecimal.ONE);
				BigDecimal gFract = r.remainder(BigDecimal.ONE);
				BigDecimal bFract = r.remainder(BigDecimal.ONE);

				if(rFract.signum() >= 0.5){
					r = r.setScale(0, RoundingMode.UP);
				}
				else{
					r = r.setScale(0, RoundingMode.DOWN);
				}
				if(gFract.signum() >= 0.5){
					g = g.setScale(0, RoundingMode.UP);
				}
				else{
					g = g.setScale(0, RoundingMode.DOWN);
				}
				if(bFract.signum() >= 0.5){
					b = b.setScale(0, RoundingMode.UP);
				}
				else{
					b = b.setScale(0, RoundingMode.DOWN);
				}

				rFin = (int)r.intValueExact();
				if(rFin > 255)
					rFin = 255;

				gFin = (int)g.intValueExact();
				if(gFin > 255)
					gFin = 255;

				bFin = (int)b.intValueExact();
				if(bFin > 255)
					bFin = 255;

				mycolor = new Color(rFin, gFin, bFin, a);

				image.setRGB(x, y, mycolor.getRGB());
			}
		}

		//write image
		try{
			f = new File("changed.png");  //output file path
			ImageIO.write(image, "png", f);
		}catch(IOException e){
			System.out.println("Error: "+e);
		}
	}

}
