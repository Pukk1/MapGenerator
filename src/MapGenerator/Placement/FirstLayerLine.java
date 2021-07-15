package MapGenerator.Placement;

public class FirstLayerLine{
    private int number = 0;
    private int pictureX = 0;
    private int pictureY = 0;
    private int numberOfPictures = 0;

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPictureX(int pictureX) {
        this.pictureX = pictureX;
    }

    public void setPictureY(int pictureY) {
        this.pictureY = pictureY;
    }

    public int getNumber() {
        return number;
    }

    public int getPictureX() {
        return pictureX;
    }

    public int getPictureY() {
        return pictureY;
    }

    public int getNumberOfPictures() {
        return numberOfPictures;
    }

    public void setNumberOfPictures(int numberOfPictures) {
        this.numberOfPictures = numberOfPictures;
    }
}