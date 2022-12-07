package ShapePackage.shapes;

public abstract class Shape implements SimpleShape {
    private int x;
    private int y;

    protected Shape(int x, int y) {
        moveTo(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveTo(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public boolean clickedOnShape(int x, int y) {
        return this.getX()-25 <= x && this.getX()+25 >= x && this.getY()-25 <= y && this.getY()+25 >= y;
    }

    @Override
    public boolean add(SimpleShape shape) { return false; }

}
