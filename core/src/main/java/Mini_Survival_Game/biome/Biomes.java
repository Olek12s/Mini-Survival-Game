package Mini_Survival_Game.biome;

import Mini_Survival_Game.biome.biomes.*;
import Mini_Survival_Game.utilities.noise.Noise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Mini_Survival_Game.level.chunk.ChunkManager.CHUNK_SIZE;

public class Biomes {

    ////////////////////////////////////////////////////
    // List of all existing biomes
    ////////////////////////////////////////////////////
    public static final List<Biome> biomeList = new ArrayList<>();

//    public static final Biome beach = new Beach();
//    public static final Biome desert = new Desert();
//    public static final Biome winter = new Winter();
    public static final Biome forest = new Forest();
    public static final Biome ocean = new Ocean();
    public static final Biome river = new River();

    ////////////////////////////////////////////////////
    // Assigned map colors for biomes
    ////////////////////////////////////////////////////


    public static void addBiome(Biome biome) {
        biomeList.add(biome);
    }


    ////////////////////////////////////////////////////
    // Interactive Biomes Preview
    ////////////////////////////////////////////////////
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BiomeViewer viewer = new BiomeViewer();
            viewer.setVisible(true);
        });
    }

    public static class BiomeViewer extends JFrame {
        private double zoom = 1.0;
        private double doubleOffsetX = 0;
        private double doubleOffsetY = 0;

        private int generatedOffsetX = 0;
        private int generatedOffsetY = 0;

        private long currentSeed = 0;

        private int dragOffsetX = 0;
        private int dragOffsetY = 0;
        private int lastMouseX, lastMouseY;

        private BufferedImage mapImage;
        private final int mapWidth = 800;
        private final int mapHeight = 600;

        private final JPanel mapPanel;
        private final JLabel coordsLabel;
        private final JTextField seedField;
        private final JCheckBox chunkGridCheck;

        private boolean isGenerating = false;
        private boolean generationPending = false;

        private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        private final int THREAD_CHUNK_SIZE = 64;

        public BiomeViewer() {
            setTitle("Biome creator");
            setSize(mapWidth + 16, mapHeight + 80);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            mapPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (mapImage != null) {
                        int drawW = (int) (mapImage.getWidth() * zoom);
                        int drawH = (int) (mapImage.getHeight() * zoom);

                        int subPixelOffsetX = (int) Math.round((generatedOffsetX - doubleOffsetX) * zoom);
                        int subPixelOffsetY = (int) Math.round((generatedOffsetY - doubleOffsetY) * zoom);

                        g.drawImage(mapImage, dragOffsetX + subPixelOffsetX, dragOffsetY + subPixelOffsetY, drawW, drawH, null);
                    }
                    else {
                        g.drawString("Generating map...", 50, 50);
                    }

                    if (chunkGridCheck != null && chunkGridCheck.isSelected()) {
                        g.setColor(new java.awt.Color(0, 0, 0, 60));

                        int startWorldX = (int) doubleOffsetX;
                        int startWorldY = (int) doubleOffsetY;

                        int endWorldX = startWorldX + (int)(getWidth() / zoom);
                        int endWorldY = startWorldY + (int)(getHeight() / zoom);

                        for (int x = startWorldX - 8; x <= endWorldX + 8; x++) {
                            if (x % 8 == 0) {
                                int screenX = (int) ((x - doubleOffsetX) * zoom) + dragOffsetX;
                                g.drawLine(screenX, 0, screenX, getHeight());
                            }
                        }
                        for (int y = startWorldY - 8; y <= endWorldY + 8; y++) {
                            if (y % 8 == 0) {
                                int screenY = (int) ((y - doubleOffsetY) * zoom) + dragOffsetY;
                                g.drawLine(0, screenY, getWidth(), screenY);
                            }
                        }
                    }
                }
            };
            mapPanel.setBackground(java.awt.Color.DARK_GRAY);
            add(mapPanel, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            controlPanel.add(new JLabel("Seed:"));
            seedField = new JTextField(String.valueOf(currentSeed), 10);
            controlPanel.add(seedField);

            JButton applySeedBtn = new JButton("Apply");
            applySeedBtn.addActionListener(e -> applySeed());
            controlPanel.add(applySeedBtn);

            JButton randomSeedBtn = new JButton("Random seed");
            randomSeedBtn.addActionListener(e -> {
                long newSeed = new Random().nextLong();
                seedField.setText(String.valueOf(newSeed));
                applySeed();
            });
            controlPanel.add(randomSeedBtn);

            chunkGridCheck = new JCheckBox("Show chunks grid");
            chunkGridCheck.addActionListener(e -> mapPanel.repaint());
            controlPanel.add(chunkGridCheck);

            coordsLabel = new JLabel("X: 0 Y: 0 | Zoom: 1.0x");
            controlPanel.add(coordsLabel);

            add(controlPanel, BorderLayout.NORTH);

            mapPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    lastMouseX = e.getX();
                    lastMouseY = e.getY();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    doubleOffsetX -= dragOffsetX / zoom;
                    doubleOffsetY -= dragOffsetY / zoom;

                    dragOffsetX = 0;
                    dragOffsetY = 0;
                    generateMapAsync();
                }
            });

            mapPanel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    dragOffsetX = e.getX() - lastMouseX;
                    dragOffsetY = e.getY() - lastMouseY;

                    int worldX = (int) ((e.getX() - dragOffsetX) / zoom + doubleOffsetX);
                    int worldY = (int) ((e.getY() - dragOffsetY) / zoom + doubleOffsetY);
                    updateCoordsLabel(worldX, worldY);

                    mapPanel.repaint();
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    int worldX = (int) (e.getX() / zoom + doubleOffsetX);
                    int worldY = (int) (e.getY() / zoom + doubleOffsetY);
                    updateCoordsLabel(worldX, worldY);
                }
            });

            mapPanel.addMouseWheelListener(e -> {
                double oldZoom = zoom;
                if (e.getWheelRotation() < 0) {
                    zoom *= 1.2;
                } else {
                    zoom /= 1.2;
                }

                zoom = Math.clamp(zoom, 0.1, 10.0);

                double mouseWorldX = (e.getX() - dragOffsetX) / oldZoom + doubleOffsetX;
                double mouseWorldY = (e.getY() - dragOffsetY) / oldZoom + doubleOffsetY;

                doubleOffsetX = mouseWorldX - (e.getX() - dragOffsetX) / zoom;
                doubleOffsetY = mouseWorldY - (e.getY() - dragOffsetY) / zoom;

                int worldX = (int) (e.getX() / zoom + doubleOffsetX);
                int worldY = (int) (e.getY() / zoom + doubleOffsetY);
                updateCoordsLabel(worldX, worldY);

                mapPanel.repaint();
                generateMapAsync();
            });

            generateMapAsync();
        }

        private void updateCoordsLabel(int x, int y) {
            String zoomText = String.format("%.1fx", zoom);
            coordsLabel.setText("X: " + x + "  Y: " + y + " | Zoom: " + zoomText);
        }

        private void applySeed() {
            try {
                currentSeed = Long.parseLong(seedField.getText());
                generateMapAsync();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Wrong seed format");
            }
        }

        private void generateMapAsync() {
            if (isGenerating) {
                generationPending = true;
                return;
            }
            isGenerating = true;
            generationPending = false;

            final int currentOffsetX = (int) Math.floor(doubleOffsetX);
            final int currentOffsetY = (int) Math.floor(doubleOffsetY);
            final double currentZoom = zoom;

            new Thread(() -> {
                int reqW = (int) Math.ceil(mapWidth / currentZoom) + 2;
                int reqH = (int) Math.ceil(mapHeight / currentZoom) + 2;

                final int finalReqWidth = Math.min(reqW, 5000);
                final int finalReqHeight = Math.min(reqH, 5000);

                Noise noise = new Noise(currentSeed, currentOffsetX, currentOffsetY, finalReqWidth, finalReqHeight);
                BufferedImage newMapImage = new BufferedImage(finalReqWidth, finalReqHeight, BufferedImage.TYPE_INT_RGB);

                int[] pixels = ((DataBufferInt) newMapImage.getRaster().getDataBuffer()).getData();

                List<Callable<Void>> tasks = new ArrayList<>();

                for (int startX = 0; startX < finalReqWidth; startX += THREAD_CHUNK_SIZE) {
                    for (int startY = 0; startY < finalReqHeight; startY += THREAD_CHUNK_SIZE) {
                        final int cx = startX;
                        final int cy = startY;
                        final int endX = Math.min(cx + THREAD_CHUNK_SIZE, finalReqWidth);
                        final int endY = Math.min(cy + THREAD_CHUNK_SIZE, finalReqHeight);

                        tasks.add(() -> {
                            for (int x = cx; x < endX; x++) {
                                for (int y = cy; y < endY; y++) {
                                    Biome b = matchBiome(noise, x, y);
                                    pixels[x + y * finalReqWidth] = getBiomeRGB(b);
                                }
                            }
                            return null;
                        });
                    }
                }

                try {
                    executor.invokeAll(tasks);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                SwingUtilities.invokeLater(() -> {
                    mapImage = newMapImage;
                    generatedOffsetX = currentOffsetX;
                    generatedOffsetY = currentOffsetY;

                    mapPanel.repaint();
                    isGenerating = false;

                    if (generationPending) {
                        generateMapAsync();
                    }
                });
            }).start();
        }

        /**
         * Selects the best matching biome for given world coordinates
         * based on temperature, height and humidity noise values.
         * @param noise source of procedural world data
         * @param x world x-coordinate
         * @param y world y-coordinate
         * @return best matching biome
         */
        public static Biome matchBiome(Noise noise, int x, int y) {
            Biome closest = null;
            //float maxWeight = -Float.MAX_VALUE;
            float maxWeight = Float.NEGATIVE_INFINITY;
            boolean oceanMatched = false;

            for (Biome biome : biomeList) {

                float weight = biome.getGenerationWeight(noise, x, y);
                if (weight > maxWeight) {
                    maxWeight = weight;
                    closest = biome;
                    if (biome == ocean) oceanMatched = true;
                }

                // Always generate ocean biome over river biome
                if (biome == river && oceanMatched) closest = ocean;
            }
            return closest;
        }

        /**
         * LibGDX Color -> AWT Color
         */
        private int getBiomeRGB(Biome b) {
            if (b == null || b.biomeColor == null) {
                return java.awt.Color.MAGENTA.getRGB();
            }

            return new java.awt.Color(
                    Math.round(b.biomeColor.r * 255),
                    Math.round(b.biomeColor.g * 255),
                    Math.round(b.biomeColor.b * 255),
                    Math.round(b.biomeColor.a * 255)
            ).getRGB();
        }
    }
}