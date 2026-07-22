package Mini_Survival_Game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UITable extends UIElement {
    private int cols;
    private int rows;
    private UIElement[][] tableGrid;

    public UITable(float x, float y, float width, float height, int rows, int cols) {
        super(x, y, width, height);
        this.rows = rows;
        this.cols = cols;
        this.tableGrid = new UIElement[rows][cols];
    }

    public void setCell(int row, int col, UIElement element) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return;

        // replace already existing element
        if (tableGrid[row][col] != null) {
            elementList.remove(tableGrid[row][col]);
        }
        tableGrid[row][col] = element;

        if (element != null) {
            addElement(element);
            recalculateCellPosition(row, col);
        }
    }

    /**
     * Oblicza pozycję X i Y elementu wewnątrz komórki, biorąc pod uwagę
     * rozmiar komórki, paddingi tabeli oraz marginesy samego elementu.
     */
    private void recalculateCellPosition(int row, int col) {
        UIElement element = tableGrid[row][col];
        if (element == null) return;

        float cellWidth = getInnerWidth() / cols;
        float cellHeight = getInnerHeight() / rows;

        element.x = col * cellWidth;

        int invertedRow = (rows - 1) - row;
        element.y = invertedRow * cellHeight;
    }



    @Override
    protected void renderElement(SpriteBatch batch) {

    }
}
