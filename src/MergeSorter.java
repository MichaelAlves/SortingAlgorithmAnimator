import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JComponent;


public class MergeSorter extends Sorter{
	
	private int markedPos = -1;

	
	public MergeSorter(int[] anArray, JComponent aComponent){
		values = anArray;
		component = aComponent;
		sortStateLock = new ReentrantLock();
		
	}
	public void sort() throws InterruptedException{
		MergeSort(0, values.length-1);
		
	}
	
	public void MergeSort(int from, int to) throws InterruptedException{
		if(from==to) return; 
		
		int mid = (from+to)/2;
		MergeSort(from, mid);
		MergeSort(mid+1, to);
		Merge(from, mid, to);
		
	}
	
	public void Merge(int low, int mid, int high) throws InterruptedException{
		int[] sortedArray = new int[values.length];
		//copy both parts into a temporary array
		for(int i=low; i <= high; i++){
			sortedArray[i] = values[i];
		}
		int i = low;
		int j = mid + 1;
		int k = low;
				
		// copy the smallest values from either the left or the right side back
	    // to the original array
		while( i<=mid && j <= high){
			sortStateLock.lock();
			try{
			   if(sortedArray[i] <= sortedArray[j]){
			       values[k] = sortedArray[i];
				   markedPos = i;
				   i++;
				}
				else {
				   values[k] = sortedArray[j];
				   markedPos = j;
				   j++;
				}		
			}
			finally{
				sortStateLock.unlock();
			}
			pause(5);
			k++;
		}
				
		// copy the rest of the left side of the array into the target array
		while(i <= mid){
		   values[k] = sortedArray[i];
		   k++; i++;
				
		}
	}
	@Override
	public void draw(Graphics2D g2) {
		sortStateLock.lock();
		try{
			int deltaY = component.getHeight()/values.length;
		
			for(int i=0; i < values.length; i++){
				if(i==markedPos)
					g2.setColor(Color.RED);
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
