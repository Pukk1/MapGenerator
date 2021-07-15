package MapGenerator.MapAnalysis;

public class MapData {
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private int pixSizeX = 0;
    private int pixSizeY = 0;

    private int SizeUnitW = 0;  //размер на карте абстрактный и задаётся единицей размера, которой соответствует какое-то количество пикселей
    private int SizeUnitH = 0;  //по задумке SizeUnitW и SizeUnitH должный быть равны

    //для красивого отображения размеры карты должны быть в отношении H/W = 4/8

    private boolean needFinishResize = false;

    public void setZ(int z) {
        this.z = z;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setSizeUnitH(int sizeUnitH) {
        SizeUnitH = sizeUnitH;
    }

    public void setSizeUnitW(int sizeUnitW) {
        SizeUnitW = sizeUnitW;
    }

    public int getSizeUnitH() {
        return SizeUnitH;
    }

    public int getSizeUnitW() {
        return SizeUnitW;
    }

    public void setNeedFinishResize(boolean needFinishResize) {
        this.needFinishResize = needFinishResize;
    }

    public boolean isNeedFinishResize() {
        return needFinishResize;
    }

    public void setPixSizeX(int pixSizeX) {
        this.pixSizeX = pixSizeX;
    }

    public void setPixSizeY(int pixSizeY) {
        this.pixSizeY = pixSizeY;
    }

    public int getPixSizeX() {
        return pixSizeX;
    }

    public int getPixSizeY() {
        return pixSizeY;
    }
}
