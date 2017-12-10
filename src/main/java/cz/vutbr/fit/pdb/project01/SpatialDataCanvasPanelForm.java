package cz.vutbr.fit.pdb.project01;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class SpatialDataCanvasPanelForm extends JPanel {

	private Map<Integer, Shape> shapesMap = new HashMap<>();
	
	/**
	 * Create the panel.
	 */
	public SpatialDataCanvasPanelForm() {
		
		this.addMouseListener(new MouseEventListener());
		
		int key = 0;
		Shape shape = new Ellipse2D.Float(100.0f, 100.0f, 100.0f, 100.0f);
		shapesMap.put(key++, shape);
		
		shape = new Ellipse2D.Float(50.0f, 50.0f, 50.0f, 50.0f);
		shapesMap.put(key++, shape);
		
		shape = new Ellipse2D.Float(25.0f, 100.0f, 50.0f, 100.0f);
		shapesMap.put(key++, shape);
		
		repaint();
        revalidate();
	}
	
	@Override
	protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = new Rectangle(0,0,getWidth(), getHeight() );
        g2.setPaint(Color.white);
        g2.fill(bounds);

        for (Map.Entry<Integer, Shape> shapeEntry : shapesMap.entrySet()) {
        	
            g2.setPaint( Color.cyan );
            g2.fill(shapeEntry.getValue());
            g2.setPaint( Color.black );
            g2.draw(shapeEntry.getValue());
        }
    }
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }
	
	

}
