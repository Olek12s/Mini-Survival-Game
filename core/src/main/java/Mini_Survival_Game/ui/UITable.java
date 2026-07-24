package Mini_Survival_Game.ui;

import Mini_Survival_Game.utilities.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UITable extends UIElement {
    private int cols;
    private int rows;

    private UIElement[][] grid;

    private Color tableBgColor;
    private Color tableBorderColor;
    private float tableBorderThickness = 1f;

    private CellStyle[][] cellStyles;

    private static class CellStyle {
        public Color bgColor = null;
        public Color borderColor = null;
        public float borderThickness = 1f;
    }

    public UITable(float x, float y, float width, float height, int rows, int cols) {
        super(x, y, width, height);
        this.rows = rows;
        this.cols = cols;
        this.cellStyles = new CellStyle[rows][cols];
        this.grid = new UIElement[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cellStyles[r][c] = new CellStyle();
            }
        }
    }

    public void addElementAt(UIElement element, int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Grid indexes are out of bounds.");
        }

        // replace already existing element
        if (grid[row][col] != null) {
            elementList.remove(grid[row][col]);
        }
        grid[row][col] = element;

        if (element != null) {
            addElement(element);
        }

        // Recalculate layout after adding new element
        updateLayout();
    }

    private void updateLayout() {
        float cellWidth = getInnerWidth() / cols;
        float cellHeight = getInnerHeight() / rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                UIElement el = grid[row][col];
                if (el != null) {
                    // Position are relative to left-bottom corner of table + inner table's padding
                    float cx = paddingLeft + (col * cellWidth);
                    float cy = paddingBottom + (row * cellHeight);

                    // Available space in cell shrunk by outer margins of element
                    float availableW = cellWidth - el.getMarginLeft() - el.getMarginRight();
                    float availableH = cellHeight - el.getMarginTop() - el.getMarginBottom();

                    float eW = el.getWidth();
                    float eH = el.getHeight();

                    // If element has width/height equal 0, fill whole avaiable space
                    if (eW <= 0) eW = availableW;
                    if (eH <= 0) eH = availableH;

                    // Clamp to avaiable to not go out of cell
                    eW = Math.min(eW, availableW);
                    eH = Math.min(eH, availableH);

                    el.setWidth(eW);
                    el.setHeight(eH);

                    // Position based on margin and RelativePosition
                    float targetX = cx + el.getMarginLeft();
                    float targetY = cy + el.getMarginBottom();

                    // Horizontal alignment
                    switch (el.getAlignment()) {
                        case TOP:
                        case MID:
                        case BOT:
                            targetX += (availableW - eW) / 2f;
                            break;
                        case TOP_RIGHT:
                        case MID_RIGHT:
                        case BOT_RIGHT:
                            targetX += (availableW - eW);
                            break;
                        default: // LEFT zostaje bez zmian
                            break;
                    }

                    // Vertical alignment
                    switch (el.getAlignment()) {
                        case MID_LEFT:
                        case MID:
                        case MID_RIGHT:
                            targetY += (availableH - eH) / 2f;
                            break;
                        case TOP_LEFT:
                        case TOP:
                        case TOP_RIGHT:
                            targetY += (availableH - eH);
                            break;
                        default: // BOT
                            break;
                    }

                    el.setX(targetX);
                    el.setY(targetY);
                }
            }
        }
    }

    public void setTableStyle(Color bgColor, Color borderColor, float borderThickness) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                setCellStyle(row, col, bgColor, borderColor, borderThickness);
            }
        }
    }

    public void setCellStyle(int row, int col, Color bgColor, Color borderColor, float borderThickness) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            cellStyles[row][col].bgColor = bgColor;
            cellStyles[row][col].borderColor = borderColor;
            cellStyles[row][col].borderThickness = borderThickness;
        }
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        updateLayout();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        updateLayout();
    }

    @Override
    protected void renderElement(SpriteBatch batch) {
        float ax = getAbsoluteX();
        float ay = getAbsoluteY();
        Texture tex = Renderer.getPixel();

        // Draw background of the table
        if (tableBgColor != null) {
            batch.setColor(tableBgColor);
            batch.draw(tex, ax, ay, width, height);
        }

        float cellWidth = getInnerWidth() / cols;
        float cellHeight = getInnerHeight() / rows;
        float startX = ax + paddingLeft;
        float startY = ay + paddingBottom;

        // Draw cells (background and borders)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float cx = startX + (col * cellWidth);
                float cy = startY + (row * cellHeight);
                CellStyle style = cellStyles[row][col];

                if (style.bgColor != null) {
                    batch.setColor(style.bgColor);
                    batch.draw(tex, cx, cy, cellWidth, cellHeight);
                }

                if (style.borderColor != null && style.borderThickness > 0) {
                    batch.setColor(style.borderColor);
                    drawBorder(batch, tex, cx, cy, cellWidth, cellHeight, style.borderThickness);
                }
            }
        }

        // Draw table's border
        if (tableBorderColor != null && tableBorderThickness > 0) {
            batch.setColor(tableBorderColor);
            drawBorder(batch, tex, ax, ay, width, height, tableBorderThickness);
        }
        batch.setColor(Color.WHITE);
    }

    private void drawBorder(SpriteBatch batch, Texture tex, float x, float y, float w, float h, float t) {
        batch.draw(tex, x, y, w, t); // Bottom
        batch.draw(tex, x, y + h - t, w, t); // Top
        batch.draw(tex, x, y + t, t, h - 2 * t); // Left
        batch.draw(tex, x + w - t, y + t, t, h - 2 * t); // Right
    }

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        super.renderDebug(shapeRenderer);   // render table borders

        // render table's cells borders
        float cellWidth = getInnerWidth() / cols;
        float cellHeight = getInnerHeight() / rows;

        // start from left-bottom corner
        float startX = getAbsoluteX() + paddingLeft;
        float startY = getAbsoluteY() + paddingBottom;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float cellX = startX + (col * cellWidth);
                float cellY = startY + (row * cellHeight);

                shapeRenderer.rect(cellX, cellY, cellWidth, cellHeight);
            }
        }
    }
}
