package MapGenerator.MapAnalysis;

import MapGenerator.Configurations;

public class MapData {
    private int xSize = 0;
    private int ySize = 0;
    private int zSize = 0;
    private int pixSizeX = 0;
    private int pixSizeY = 0;

    private int sizeUnitW = 0;  //размер на карте абстрактный и задаётся единицей размера, которой соответствует какое-то количество пикселей
    private int sizeUnitH = 0;  //по задумке SizeUnitW и SizeUnitH должный быть равны

    //для красивого отображения размеры карты должны быть в отношении H/W = 4/8

    private int pixSizeCellW = 0;  //размер одной клетки на карте в pix
    private int pixSizeCellH = 0;

    private boolean needFinishResize = false;

    public void setZSize(int zSize) {
        this.zSize = zSize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public int getzSize() {
        return zSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getXSize() {
        return xSize;
    }

    public void setSizeUnitH(int sizeUnitH) {
        this.sizeUnitH = sizeUnitH;
    }

    public void setSizeUnitW(int sizeUnitW) {
        this.sizeUnitW = sizeUnitW;
    }

    public int getSizeUnitH() {
        return sizeUnitH;
    }

    public int getSizeUnitW() {
        return sizeUnitW;
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

    public void createPixSizeCell(){
        pixSizeCellW = sizeUnitW* Configurations.mapFormatX;
        pixSizeCellH = sizeUnitH* Configurations.mapFormatY;
    }

    public int getPixSizeCellH() {
        return pixSizeCellH;
    }

    public int getPixSizeCellW() {
        return pixSizeCellW;
    }
}
