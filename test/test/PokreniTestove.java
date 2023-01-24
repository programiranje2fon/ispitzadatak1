package test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import ispravka_koda.UpisivanjeUTXTFajl3;
import ispravka_koda.UpisivanjeUTXTFajl3Test;
import porodiliste.PorodilisteTest;
import porodiliste.bebe.BebaTest;
import porodiliste.statistike.StatistikeTest;

/**
 * This class executes all tests and prints out the report.
 * 
 */
public class PokreniTestove {

	public static void main(String[] args) {
		runTestsForClass(BebaTest.class);
		runTestsForClass(StatistikeTest.class);
		runTestsForClass(PorodilisteTest.class);
		runTestsForClass(UpisivanjeUTXTFajl3Test.class);
	}

	/**
	 * Runs all tests declared in the class c.
	 * 
	 * @param c class whose tests to run
	 */
	@SuppressWarnings("rawtypes")
	private static void runTestsForClass(Class c) {
		Result r = JUnitCore.runClasses(c);

		int totalCount = r.getRunCount();
		int failureCount = r.getFailureCount();
		int successfulCount = totalCount - failureCount;
		String className = c.getName().substring(0, c.getName().length() - 4); // remove last 4 characters "Test"

		if (r.wasSuccessful()) {
			System.out.println("------------------------------------------------");
			System.out.format("TEST KLASA: %s %n%n", className);
			System.out.format("Pokrenuto: %-5d Uspešno: %-5d Neuspešno: %-5d %n%n", totalCount, successfulCount,
					failureCount);
			System.out.println("\tSvi testovi su uspešno prošli!");
			System.out.println();
			System.out.flush();
		} else {
			System.err.println("------------------------------------------------");
			System.err.format("TEST KLASA: %s %n%n", className);
			System.err.format("Pokrenuto: %-5d Uspešno: %-5d Neuspešno: %-5d %n%n", totalCount, successfulCount,
					failureCount);

			// print the test summary only if there are no missing classes,
			// attributes or methods.
			if (thereAreNoLinkageErrors(r.getFailures())) {

				// create a map where TestTypes are keys. Values are maps with
				// the method name (from the tested class) is the key, and the
				// value is a two-dimensional array with the method stats (the
				// number of successful and the number of failed tests for the
				// method).
				Map<TestTypes, Map<String, int[]>> testTypeMethodMap = new LinkedHashMap<>();

				for (TestTypes testType : TestTypes.values()) {
					Map<String, int[]> methodStatsMap = new LinkedHashMap<>();

					for (Method method : c.getDeclaredMethods()) {
						String[] methodNameElements = method.getName().split("_");

						if (methodNameElements[0].equals(testType.namePrefix)) {
							String methodName = methodNameElements[1];

							int[] methodStats = null;

							// fetch the array if exists or create it
							if (methodStatsMap.containsKey(methodName)) {
								methodStats = methodStatsMap.get(methodName);
							} else {
								methodStats = new int[2];
								methodStatsMap.put(methodName, methodStats);
							}

							// check if test has failed
							if (isTestFailed(r.getFailures(), method.getName())) {
								methodStats[1]++; // increment the number of failed tests
							} else {
								methodStats[0]++; // otherwise, increment the number of successful tests
							}
							methodStatsMap.put(methodName, methodStats);
						}
					}

					// if for a given TestTypes instance there are no tests, do not add it
					if (!methodStatsMap.isEmpty())
						testTypeMethodMap.put(testType, methodStatsMap);
				}

				// print the method stats
				if (!testTypeMethodMap.isEmpty()) {
					for (TestTypes testType : testTypeMethodMap.keySet()) {
						System.err.println("Testovi za " + testType.pluralDative + ":");

						Map<String, int[]> methodsMap = testTypeMethodMap.get(testType);

						for (String methodName : methodsMap.keySet()) {
							int[] methodStats = methodsMap.get(methodName);

							System.err.printf("\t %-20s %s%n", methodName,
									methodStats[1] == 0 ? "OK" : "Neuspešno: " + methodStats[1]);
						}
						System.err.println();
					}
				}
			}

			System.err.println("Spisak NEUSPEŠNIH testova:\n");
			int i = 1;

			for (Failure failure : r.getFailures()) {
				Throwable ex = failure.getException();

				if (ex instanceof NoClassDefFoundError) {
					System.err.format(
							"%s. U projektu ne postoji klasa %s %n \t (proverite naziv klase i naziv paketa) %n%n", i++,
							className);
				} else if (ex instanceof NoSuchFieldError) {
					System.err.format("%s. U projektu ne postoji atribut %s %n\t (proverite tip i naziv atributa) %n%n",
							i++, ex.getMessage());
				} else if (ex instanceof NoSuchMethodError) {
					String methodName = failure.getDescription().getMethodName().split("_")[1];

					System.err.format(
							"%s. U klasi %s ne postoji metoda %s %n\t (proverite naziv metode, povratni tip, tipove i redosled parametara) %n%n",
							i++, className, methodName);
				} else {
					String[] methodNameElements = failure.getDescription().getMethodName().split("_");
					System.out.println(failure.getDescription().getMethodName());
					String methodName = methodNameElements[1];
					String testVariantName = null;

					if (methodNameElements.length > 2) {
						testVariantName = methodNameElements[2];
					}

					TestTypes testType = Arrays.stream(TestTypes.values())
							.filter(tt -> tt.namePrefix.equals(methodNameElements[0])).findAny().get();

					System.err.format("%s. Test %sza %s %s: %n \t%s %n%n", i++,
							testVariantName != null ? testVariantName + " " : "", testType.vocative, methodName,
							failure.getMessage());
				}
			}

			System.err.println();
			System.err.flush();
		}
	}

	/**
	 * Returns true if there are failures caused by missing classes, fields or
	 * methods. Otherwise, returns false.
	 * 
	 * @param failures list of @{org.junit.runner.notification.Failure} instances.
	 * @return true or false
	 */
	private static boolean thereAreNoLinkageErrors(List<Failure> failures) {
		for (Failure failure : failures) {
			Throwable ex = failure.getException();
			if (ex instanceof NoClassDefFoundError || ex instanceof NoSuchFieldError || ex instanceof NoSuchMethodError)
				return false;
		}
		return true;
	}

	/**
	 * Returns true if there is a failed test for the given method name.
	 * 
	 * @param failureList list of @{org.junit.runner.notification.Failure}
	 *                    instances.
	 * @param methodName  name of the method to search the test for
	 * @return true or false
	 */
	private static boolean isTestFailed(List<Failure> failureList, String methodName) {
		return failureList.stream().anyMatch(f -> f.getDescription().getMethodName().equals(methodName));
	}

	/**
	 * Enum for different types of tests. There is an instance for each element that
	 * can be tested.
	 * 
	 */
	private enum TestTypes {
		CLASS("klasa", "klasu", "klasu"), ATTRIBUTE("atribut", "atribute", "atribut"),
		CONSTANT("konstanta", "konstante", "konstantu"), ENUM("enum", "nabrojive tipove", "nabrojiv tip"),
		CONSTRUCTOR("konstruktor", "konstruktore", "konstruktor"), METHOD("metoda", "metode", "metodu");

		private String namePrefix;
		private String pluralDative;
		private String vocative;

		private TestTypes(String name, String headingText, String vocative) {
			this.namePrefix = name;
			this.pluralDative = headingText;
			this.vocative = vocative;
		}
	}
}
