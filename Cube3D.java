import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Cube3D extends JPanel implements KeyListener {
    private double[][] vertices = {
            {-1, -1, -1}, {1, -1, -1}, {1, 1, -1}, {-1, 1, -1}, // Задняя грань
            {-1, -1, 1}, {1, -1, 1}, {1, 1, 1}, {-1, 1, 1}      // Передняя грань
    };
    private int[][] edges = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0}, // Ребра задней грани
            {4, 5}, {5, 6}, {6, 7}, {7, 4}, // Ребра передней грани
            {0, 4}, {1, 5}, {2, 6}, {3, 7}  // Связи между гранями
    };

    private double angleX = 0, angleY = 0;

    public Cube3D() {
        JFrame frame = new JFrame("Cube3D");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.WHITE);

        // Центр экрана
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Проекция и рендеринг ребер
        for (int[] edge : edges) {
            int[] p1 = project(vertices[edge[0]]);
            int[] p2 = project(vertices[edge[1]]);
            g2d.drawLine(centerX + p1[0], centerY - p1[1], centerX + p2[0], centerY - p2[1]);
        }
    }

    private int[] project(double[] vertex) {
        double x = vertex[0];
        double y = vertex[1];
        double z = vertex[2];

        // Вращение
        double tempX = x * Math.cos(angleY) - z * Math.sin(angleY);
        z = x * Math.sin(angleY) + z * Math.cos(angleY);
        x = tempX;

        double tempY = y * Math.cos(angleX) - z * Math.sin(angleX);
        z = y * Math.sin(angleX) + z * Math.cos(angleX);
        y = tempY;

        // Проекция на 2D
        double scale = 400 / (z + 5);
        int projX = (int) (x * scale);
        int projY = (int) (y * scale);
        return new int[]{projX, projY};
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) angleY -= 0.1;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) angleY += 0.1;
        if (e.getKeyCode() == KeyEvent.VK_UP) angleX -= 0.1;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) angleX += 0.1;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Cube3D();
    }
}
