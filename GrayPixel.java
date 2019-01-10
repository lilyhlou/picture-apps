package a8;

public class GrayPixel implements Pixel {

	private double intensity;

	private static final char[] PIXEL_CHAR_MAP = { '#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ' };

	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

	@Override
	public double getBlue() {
		return getIntensity();
	}

	@Override
	public double getGreen() {
		return getIntensity();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity() * 10.0)];
	}

	public Pixel blend(Pixel p, double weight) {
		if (weight < 0.0 || weight > 1.0) {
			throw new RuntimeException("Explanation string");
		}
		double newIntensity = (weight * p.getIntensity()) + ((1 - weight) * this.intensity);
		Pixel blendPixel = new GrayPixel(newIntensity);
		return blendPixel;
	}

}
