import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JComponent;


public class QuickSorter extends Sorter{
	private int pivotPos = -1;
	private int markedPos1 = -1;
	private int markedPos2 = -1;
	private int rangeTo = -1;
	private int rangeFrom = -1;
	
	public QuickSorter(int[] anArray, JComponent aComponent){
		values = anArray;
		component = aComponent;
		sortStateLock = new ReentrantLock();
	}
	
	
	public void sort() throws InterruptedException{ //wrapper function for quicksort
		quicksort(0, values.length-1);
	}
	public int partition(int left, int right) throws InterruptedException{
		
		int i = left, j = right;
	    int pivot = values[(left + right) / 2];  //middle element as pivot
	    
	    while (i <= j) {
	    	sortStateLock.lock();
	    	try{
	    		pivotPos = (left+right)/2;
	    		while (values[i] < pivot){
	    			i++;			//increment elements to the left of the pivot until we find one that is larger than it
	    		}
	    		while (values[j] > pivot){
	    			j--;			//decrement elements to the right of the pivot until we find one that is smaller than it
	    		}
	    		if (i <= j) {
	    			swap(i, j);		//swap the two elements found
	    			markedPos1 = i;	
	    			markedPos2 = j;
	    			i++;
	    			j--;
	    		}
	    	}
	    	finally{
	    		sortStateLock.unlock();
	    	}
	    	pause(3);
	     }
	   return i;
	}
	
	public void quicksort(int low, int high) throws InterruptedException{
		if(high <= low)
			return;
		int mid = partition(low, high);
		quicksort(low, mid-1);
		quicksort(mid, high);
	}
	
	public void swap(int i, int j){
		int tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}
	
	
	public void draw(Graphics2D g2){
		sortStateLock.lock();
		try{
			int deltaY = component.getHeight()/values.length;
			for(int i=0; i < values.length; i++){
				if(i<=rangeTo  && i>=rangeFrom){ //highlight partition range
					g2.setColor(Color.BLUE);
					if(i==pivotPos)  			//highlight pivot
						g2.setColor(Color.RED);
					if(i==markedPos1 || i==markedPos2) //highlight element to the left and right of pivot being compared
						g2.setColor(Color.CYAN);
				}
				else{
					g2.setColor(Color.BLACK);
				}
				g2.setStroke(new BasicStroke(4));
				g2.draw(new Line2D.Double(0, i*deltaY, values[i], i*deltaY));
					
			}
		}
		finally{
			sortStateLock.unlock();
		}
		
	}

}
