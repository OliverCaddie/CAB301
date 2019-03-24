package cab301;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Assignment1 {
	
	static Random r = new Random();
	static int count = 0;

	public static void main(String[] args) throws IOException {
		int numResults = 10;
		int numRepeats = 10;
		int jumpSize = 10;
		StringBuilder results = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder dataUnordered = new StringBuilder();
		int n;
		int[] input;
		long t1, t2;
		
		for (int i = 0; i < numResults; i++) {
			for (int j = 0; j < numRepeats; j++) {
				n = (i+1)*jumpSize;
				count = 0;
				input = generate(n);
				t1 = System.currentTimeMillis();
				bruteForceMedian(input);
				t2 = System.currentTimeMillis();
				
				data.append(Integer.toString(n));
				for (int k = 0; k < n; k++) {
					data.append("\t");
					data.append(Integer.toString(input[k]));
					dataUnordered.append(Integer.toString(input[k]));
					dataUnordered.append("\n");
				}
				data.append("\n");
				
				results.append(Integer.toString(n));
				results.append("\t");
				results.append(Integer.toString(count));
				results.append("\t");
				results.append(Long.toString(t2 - t1));
				results.append("\n");
			}
		}
		
		
		Path dPath = Paths.get("src/cab301/data.xls");
		Path d2Path = Paths.get("src/cab301/data2.xls");
		Path rPath = Paths.get("src/cab301/results.xls");
		
		Files.write(dPath, data.toString().getBytes());
		Files.write(d2Path, dataUnordered.toString().getBytes());
		Files.write(rPath, results.toString().getBytes());
	}

	static int bruteForceMedian(int[] a) {
		int n = a.length;
		int k = (int) Math.ceil(((double) n)/2);
		for (int i = 0; i < n; i++) {
			int numSmaller = 0;
			int numEqual = 0;
			for (int j = 0; j < n; j++) {
				if (++count > 0 && a[j] < a[i]) {
					numSmaller++;
				} else if (++count > 0 && a[j] == a[i]) {
					numEqual++;
				}
			}
			if (numSmaller < k && k <= numSmaller + numEqual) {
				return a[i];
			}
		}
		return -1;
	}
	
	static int[] generate(int n) {
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = r.nextInt();
		}
		return a;
	}
	
}
