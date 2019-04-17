package cab301.project2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Assignment2 {

	
	static int count1;
	static int count2;
	static Random r = new Random();
	
	public static void main(String[] args) {
		experiment(30, 10, 100);

	}

	
	static void experiment(int numValues, int numRepeats, int jumpSize) {
		int[] a;
		int n;
		long t1, t2, t3;
		StringBuilder results = new StringBuilder();
		
		results.append("n");
		results.append("\t");
		results.append("count.1");
		results.append("\t");
		results.append("count.2");
		results.append("\t");
		results.append("time.1");
		results.append("\t");
		results.append("time.2");
		results.append("\r\n");
		
		for (int i = 1; i <= numValues; i++) {
			n = jumpSize * i;
			a = generate(n);
			
			t1 = System.currentTimeMillis();
			for (int j = 1; j < numRepeats || j == numRepeats && (count1 = 0) == 0; j++) minDistance(a);
			t2 = System.currentTimeMillis();
			for (int j = 1; j < numRepeats || j == numRepeats && (count2 = 0) == 0; j++) minDistance2(a);	
			t3 = System.currentTimeMillis();
			
			results.append(Integer.toString(n));
			results.append("\t");
			results.append(Integer.toString(count1));
			results.append("\t");
			results.append(Integer.toString(count2));
			results.append("\t");
			results.append(Double.toString(((double)t2 - t1)/numRepeats));
			results.append("\t");
			results.append(Double.toString(((double)t3 - t2)/numRepeats));
			results.append("\r\n");
			
			System.out.println(n);
		}
		Path rPath = Paths.get("src/cab301/results.txt");
		try {
			Files.write(rPath, results.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	static int minDistance(int[] a) {
		int d = Integer.MAX_VALUE;
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j && ++count1 > 0 && Math.abs(a[i] - a[j]) < d) {
					d = Math.abs(a[i] - a[j]);
				}
			}
		}
		return d;
	}
	
	static int minDistance2(int[] a) {
		int d = Integer.MAX_VALUE;
		int n = a.length;
		int t;
		for (int i = 0; i < n-1; i++) {
			for (int j = i + 1; j < n; j++) {
				t = Math.abs(a[i] - a[j]);
				count2++;
				if (t < d) {
					d = t;
				}
			}
		}
		return d;
	}
	
	static int[] generate(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = r.nextInt(Integer.MAX_VALUE); 
		}
		return a;
	}
	
	
	
}
