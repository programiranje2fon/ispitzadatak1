package ispravka_koda;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UpisivanjeUTXTFajl3 {
	public static void upisiArmstrongoveBrojeve() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("brojevi3.txt"));
			for (int i = 100; i <= 999; i++) {
				int zbirKubova = 0, pom = i;
				while (pom > 0) {
					zbirKubova = zbirKubova + (pom % 10) * (pom % 10) * (pom % 10);
					pom = pom / 10;
				}
				if (zbirKubova == i)
					out.println(i);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}