import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JComponent;


public class InsertionSorter extends Sorter {
	private int markedPos = -1;
	private int alreadySorted = -1;
	
	public InsertionSorter(int[] arr, JComponent aComponent){
		arr = values;
		aComponent = component;
		sortStateLock = new ReentrantLock();
	}
	
	@Override
	//wrapper function
	public void sort() throws InterruptedException {
		insertionSort(0, values.length-1);

	}
	
	//insertion sort algorithm
	public void insertionSort(int start, int end) throws InterruptedException {
	      int i, j, newValue;
	      for (i = start+1; i < end; i++) {
	    	  	sortStateLock.lock();
	    	  	try{
	    	  		newValue = values[i];
	    	  		j = i-1;
	    	  		while (j >= 0 && values[j] > newValue) {
	    	  			values[j+1] = values[j];
	    	  			j--;
	    	  		}
	    	  		alreadySorted = j;
	    	  		values[j+1] = newValue;
	    	  		markedPos = j+1;
	    	  	}
	    	  	finally{
	    	  		sortStateLock.unlock();
	    	  	}
	    	  	pause(3);
	      }
	}
	@Override
	//draws the current state of the algorithm
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		sortStateLock.lock();
		try{
			int deltaY = component.getHeight()/values.length;
			for(int i=0; i < values.length; i++){
				if(i==markedPos)
					g2.setColor(Color.RED);
				else if(i<=alreadySorted)
					g2.setColor(Color.BLUE);
				else
					g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(4));
				g2.draw(new Line2D.Double(0, i*deltaY , values[i], i*deltaY));
			}
		}
		finally{
			sortStateLock.unlock();
		}

	}

	

}
