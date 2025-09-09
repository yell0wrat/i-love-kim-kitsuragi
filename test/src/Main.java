import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    private static final String IMAGE_PATH = "/home/rei/IdeaProjects/test/1200-33278323.jpeg";

    public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        //main screen (screen[0])
        GraphicsDevice mainScreen = screens[0];
        Rectangle mainScreenBounds = mainScreen.getDefaultConfiguration().getBounds();

        //multiple windows on main monitor only
        for (int i = 0; i < 8; i++) {
            createImageWindow(i, mainScreenBounds);
        }
    }

    private static void createImageWindow(int windowNumber, Rectangle screenBounds) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Window " + (windowNumber + 1));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //image load god
            ImageIcon originalIcon = new ImageIcon(IMAGE_PATH);
            Image image = originalIcon.getImage();

            //random transformations
            Random random = new Random();

            //random size variation
            double scale = 0.2 + random.nextDouble() * 0.4;
            int newWidth = (int)(image.getWidth(null) * scale);
            int newHeight = (int)(image.getHeight(null) * scale);

            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel label = new JLabel(scaledIcon);
            frame.add(label);
            frame.pack();

            // Calculate position to stay within main screen bounds
            int maxX = screenBounds.width - newWidth;
            int maxY = screenBounds.height - newHeight;

            // Ensure window stays within screen bounds
            int x = screenBounds.x + random.nextInt(Math.max(1, maxX));
            int y = screenBounds.y + random.nextInt(Math.max(1, maxY));

            frame.setLocation(x, y);
            frame.setVisible(true);
        });
    }
}