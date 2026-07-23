package Mini_Survival_Game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UITable extends UIElement {
    private int cols;
    private int rows;

    private UIElement[][] grid;

    public UITable(float x, float y, float width, float height, int rows, int cols) {
        super(x, y, width, height);
        this.rows = rows;
        this.cols = cols;
        this.grid = new UIElement[rows][cols];
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
                UIElement element = grid[row][col];
                if (element != null) {
                    // Position are relative to left-bottom corner of table + inner table's padding
                    float targetX = paddingLeft + (col * cellWidth);
                    float targetY = paddingBottom + (row * cellHeight);

                    element.setX(targetX);
                    element.setY(targetY);

                    // size of element adapts to the cell's size
                    // TODO: include margins
                    element.setWidth(cellWidth);
                    element.setHeight(cellHeight);
                }
            }
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
