import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;


public class QuickSortComponent extends JComponent{
	
	private QuickSorter sorter;
	public QuickSortComponent(){
		
		int[] arr = randArray.generate(30, 100);
		sorter = new QuickSorter(arr, this);
		
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		sorter.draw(g2);
	}
	
	public void startAnimation(){
		
		class AnimationRunnable implements Runnable {

			@Override
			public void run() {
				
				try{
					sorter.sort();
				}
				catch(InterruptedException e){
					
				}
				// TODO Auto-generated method stub
				
			}
			
		}
		
		Runnable r = new AnimationRunnable();
		Thread t = new Thread(r);
		t.start();
	}

}
