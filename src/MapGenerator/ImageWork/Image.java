package MapGenerator.ImageWork;

public class Image {
    private int y;
    private int x;
    private int z;
    private String path;

    public Image(int x, int y, int z, String path){
        this.x = x;
        this.y = y;
        this.z = z;
        this.path = path;   //путь к файлу
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
