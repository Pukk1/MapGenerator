package MapGenerator.ImageWork;

public class Image implements Comparable<Image>{
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

//    @Override
//    public int compareTo(Object o) {
//        Image image2 = (Image) o;
//
//        if(image2.getZ() > this.getZ()){
//            System.out.println("ttt");
//            return -3;
//        }
//        if(image2.getX() > this.getX()){
//            System.out.println("ttt");
//            return 2;
//        }
//        if(image2.getY() < this.getY()){
//            System.out.println("ttt");
//
//            return 1;
//        }
//
//        return 0;
//    }

    @Override
    public int compareTo(Image o) {

        if(o.getX() > this.getX()){
            return 1;
        }
        if(o.getX() < this.getX()){
            return -1;
        }

        if(o.getZ() < this.getZ()){
            return 1;
        }
        if(o.getZ() > this.getZ()){
            return -1;
        }

        if(o.getY() < this.getY()){
            return 1;
        }
        if(o.getY() > this.getY()){
            return -1;
        }

        return 0;
    }
}
