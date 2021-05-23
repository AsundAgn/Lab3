/**
 * Этот класс представляет собой один шаг в пути, сгенерированном алгоритмом поиска пути A*.
 * Путевые точки состоят из местоположения, предыдущей путевой точки в пути и некоторых значений затрат, 
 * используемых для определения наилучшего пути.
 **/
public class Waypoint
{
    /** Расположение путевой точки **/
    Location loc;

    /**
     * Предыдущая путевая точка в этом пути или <code>null</code>, если это корень поиска A*
     **/
    Waypoint prevWaypoint;

    /**
     *В этом поле хранится общие затраты от начального местоположения
     * до этой путевой точки через цепочку путевых точек.
     * Это фактические затраты на следования по пути; они не включает никаких оценок
     **/
    private float prevCost;

    /**
     * В этом поле хранится оценка оставшихся затрат от этой путевой точки
     *  до конечного пункта назначения.
     **/
    private float remainingCost;


    /**
     * Создает новую путевую точку для указанного местоположения
     **/
    public Waypoint(Location loc, Waypoint prevWaypoint)
    {
        this.loc = loc;
        this.prevWaypoint = prevWaypoint;
    }

    /** Возвращает местоположение путевой точки **/
    public Location getLocation()
    {
        return loc;
    }
    
    /**
     * Возвращает предыдущую путевую точку в пути или <code>null</code>, если это начало пути
     **/
    public Waypoint getPrevious()
    {
        return prevWaypoint;
    }
    
    /**
     * Позволяет установить как предыдущую затрату, 
     * так и оставшуюся в одном вызове метода.
     * Обычно эти значения будут установлены в одно и то же время в любом случае.
     **/
    public void setCosts(float prevCost, float remainingCost)
    {
        this.prevCost = prevCost;
        this.remainingCost = remainingCost;
    }

    /**
     * Возвращает фактическую затрату в эту точку из начального местоположения 
     * через ряд путевых точек в этой цепочке
     **/
    public float getPreviousCost()
    {
        return prevCost;
    }

    /**
     * Возвращает оценку оставшейся затраты от этой точки до конечного пункта назначения
     **/
    public float getRemainingCost()
    {
        return remainingCost;
    }

    /**
     *  Возвращает общую смету затрат для этой путевой точки
     **/
    public float getTotalCost()
    {
        return prevCost + remainingCost;
    }
}

