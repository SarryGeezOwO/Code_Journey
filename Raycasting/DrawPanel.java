import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DrawPanel extends JPanel implements Runnable{

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int TILE_SIZE = 64;

    int mapX = 8;
    int mapY = 8;

    double playerX = 100;
    double playerY = 100;
    double pDeltaX;
    double pDeltaY;
    double pAngle;

    public static final double PI = Math.PI;
    public static final double PI2 = Math.PI / 2;
    public static final double PI3 = 3 * Math.PI / 2;
    public static final double DR = 0.0174533; // One degree in radians

    int[] grid = {
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 1, 0, 1,
            1, 0, 0, 0, 0, 1, 1, 1,
            1, 0, 3, 0, 0, 2, 0, 1,
            1, 0, 0, 1, 0, 0, 0, 1,
            1, 0, 0, 1, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 1, 1,
    };

    public DrawPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);

        pAngle -= 0.05;
        if(pAngle < 0) pAngle += 2 * PI;
        pDeltaX = Math.cos(pAngle) * 5;
        pDeltaY = Math.sin(pAngle) * 5;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        playerX += pDeltaX;
                        playerY += pDeltaY;
                        break;
                    case KeyEvent.VK_A:
                        pAngle -= 0.05;
                        if(pAngle < 0) pAngle += 2 * PI;
                        pDeltaX = Math.cos(pAngle) * 5;
                        pDeltaY = Math.sin(pAngle) * 5;
                        break;
                    case KeyEvent.VK_S:
                        playerX -= pDeltaX;
                        playerY -= pDeltaY;
                        break;
                    case KeyEvent.VK_D:
                        pAngle += 0.05;
                        if(pAngle > 2*PI) pAngle -= 2 * PI;
                        pDeltaX = Math.cos(pAngle) * 5;
                        pDeltaY = Math.sin(pAngle) * 5;
                        break;
                    default:
                        break;
                }
            }
        });

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(int x = 0; x < mapX; x++) {
            for(int y = 0; y < mapY; y++) {

                g.setColor((grid[x*mapX+y]) == 0 ? Color.BLACK : Color.WHITE);
                g.fillRect((y * TILE_SIZE), (x * TILE_SIZE), TILE_SIZE, TILE_SIZE);

            }
        }
        drawRay(g);
        drawPlayer(g);

    }

    public double dist(double ax, double ay, double bx, double by, double angle) {
        return (Math.sqrt( (bx-ax) * (bx - ax) + (by - ay) * (by - ay) ));
    }

    public void drawRay(Graphics g) {
        int r, mx, my, mp = 0, dof;
        double rx = 0, ry = 0, ra, xo = 0, yo = 0, disT = 0;

        ra = pAngle - DR*30;
        if(ra < 0) ra += 2*PI;
        if(ra > 2*PI) ra -= 2*PI;

        for(r=0; r < 60; r++) {
            // ------ Horizontal Check -------//
            dof = 0;
            double distH = 1000000000;
            double hx = playerX;
            double hy = playerY;
            double aTan = -1/Math.tan(ra);
            if(ra > PI) {
                ry = (((int)playerY >>> 6) << 6) - 0.0001;
                rx = (playerY - ry) * aTan + playerX;
                yo = -64;
                xo = -yo * aTan;
            }
            if(ra < PI) {
                ry = (((int)playerY >>> 6) << 6) + 64;
                rx = (playerY - ry) * aTan + playerX;
                yo = 64;
                xo = -yo * aTan;
            }
            if(ra==0 || ra == PI) {
                rx = playerX;
                ry = playerY;
                dof= 8;
            }

            while(dof < 8) {
                mx = (int)(rx) >>> 6;
                my = (int)(ry) >>> 6;
                mp = my * mapX + mx;

                if(mp > 0 && mp < mapX * mapY && grid[mp] > 0) {
                    hx = rx;
                    hy = ry;
                    distH = dist(playerX, playerY, hx, hy, ra);
                    dof = 8;
                }else {
                    rx += xo;
                    ry += yo;
                    dof+=1;
                }
            }

            // ------ Vertical Check -------//
            dof = 0;
            double distV = 1000000000;
            double vx = playerX;
            double vy = playerY;
            double nTan = -Math.tan(ra);
            if(ra > PI2 && ra < PI3) {
                rx = (((int)playerX >>> 6) << 6) - 0.0001;
                ry = (playerX - rx) * nTan + playerY;
                xo = -64;
                yo = -xo * nTan;
            }
            if(ra < PI2 || ra > PI3) {
                rx = (((int)playerX >>> 6) << 6) + 64;
                ry = (playerX - rx) * nTan + playerY;
                xo = 64;
                yo = -xo * nTan;
            }
            if(ra==0 || ra == PI) {
                rx = playerX;
                ry = playerY;
                dof= 8;
            }

            while(dof < 8) {
                mx = (int)(rx) >>> 6;
                my = (int)(ry) >>> 6;
                mp = my * mapX + mx;

                if(mp >0 && mp < mapX * mapY && grid[mp] > 0) {
                    vx = rx;
                    vy = ry;
                    distV = dist(playerX, playerY, vx, vy, ra);
                    dof = 8;
                }else {
                    rx += xo;
                    ry += yo;
                    dof+=1;
                }
            }

            if(distV < distH) {
                rx = vx;
                ry = vy;
                disT = distV;
                g.setColor(new Color(224, 224, 224));
            }
            if(distH < distV) {
                rx = hx;
                ry = hy;
                disT = distH;
                g.setColor(new Color(196, 196, 196));
            }

            g.drawLine((int) playerX, (int) playerY, (int) rx, (int) ry);

            //---- Draw Walls ---- //
            double ca = pAngle-ra;
            if(ca < 0)  ca += 2*PI;
            if(ca > 2*PI)  ca -= 2*PI;
            disT = disT * Math.cos(ca);

            double lineH = (TILE_SIZE * 600) / disT;
            if(lineH > 600) lineH = 600;
            double lineO = 200 - lineH/2;
            g.drawLine(r*4 + 640, (int) lineO, r*4 + 640, (int)(lineH+lineO));
            

            ra += DR;
            if(ra < 0) ra += 2*PI;
            if(ra > 2*PI) ra -= 2*PI;
        }
    }

    public void drawPlayer(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int) (playerX - 3), (int) (playerY - 3), 6, 6);

        g.setColor(Color.red);
        g.drawLine(
                (int) playerX,
                (int) playerY,
                (int)(playerX + pDeltaX * 4),
                (int)(playerY + pDeltaY * 4)
        );
    }

    @Override
    public void run() {
        while(true) {
            validate();
            repaint();
        }
    }
}
