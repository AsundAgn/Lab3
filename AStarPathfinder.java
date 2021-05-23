import java.util.HashMap;
import java.util.HashSet;


/**
 * Этот класс содержит реализацию алгоритма поиска пути A*.
 * Алгоритм реализован как статический метод,
 * поскольку алгоритму поиска путей действительно не нужно поддерживать
 * какое-либо состояние между вызовами алгоритма.
 */
public class AStarPathfinder
{
    /**
     * Эта константа содержит максимальный предел отсечения для стоимости путей.
     * Если какая-либо конкретная путевая точка превышает этот предел затрат,
     *  путевая точка отбрасывается.
     **/
    public static final float COST_LIMIT = 1e6f;

    
    /**
     * Пытается вычислить путь, который перемещается между начальным и конечным
     * местоположениями указанной карты. Если путь найден, возвращается <em>final</em> - точка 
     * последнего шага в пути; эту путевую точку можно
     * использовать для возврата к начальной точке.
     * Если путь не найден, возвращается <code>null</code>.
     **/
    public static Waypoint computePath(Map2D map)
    {
        // Переменные, необходимые для поиска A*
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Установка начальной точки для поиска A*
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, estimateTravelCost(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;
        
        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // ПОиск аилучшей точки маршрута
            Waypoint best = state.getMinOpenWaypoint();
            
            // Если лучшая точка - это место финиша, то мы закончили
            if (best.getLocation().equals(finishLoc))
            {
                finalWaypoint = best;
                foundPath = true;
            }
            
            // Обновление/добавление всех соседей текущего лучшего местоположения.
            // Анологично попытке вполнить все "следущие шаги" из этого места
            takeNextStep(best, state);
            
            // Перемещение этого местоположения из списка "открыто" в список "закрыто"
            state.closeWaypoint(best.getLocation());
        }
        
        return finalWaypoint;
    }

    /**
     * Этот статический вспомогательный метод берет путевую точку и генерирует
     * все допустимые "следующие шаги" от данной.  
     * Новые путевые точки добавляются в коллекцию "открытые"
     * путевые точки переданного А* состояния объекта*
     **/
    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();
        
        for (int y = loc.yCoord - 1; y <= loc.yCoord + 1; y++)
        {
            for (int x = loc.xCoord - 1; x <= loc.xCoord + 1; x++)
            {
                Location nextLoc = new Location(x, y);
                
                // Если "следующее местоположение" находится за пределами карты, пропускаем его.
                if (!map.contains(nextLoc))
                    continue;
                
                // Если "следующее местоположение" - это это же местоположение, пропускаем его.
                if (nextLoc == loc)
                    continue;
                
                // Если это местоположение уже находится в "закрытом" наборе,
                // переходим к следующему.
                if (state.isLocationClosed(nextLoc))
                    continue;

                // Делаем точку для этогого "следующего положения"
                
                Waypoint nextWP = new Waypoint(nextLoc, currWP);
                
                //Информация о оптимальном пути для включения барьеров и т.д.

                float prevCost = currWP.getPreviousCost() +
                    estimateTravelCost(currWP.getLocation(),
                                       nextWP.getLocation());

                prevCost += map.getCellValue(nextLoc);
                
                // Пропустите это "следующее местоположение", если оно не оптимально.

                if (prevCost >= COST_LIMIT)
                    continue;
                
                nextWP.setCosts(prevCost,
                    estimateTravelCost(nextLoc, map.getFinish()));

                //  Добавляет путевую точку в набор открытых путевых точек.
                // Если для этого местоположения уже она уже существует,
                // новая путевая точка заменяет старую, но только в том случае,
                // если она более оптмальна.
                state.addOpenWaypoint(nextWP);
            }
        }
    }
    
    /**
     * Оценивает оптимальность пути между двумя указанными точками.
     * Фактическая вычисленная оптимальность - это просто расстояние по прямой между двумя местоположениями.
     **/
    private static float estimateTravelCost(Location currLoc, Location destLoc)
    {
        int dx = destLoc.xCoord - currLoc.xCoord;
        int dy = destLoc.yCoord - currLoc.yCoord;
        
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
