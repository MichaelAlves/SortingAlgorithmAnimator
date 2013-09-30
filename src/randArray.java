import java.util.Random;


public class randArray {
	
	public static Random generator = new Random();
	
	public static int[] generate(int length, int n){
		int a[] = new int[length];
		
		for(int i = 0; i < a.length; i++)
			a[i] = generator.nextInt(n);
		
		return a;
		
	}
}
