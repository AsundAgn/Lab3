import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * пользовательским компонентом 
 * Swing для представления одной ячейки карты на 2D-карте.
 */
public class JMapCell extends JComponent
{
    private static final Dimension CELL_SIZE = new Dimension(12, 12);
    
    /** Значение True указывает, что ячейка является конечной точкой, либо начальной **/
    boolean endpoint = false;
    
    
    /** True указывает, что ячейка проходима; false - обратное **/
    boolean passable = true;
    
    
    //True указывает, что эта ячейка является частью пути
     
    boolean path = false;
    
    // Строит новую ячейку карты с заданной "проходимостью"."
     
    public JMapCell(boolean pass)
    {
        //Установливает предпочтительный размер ячейки (определение основного окна)
        setPreferredSize(CELL_SIZE);
        
        setPassable(pass);
    }
    
    // Создает новую ячейку карты, которая по умолчанию является проходимой 
    public JMapCell()
    {
        this(true);
    }
    
    /** Помечает эту ячейку как начальную или конечную. **/
    public void setEndpoint(boolean end)
    {
        endpoint = end;
        updateAppearance();
    }
    
    /**
     * Устанавливает эту ячейку как проходимую или не проходимую.
     * Вход true - проходимую; вход false - не проходимую.
     **/
    public void setPassable(boolean pass)
    {
        passable = pass;
        updateAppearance();
    }
    
    public boolean isPassable()
    {
        return passable;
    }
    
    /** Переключает текущее "проходимое" состояние ячейки карты. **/
    public void togglePassable()
    {
        setPassable(!isPassable());
    }
    
    // Помечает эту ячейку как часть пути, обнаруженного алгоритмом A*
    public void setPath(boolean path)
    {
        this.path = path;
        updateAppearance();
    }
    
    /**
     * Обновление цвета фона в соответствии проходимости
     **/
    private void updateAppearance()
    {
        if (passable)
        {
            // Сносная клетка. Указывает ее состояние с границей.
            setBackground(Color.WHITE);

            if (endpoint)
                setBackground(Color.CYAN);
            else if (path)
                setBackground(Color.GREEN);
        }
        else
        {
            // ССмена фона непроходимой клетки в красный
            setBackground(Color.RED);
        }
    }

    // Реализация метода paint
    protected void paintComponent(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
