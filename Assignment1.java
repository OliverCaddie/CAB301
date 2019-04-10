package cab301;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class Assignment1 {
	
	static Random r = new Random();
	static int count = -1;
	static int equal = -1;
	static int index = -1;
	
	public static void main(String[] args) {
		int runType = 1; // 1 for experiment, 0 for test
		if (runType == 1) experiment(100, 10, 100); // numValues, numReapeats, jumpSize
		else System.out.println(test() ? "success" : "fail");
	}
	
	static boolean test() {
		int maxLength = 20;
		int numRepeats = 10;
		int[] input;
		int result;
		for (int n = 1; n <= maxLength; n++) {
			for (int i = 1; i <= numRepeats; i++) {
				input = generate(n);
				result = bruteForceMedian(input);
				Arrays.sort(input);
				if (result != input[(int) Math.ceil(((double) n)/2) - 1]) {
					return false;
				}
			}
		}
		return true;
	}

	static void experiment(int numValues, int numRepeats, int jumpSize) {
		StringBuilder results = new StringBuilder();
		int n;
		int[] input = null;
		long t1, t2;
		int result;
		
		results.append("n");
		results.append("\t");
		results.append("count");
		results.append("\t");
		results.append("time");
		results.append("\t");
		results.append("index");
		results.append("\t");
		results.append("equal");
		results.append("\t");
		results.append("result");
		results.append("\n");
		
		for (int i = 0; i < numValues; i++) {
			n = (i+1)*jumpSize;
			for (int j = 0; j < numRepeats; j++) {
				count = 0;
				input = generate(n);
				t1 = System.currentTimeMillis();
				result = bruteForceMedian(input);
				t2 = System.currentTimeMillis();
				results.append(Integer.toString(n));
				results.append("\t");
				results.append(Integer.toString(count));
				results.append("\t");
				results.append(Long.toString(t2 - t1));
				results.append("\t");
				results.append(Integer.toString(index));
				results.append("\t");
				results.append(Integer.toString(equal));
				results.append("\t");
				results.append(Integer.toString(result));
				results.append("\n");
			}
			System.out.println(input.length);
		}
		
		Path rPath = Paths.get("src/cab301/results.txt");
		try {
			Files.write(rPath, results.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
				equal = numEqual;
				index = i;
				return a[i];
			}
		}
		return -1;
	}
	
	static int medTest(int[] a) {
		int n = a.length;
		int k = (int) Math.ceil(((double) n)/2);
		int numSmaller = 0;
		int numEqual = 0;
		int i = 0;
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
		
		return -1;
	}
	
	static int[] generate(int n) {
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = r.nextInt(n); 
		}
		return a;
	}
	
}
