/**
 * Класс представляет определенное местоположение на 2D-карте.
 * Координаты-это целочисленные значения.
 **/
public class Location
{
    /**  X координата этого местоположения. **/
    public int xCoord;

    /**  Y координата этого местоположения. **/
    public int yCoord;


    /** Создает новое местоположение с заданными целочисленными координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создает новое местоположение с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

     
    public boolean equals(Object obj) {
    
        if (obj instanceof Location) {
        
            Location other = (Location) obj;
            if (xCoord == other.xCoord &&
                yCoord == other.yCoord) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    
    public int hashCode() {
        int result = 17; // Prime value

        result = 37 * result + xCoord * 31;
        result = 37 * result + yCoord;
        return result;
    }
}
