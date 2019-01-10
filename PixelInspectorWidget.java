package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorWidget extends JPanel implements MouseListener {

	private PictureView picture_view;
	private JLabel x_coord;
	private JLabel y_coord;
	private JLabel red;
	private JLabel green;
	private JLabel blue;
	private JLabel brightness;

	public PixelInspectorWidget(Picture picture) {
		setLayout(new BorderLayout());

		JPanel labels = new JPanel();
		JPanel pic = new JPanel();

		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);

		pic.add(picture_view);
		add(labels, BorderLayout.WEST);
		add(pic, BorderLayout.EAST);
		labels.setLayout(new GridLayout(6, 1));
		labels.setAlignmentX(LEFT_ALIGNMENT);
		labels.setAlignmentY(CENTER_ALIGNMENT);

		x_coord = new JLabel("X: ");
		labels.add(x_coord);

		y_coord = new JLabel("Y: ");
		labels.add(y_coord);

		red = new JLabel("Red: ");
		labels.add(red);

		green = new JLabel("Green: ");
		labels.add(green);

		blue = new JLabel("Blue: ");
		labels.add(blue);

		brightness = new JLabel("Brightness:           ");
		labels.add(brightness);

		labels.setAlignmentY(CENTER_ALIGNMENT);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x_coord.setText("X: " + e.getX());
		y_coord.setText("Y: " + e.getY());

		// Math.floor gets the double to the hundredths place
		red.setText("Red: " + Math.floor(picture_view.getPicture().getPixel(e.getX(), e.getY()).getRed() * 100) / 100);
		green.setText(
				"Green: " + Math.floor(picture_view.getPicture().getPixel(e.getX(), e.getY()).getGreen() * 100) / 100);
		blue.setText(
				"Blue: " + Math.floor(picture_view.getPicture().getPixel(e.getX(), e.getY()).getBlue() * 100) / 100);
		brightness.setText("Brightness: "
				+ Math.floor(picture_view.getPicture().getPixel(e.getX(), e.getY()).getIntensity() * 100) / 100);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
