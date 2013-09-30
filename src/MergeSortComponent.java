import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;


public class MergeSortComponent extends JComponent{
	private MergeSorter sorter;
	
	public MergeSortComponent(){
		int[] values = randArray.generate(30, 200);
		sorter = new MergeSorter(values, this);
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		sorter.draw(g2);
		
	}
	
	public void startAnimation(){
		class AnimationRunnable implements Runnable {

			@Override
			public void run() {
				try {
					sorter.sort();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			}
			
		}
		Runnable r = new AnimationRunnable();
		Thread t = new Thread(r);
		t.start();
		
	}
}
