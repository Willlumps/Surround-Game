package surroundpack;

public class TestObject {

    private int row;
    private int col;
    private Object object;

    public TestObject(int row, int col, Object object) {
        this.row = row;
        this.col = col;
        this.object = object;
    }

    public Object getObject() {
        return this.object;
    }

    public int getUpValue() {
        if (row == 0) {
            throw new IndexOutOfBoundsException();
        }
        return row - 1;
    }
}
