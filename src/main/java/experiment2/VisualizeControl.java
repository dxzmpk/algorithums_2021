package experiment2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class VisualizeControl {

    List<Pos> road;
    TheMap map = new TheMap();

    public class Grid extends JPanel {

        private List<Point> greyCells;

        public Grid() {
            greyCells = new ArrayList<>(25);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int[][] mapInts = map.map;
            for (int out = 0; out < mapInts.length; out++) {
                for (int in = 0; in < mapInts[0].length; in++) {
                    // 普通地形
                    int cellX = 20 + out * 20;
                    int cellY = 520 - ((in + 1) * 20);
                    if (mapInts[out][in] == 0) {
                        g.setColor(Color.WHITE);
                        g.fillRect(cellX, cellY, 20, 20);
                    } else if (mapInts[out][in] == Integer.MAX_VALUE) {
                        g.setColor(Color.BLACK);
                        g.fillRect(cellX, cellY, 20, 20);
                    } else if (mapInts[out][in] == 2){
                        g.setColor(Color.BLUE);
                        g.fillRect(cellX, cellY, 20, 20);
                    } else if (mapInts[out][in] == 4){
                        g.setColor(Color.ORANGE);
                        g.fillRect(cellX, cellY, 20, 20);
                    }
                }
            }
            for (Pos pos : road) {
                int cellX = 20 + pos.x * 20;
                int cellY = 520 - ((pos.y + 1) * 20);
                if (pos.left) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.magenta);
                }
                g.fillOval(cellX + 2, cellY + 2,16,16);
            }
            road.clear();
            road.add(map.source);
            road.add(map.target);
            for (Pos pos : road) {
                int cellX = 20 + pos.x * 20;
                int cellY = 520 - ((pos.y + 1) * 20);
                if (pos.equals(map.source)) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.RED);
                }
                g.fillRect(cellX, cellY, 20, 20);
            }
            g.setColor(Color.BLACK);
            g.drawRect(20, 20, 800, 500);

            for (int i = 20; i <= 800; i += 20) {
                g.drawLine(i, 20, i, 520);
            }

            for (int i = 20; i <= 500; i += 20) {
                g.drawLine(20, i, 820, i);
            }
        }

        public void fillCell(int x, int y) {
            greyCells.add(new Point(x, y));
            repaint();
        }

    }

    public void drawMapAndRoad(TheMap map, List<Pos> road) {
        this.map = map;
        this.road = road;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                Grid grid = new Grid();
                JFrame window = new JFrame();
                window.setSize(870, 600);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.add(grid);
                window.setVisible(true);
                grid.fillCell(1, 1);
            }
        });
    }
}
