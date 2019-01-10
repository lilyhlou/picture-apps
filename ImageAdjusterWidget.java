package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjusterWidget extends JPanel implements ChangeListener {
	private PictureView picture_view;
	private Picture pict;
	private JSlider blur;
	private JSlider saturation;
	private JSlider brightness;

	public ImageAdjusterWidget(Picture picture) {
		setLayout(new BorderLayout());
		pict = picture;
		picture_view = new PictureView(pict.createObservable());
		JPanel pic = new JPanel();
		pic.add(picture_view);

		// creates jpanel of three sliders
		JPanel sliders = new JPanel();
		sliders.setLayout(new GridLayout(3, 1));
		// creates blur slider
		blur = new JSlider(0, 5, 0);
		blur.setPaintTicks(true);
		blur.setPaintLabels(true);
		blur.setMajorTickSpacing(1);
		blur.setSnapToTicks(true);
		// panel with blur slider + labels
		JPanel blurPanel = new JPanel();
		blurPanel.setLayout(new BorderLayout());
		blurPanel.add(new JLabel("Blur: "), BorderLayout.WEST);
		blurPanel.add(blur, BorderLayout.CENTER);
		sliders.add(blurPanel);

		// creates saturation slider
		saturation = new JSlider(-100, 100, 0);
		saturation.setPaintTicks(true);
		saturation.setPaintLabels(true);
		saturation.setMajorTickSpacing(25);
		// jpanel with saturation slider and labels
		JPanel saturationPanel = new JPanel();
		saturationPanel.setLayout(new BorderLayout());
		saturationPanel.add(new JLabel("Saturation: "), BorderLayout.WEST);
		saturationPanel.add(saturation, BorderLayout.CENTER);
		sliders.add(saturationPanel);

		// brightness slider
		brightness = new JSlider(-100, 100, 0);
		brightness.setPaintTicks(true);
		brightness.setPaintLabels(true);
		brightness.setMajorTickSpacing(25);

		// jpanel with brightness slider and label
		JPanel brightnessLabel = new JPanel();
		brightnessLabel.setLayout(new BorderLayout());
		brightnessLabel.add(new JLabel("Brightness: "), BorderLayout.WEST);
		brightnessLabel.add(brightness, BorderLayout.CENTER);
		sliders.add(brightnessLabel);

		add(picture_view, BorderLayout.NORTH);
		add(sliders, BorderLayout.SOUTH);

		blur.addChangeListener(this);
		saturation.addChangeListener(this);
		brightness.addChangeListener(this);

	}

	public PictureImpl lighten(double factor, Picture pic) {
		// weight is the number on slider / 100 since rgb values are out of 1.00

		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Factor out of bounds");
		}
		Pixel whitePixel = new ColorPixel(1, 1, 1); // white pixel
		PictureImpl lighterVersion = new PictureImpl(pic.getWidth(), pic.getHeight());

		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				Pixel currentpixel = pic.getPixel(x, y);
				lighterVersion.setPixel(x, y, currentpixel);
			}
		}
		// makes clone of current picture object, lighterVersion

		for (int x = 0; x < lighterVersion.getWidth(); x++) {
			for (int y = 0; y < lighterVersion.getHeight(); y++) {
				Pixel currentpixel = lighterVersion.getPixel(x, y);
				Pixel currentpixelcolor = new ColorPixel(currentpixel.getRed(), currentpixel.getGreen(),
						currentpixel.getBlue());
				Pixel lighterPixel = currentpixelcolor.blend(whitePixel, 1 - factor);
				lighterVersion.setPixel(x, y, lighterPixel);

			}
		}

		return lighterVersion;
	}

	private PictureImpl darken(double factor, Picture pic) {
		// weight is the number on slider / 100 * -1 since rgb values are out of
		// 1.00 and darkness is negative on the slider

		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Factor out of bounds");
		}

		Pixel blackPixel = new ColorPixel(0, 0, 0); // black pixel
		PictureImpl darkerVersion = new PictureImpl(pic.getWidth(), pic.getHeight());

		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				Pixel currentpixel = pic.getPixel(x, y);
				darkerVersion.setPixel(x, y, currentpixel);
			}
		}
		// makes clone of current picture object, darkerVersion

		for (int x = 0; x < darkerVersion.getWidth(); x++) {
			for (int y = 0; y < darkerVersion.getHeight(); y++) {
				Pixel currentpixel = darkerVersion.getPixel(x, y);
				Pixel currentpixelcolor = new ColorPixel(currentpixel.getRed(), currentpixel.getGreen(),
						currentpixel.getBlue());
				Pixel darkerPixel = currentpixelcolor.blend(blackPixel, 1 - factor);
				darkerVersion.setPixel(x, y, darkerPixel);

			}
		}

		return darkerVersion;
	}

	private PictureImpl blur(int blurSpan, Picture pic) {

		PictureImpl blurryPic = new PictureImpl(pic.getWidth(), pic.getHeight());

		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				Pixel currentpixel = pic.getPixel(x, y);
				blurryPic.setPixel(x, y, currentpixel);
			}
		}
		// makes clone of current picture object

		// if blurSpan is 0, return current pic
		if (blurSpan == 0) {
			return blurryPic;
		}

		for (int x = 0; x < blurryPic.getWidth(); x++) {
			for (int y = 0; y < blurryPic.getHeight(); y++) {
				// averages rgb values for each pixel in blurryPic
				double newRed = 0;
				double newGreen = 0;
				double newBlue = 0;
				int pixelsBlurred = 0;

				for (int pixelX = x - blurSpan; pixelX < x + blurSpan; pixelX++) {
					for (int pixelY = y - blurSpan; pixelY < y + blurSpan; pixelY++) {
						try {
							Pixel currentPixel = pic.getPixel(pixelX, pixelY);
							pixelsBlurred++;
							newRed = currentPixel.getRed() + newRed;
							newGreen = currentPixel.getGreen() + newGreen;
							newBlue = currentPixel.getBlue() + newBlue;

						} catch (RuntimeException e) {
							// does nothing if out of bounds
						}

					}
				}
				newRed = newRed / pixelsBlurred;
				newGreen = newGreen / pixelsBlurred;
				newBlue = newBlue / pixelsBlurred;
				Pixel blurredPixel = new ColorPixel(newRed, newGreen, newBlue);
				blurryPic.setPixel(x, y, blurredPixel);

			}

		}
		return blurryPic;

	}

	private PictureImpl saturationNegative(int saturationLevel, Picture pic) {
		PictureImpl newPic = new PictureImpl(pic.getWidth(), pic.getHeight());

		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				Pixel currentpixel = pic.getPixel(x, y);
				newPic.setPixel(x, y, currentpixel);
			}
		}
		// makes clone of current picture object

		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				// loops through newPic to reset pixel values
				Pixel currentpixel = pic.getPixel(x, y);
				double redValue = currentpixel.getRed();
				double greenValue = currentpixel.getGreen();
				double blueValue = currentpixel.getBlue();
				double pixelIntensity = currentpixel.getIntensity();
				redValue = redValue * (1.0 + (saturationLevel / 100.0)) - (pixelIntensity * saturationLevel / 100.0);
				greenValue = greenValue * (1.0 + (saturationLevel / 100.0))
						- (pixelIntensity * saturationLevel / 100.0);
				blueValue = blueValue * (1.0 + (saturationLevel / 100.0)) - (pixelIntensity * saturationLevel / 100.0);
				currentpixel = new ColorPixel(redValue, greenValue, blueValue);
				newPic.setPixel(x, y, currentpixel);
			}
		}

		return newPic;
	}

	private PictureImpl saturationPositive(int saturationLevel, Picture pic) {
		PictureImpl newPic = new PictureImpl(pic.getWidth(), pic.getHeight());

		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				Pixel currentpixel = pic.getPixel(x, y);
				newPic.setPixel(x, y, currentpixel);
			}
		}
		// makes clone of current picture object

		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				// loops through newPic to reset pixel values
				Pixel currentpixel = pic.getPixel(x, y);
				double redValue = currentpixel.getRed();
				double greenValue = currentpixel.getGreen();
				double blueValue = currentpixel.getBlue();
				double a;
				if (redValue > greenValue && redValue > blueValue) {
					a = redValue;
				} else if (greenValue > blueValue) {
					a = greenValue;
				} else {
					a = blueValue;
				}

				if (a == 0) {
					newPic.setPixel(x, y, currentpixel);
					// if pixel is black
				} else {
					redValue = redValue * ((a + ((1.0 - a) * (saturationLevel / 100.0))) / a);
					greenValue = greenValue * ((a + ((1.0 - a) * (saturationLevel / 100.0))) / a);
					blueValue = blueValue * ((a + ((1.0 - a) * (saturationLevel / 100.0))) / a);
					currentpixel = new ColorPixel(redValue, greenValue, blueValue);
					newPic.setPixel(x, y, currentpixel);
				}
			}
		}

		return newPic;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Picture changedPic = new PictureImpl(pict.getWidth(), pict.getHeight());

		for (int x = 0; x < pict.getWidth(); x++) {
			for (int y = 0; y < pict.getHeight(); y++) {
				Pixel currentpixel = pict.getPixel(x, y);
				changedPic.setPixel(x, y, currentpixel);
			}
		}
		// clones picture
		int blurSpan = blur.getValue();
		changedPic = blur(blurSpan, changedPic);

		int saturationLevel = saturation.getValue();
		if (saturationLevel < 0) {
			changedPic = saturationNegative(saturationLevel, changedPic);
		} else {
			changedPic = saturationPositive(saturationLevel, changedPic);

		}

		double brightnessValue = brightness.getValue();
		double factor;

		if (brightnessValue > 0) {
			factor = brightnessValue / 100;
			changedPic = lighten(1 - factor, changedPic);
			picture_view.setPicture(changedPic.createObservable());
			// 1.0 means that the result will be the same as the current
			// object while a weight of 0.0 means that the result will be the
			// same as a white pixel

		} else {
			factor = brightnessValue / 100 * -1;
			changedPic = darken(1 - factor, changedPic);
			picture_view.setPicture(changedPic.createObservable());

			// 1.0 means that the result will be the same as the current
			// object while a weight of 0.0 means that the result will be the
			// same as a black pixel
		}
	}

}
