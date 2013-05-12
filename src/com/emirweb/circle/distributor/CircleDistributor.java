package com.emirweb.circle.distributor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public final class CircleDistributor {

	private static final double IMAGE_RADIUS = 75;
	private static final double MAIN_IMAGE_RADIUS = 125;
	private static final double WIDTH = 1000;
	private static final double HEIGHT = 500;
	private static final double A = WIDTH / 2;
	private static final double B = HEIGHT / 2;
	private static final double H = A;
	private static final double L = B;
	private static final double CIRCUMFERENCE = getCircumference(A, B);
	private static final int NUMBER_OF_POINTS = 6;
	private static final double DISTANCE_BETWEEN_POINTS = A / 3;
	private static final double STARTING_DISTANCE = 0;// DISTANCE_BETWEEN_POINTS / 2;

	private static final String CSS_SELECTOR = "#WeddingPartyCell";

	public final static void main(final String[] args) {
		// System.out.println("A: " + A);
		// System.out.println("B: " + B);
		// System.out.println("CIRCUMFERENCE: " + CIRCUMFERENCE);
		// System.out.println("DISTANCE_BETWEEN_POINTS: " + DISTANCE_BETWEEN_POINTS);
		// System.out.println("STARTING_DISTANCE: " + STARTING_DISTANCE);

		final List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (int i = 0; i < NUMBER_OF_POINTS + 1; i++) {
			final double x = getIthX(i, true);
			final Coordinate coordinate = new Coordinate(x, getY(x, false));
			coordinates.add(coordinate);
		}

		for (int i = 1; i < NUMBER_OF_POINTS; i++) {
			final double x = getIthX(i, true);
			final Coordinate coordinate = new Coordinate(x, getY(x, true));
			coordinates.add(coordinate);
		}

		final File file = new File("/var/www/WeddingWebSite/CSS/WeddingPartyOval.css");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (final IOException ioException) {
				ioException.printStackTrace();
			}
		}
		if (!file.canWrite()) {
			System.err.println("Unable to write to file");
			return;
		}

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			for (final Coordinate coordinate : coordinates) {
				final int index = coordinates.indexOf(coordinate);
				outputStreamWriter.write(CSS_SELECTOR + index + " {\n");
				
				if (index == 6)
					outputStreamWriter.write("\tleft: " + (coordinate.getX() - MAIN_IMAGE_RADIUS) + "px;\n");
				else
					outputStreamWriter.write("\tleft: " + (coordinate.getX() - IMAGE_RADIUS) + "px;\n");

				if (index == 0 || index == 6)
					outputStreamWriter.write("\ttop: " + (coordinate.getY() - MAIN_IMAGE_RADIUS) + "px;\n");
				else
					outputStreamWriter.write("\ttop: " + (coordinate.getY() - IMAGE_RADIUS) + "px;\n");
				
				outputStreamWriter.write("}\n\n");
			}
			outputStreamWriter.flush();
		} catch (final IOException ioException) {
			ioException.printStackTrace();
		} finally {
			if (outputStreamWriter != null) {
				try {
					outputStreamWriter.close();
				} catch (final IOException ioException) {
					ioException.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (final IOException ioException) {
					ioException.printStackTrace();
				}
			}

		}
	}

	private static final double getIthX(final int i, final boolean withStartingPoint) {
		final double x = i * DISTANCE_BETWEEN_POINTS;
		if (withStartingPoint)
			return x + STARTING_DISTANCE;
		return x;
	}

	private static final double getY(final double x, final boolean under) {
		final double y = B / A * Math.sqrt((A - x + H) * (A + x - H));
		if (under)
			return -y + L;
		return y + L;

	}

	private static final double getCircumference(final double a, final double b) {
		return 2 * Math.PI * Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2)) / 2);
	}

	// d:= (x + (y - c)*a)/(1 + a^2)
	// x' = 2*d - x
	// y' = 2*d*a - y + 2c

	// private static final Coordinate reflect(final Coordinate coordinate){
	// final double D =
	// }

}
