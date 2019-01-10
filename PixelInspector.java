package a8;

import java.io.IOException;

import javax.swing.JFrame;

public class PixelInspector {

	public static void main(String[] args) throws IOException {
		Picture p = A8Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp-in-namibia.jpg");
		PixelInspectorWidget simple_widget = new PixelInspectorWidget(p);

		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Simple Pixel Inspector");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main_frame.setContentPane(simple_widget);

		main_frame.pack();
		main_frame.setVisible(true);

	}

}
