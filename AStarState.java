import java.util.Map;
import java.util.HashMap;
/**
 * Этот класс хранит базовое состояние, необходимое для алгоритма A* для вычисления пути по карте.
 * Это состояние включает в себя коллекцию "открытых путевых точек" и другую коллекцию
 *  "закрытых путевых точек"."
 * Кроме того, этот класс предоставляет основные операции,
 * необходимые алгоритму поиска пути A* для выполнения его обработки.
 **/
public class AStarState
{
    /** Это ссылка на карту, по которой перемещается алгоритм **/
    private Map2D map;
	
	private Map<Location, Waypoint> open;
	private Map<Location, Waypoint> closed;

    /**
     * Инициализирует новый объект состояния для использования алгоритма поиска пути A*
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
		
		this.open = new HashMap<Location, Waypoint>();
        this.closed = new HashMap<Location, Waypoint>();
    }

    /** Возвращает карту, по которой перемещается навигатор A* **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Метод сканирует все открытые путевые точки
     * и возвращает наиболее оптимальную путевую точку.
     * Если открытых путевых точек нет, этот метод возвращает <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (open.isEmpty()) {
            return null;
        }
        else {
            
            float minCost = 0; 
            Waypoint minWaypoint = null;
            for (Map.Entry<Location, Waypoint> entry : open.entrySet()) {
                if (minWaypoint == null || 
                        entry.getValue().getTotalCost() < minCost) {
                    minWaypoint = entry.getValue();
                    minCost = entry.getValue().getTotalCost();
                }
            }
        return minWaypoint;
    }
    }

    /**
     * Этот метод добавляет путевую точку в список "открытые путевые точки"
     * (или потенциально обновляет уже имеющуюся путевую точку). 
     * Если в местоположении новой путевой точки еще нет открытой путевой точки,
     * то новая путевая точка просто добавляется в список.
     * Новя точка так же может заменить имеющуюся в случае более лучших показателей оптимальности.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        if (open.containsKey(newWP.getLocation())) {
            if (newWP.getPreviousCost() < 
                    open.get(newWP.getLocation()).getPreviousCost()) {
                open.put(newWP.getLocation(), newWP);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            open.put(newWP.getLocation(), newWP);
            return true;
        }
    }


    /** Возвращает текущее количество открытых путевых точек. **/
    public int numOpenWaypoints()
    {
        return open.size();
    }


    /**
     * Этот метод перемещает путевую точку в указанном месте из открытого списка в закрытый список
     **/
    public void closeWaypoint(Location loc)
    {
    
        closed.put(loc, open.get(loc));
        open.remove(loc);
    }

    /**
     * Возвращает значение true, 
     * если список закрытых путевых точек содержит путевую точку для указанного местоположения.
     **/
    public boolean isLocationClosed(Location loc)
    {
       return closed.containsKey(loc);
    }
}

