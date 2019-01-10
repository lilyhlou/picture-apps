package a8;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class FramePuzzleWidget extends JPanel implements MouseListener, KeyListener {
	private PictureView[][] picture_view;
	private int squareRow;
	private int squareColumn;

	public FramePuzzleWidget(Picture picture) {
		setLayout(new GridLayout(5, 5));
		picture_view = new PictureView[5][5];

		squareRow = 4; // current row of white square in array
		squareColumn = 4; // current column of white square in array

		int subpictureHeight = picture.getHeight() / 5;
		int subpictureWidth = picture.getWidth() / 5;

		int pixelHeightIndex = 0;
		// starts placing subpictures at [0,0] in picture_view
		int pixelWidthIndex = 0;

		for (int column = 0; column < 5; column++) {
			pixelHeightIndex = 0;
			for (int row = 0; row < 5; row++) {
				picture_view[row][column] = new PictureView(
						picture.extract(pixelWidthIndex, pixelHeightIndex, subpictureWidth, subpictureHeight)
								.createObservable());
				picture_view[row][column].addMouseListener(this);
				picture_view[row][column].addKeyListener(this);
				pixelHeightIndex = pixelHeightIndex + subpictureHeight;

			}
			pixelWidthIndex = pixelWidthIndex + subpictureWidth;
		}
		picture_view[squareRow][squareColumn]
				.setPicture((new PictureImpl(subpictureWidth, subpictureHeight).createObservable()));

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				add(picture_view[x][y]);

			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ObservablePicture updatingPic = picture_view[squareRow][squareColumn].getPicture();
		// stores white square
		for (int row = 0; row < 5; row++) { // checks if its in the same column
			if (e.getSource().equals(picture_view[row][squareColumn])) {
				ObservablePicture toSwap = picture_view[row][squareColumn].getPicture();
				// stores subpicture to replace the white sqaure
				picture_view[squareRow][squareColumn].setPicture(toSwap);
				picture_view[row][squareColumn].setPicture(updatingPic);
				squareRow = row;
				// sets row of white rectangle to row of rectangle which
				// replaced it
			}

		}

		for (int column = 0; column < 5; column++) {
			// checks if its in the same column
			if (e.getSource().equals(picture_view[squareRow][column])) {
				ObservablePicture toSwap = picture_view[squareRow][column].getPicture();
				picture_view[squareRow][squareColumn].setPicture(toSwap);
				picture_view[squareRow][column].setPicture(updatingPic);
				squareColumn = column;
				// sets column of white rectangle to row of rectangle which
				// replaced it

			}

		}

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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();

		ObservablePicture updatingPic = picture_view[squareRow][squareColumn].getPicture();

		switch (keyPressed) {
		case KeyEvent.VK_UP:
			if (squareRow - 1 >= 0) {
				ObservablePicture toSwap = picture_view[squareRow - 1][squareColumn].getPicture();
				picture_view[squareRow][squareColumn].setPicture(toSwap);
				picture_view[squareRow - 1][squareColumn].setPicture(updatingPic);

				squareRow--;
			}
			break;
		case KeyEvent.VK_DOWN:

			if (squareRow + 1 <= 4) {
				ObservablePicture toSwap = picture_view[squareRow + 1][squareColumn].getPicture();
				picture_view[squareRow][squareColumn].setPicture(toSwap);
				picture_view[squareRow + 1][squareColumn].setPicture(updatingPic);
				squareRow++;
			}
			break;
		case KeyEvent.VK_LEFT:

			if (squareColumn - 1 >= 0) {
				ObservablePicture toSwap = picture_view[squareRow][squareColumn - 1].getPicture();
				picture_view[squareRow][squareColumn].setPicture(toSwap);
				picture_view[squareRow][squareColumn - 1].setPicture(updatingPic);

				squareColumn--;
			}

			break;
		case KeyEvent.VK_RIGHT:

			if (squareColumn + 1 <= 4) {
				ObservablePicture toSwap = picture_view[squareRow][squareColumn + 1].getPicture();
				picture_view[squareRow][squareColumn].setPicture(toSwap);
				picture_view[squareRow][squareColumn + 1].setPicture(updatingPic);

				squareColumn++;
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
