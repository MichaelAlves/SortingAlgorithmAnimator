import java.awt.Graphics2D;
import java.util.concurrent.locks.Lock;
import javax.swing.JComponent;
/*Abstract class for code re-use */
public abstract class Sorter {
	protected Lock sortStateLock; 		//lock for synchronizing shared state of draw and sorting function
	protected JComponent component;
	protected int[] values;
	private static final int THREAD_DELAY = 100;	//100 millisecond delay
	
	public abstract void draw(Graphics2D g2);		  //draws current state of the algorithm
	public abstract void sort() throws InterruptedException;  //wrapper function for each sorting algorithm
	public void pause(int steps) throws InterruptedException{ //pause at each important step in algorithm to view grpahical output
		component.repaint();
		Thread.sleep(steps*THREAD_DELAY);
	}

}
