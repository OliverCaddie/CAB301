package cab301.project1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Assignment1 {
	
	static Random r = new Random();
	static int count;
	static int equal;
	static int index;
	
	public static void main(String[] args) {
		int runType = 1; // 1 for experiment, 0 for test
		if (runType == 1) experiment(10, 100, 100); // numValues, numReapeats, jumpSize
		else System.out.println(test() ? "success" : "fail");
	}
	
	static boolean test() {
		List<int[]> testCases = new ArrayList<int[]>();
		testCases.add(new int[] {5}); // 1 element
		testCases.add(new int[] {4, 4}); // 2 element same
		testCases.add(new int[] {1, 2}); // 2 element different
		testCases.add(new int[] {1, 2, 3, 4, 5}); // ordered ascending
		testCases.add(new int[] {5, 4, 3, 2, 1}); // ordered descending
		testCases.add(new int[] {3, 5, 2, 1, 4}); // unordered
		testCases.add(new int[] {3, 5, 6, 2, 4, 1}); // even
		int result;
		for (int[] testCase : testCases) {
			result = bruteForceMedian(testCase);
			System.out.println(result);
			Arrays.sort(testCase);
			if (result != testCase[(int) Math.ceil(((double) testCase.length)/2) - 1]) {
				return false;
			}
		}
		return true;
	}

	static void experiment(int numValues, int numRepeats, int jumpSize) {
		StringBuilder results = new StringBuilder();
		int n, m;
		int[] input;
		long t1, t2;
		int result;
		
		results.append("n");
		results.append("\t");
		results.append("m");
		results.append("\t");
		results.append("f");
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
		
		for (int f = -2; f <= 2; f++) {
			for (int i = 1; i <= numValues; i++) {
				n = i*jumpSize;
				m = (int) (n * Math.pow(10, f));
				for (int j = 0; j < numRepeats; j++) {
					count = 0;
					input = generate(n, m);
					t1 = System.currentTimeMillis();
					result = bruteForceMedian(input);
					t2 = System.currentTimeMillis();
					
					results.append(Integer.toString(n));
					results.append("\t");
					results.append(Integer.toString(m));
					results.append("\t");
					results.append(Integer.toString(f));
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
				System.out.println(n + f);
			}
		}
		Path rPath = Paths.get("src/cab301/project1/results.txt");
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

	
	static int[] generate(int n, int m) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = r.nextInt(m); 
		}
		return a;
	}
	
}
