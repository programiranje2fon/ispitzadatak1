package ispravka_koda;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class UpisivanjeUTXTFajl3Test {

	@Test
	public void metoda_upisiArmstrongoveBrojeve() throws IOException{
		String ocekivaniIspis = 
				"153\n" +
				"370\n" +
				"371\n" +
				"407\n";
		
		UpisivanjeUTXTFajl3.upisiArmstrongoveBrojeve();

		String sadrzajFajla = ucitajIVratiTekst("brojevi3.txt");
		
		assertEquals(ocekivaniIspis, sadrzajFajla);
	
		new File("brojevi3.txt").delete();
	}
	
	private static String ucitajIVratiTekst(String imeFajla) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(imeFajla));
		boolean kraj = false;
		String s = "";
		
		while (!kraj) {
			String pom = in.readLine();
			
			if (pom == null)
				kraj = true;
			else
				s = s + pom + "\n";
		}
		in.close();

		return s;
	}
}
