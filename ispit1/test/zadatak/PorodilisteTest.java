package zadatak;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import porodiliste.Porodiliste;
import porodiliste.bebe.Beba;
import test.TestUtil;

public class PorodilisteTest {

	Porodiliste instance;

	@Before
	public void setUp() throws Exception {
		instance = new Porodiliste(3);
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
	}

	@Test
	public void atribut_bebe() {
		assertTrue("U klasi porodiliste nije definisan atribut bebe",
				TestUtil.doesFieldExist(Porodiliste.class, "bebe"));
	}

	@Test
	public void atribut_bebe_vidljivost() {
		assertTrue("Atribut bebe nije privatan",
				TestUtil.hasFieldModifier(Porodiliste.class, "bebe", Modifier.PRIVATE));
	}

	@Test
	public void konstruktor_porodiliste_20() {
		int velicina = 20;
		try {
			Porodiliste p1 = new Porodiliste(velicina);
			Beba[] bebeValue = (Beba[]) TestUtil.getFieldValue(p1, "bebe");
			assertTrue("Za prosledjeni prvi parametar \""+velicina+"\" atribut bebe je niz duzine " + bebeValue.length,
					bebeValue.length == velicina);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = Exception.class)
	public void konstruktor_porodiliste_manje_od_0() throws Exception {
		int velicina = -1;
		try {
			instance = new Porodiliste(velicina);
		} catch (Exception e) {
			if (e.getClass() == Exception.class) {
				throw e;
			}
			e.printStackTrace();
		}
		assertTrue("Za unetu\""+velicina+"\"vrednost konstruktor ne baca proveravani izuzetak", false);
	}

	@Test
	public void metoda_upisiEkstreme_vidljivost() {
		assertTrue("Metoda upisiEkstreme nije javna", TestUtil.hasMethodModifier(Porodiliste.class, "upisiEkstreme",
				new Class<?>[] { String.class }, Modifier.PUBLIC));
	}

	private void dodajBebe() {
		Beba[] bebe = (Beba[]) TestUtil.getFieldValue(instance, "bebe");
		Beba pom = new Beba();
		pom.setIme("Marko Jankovic");
		pom.setTezina(4320);
		pom.setDuzina(64);
		pom.setVremeRodjenja(new GregorianCalendar(1995, 1, 23, 23, 59));
		bebe[0] = pom;
		Beba pom1 = new Beba();
		pom1.setIme("Milica Jovanovic");
		pom1.setTezina(2630);
		pom1.setDuzina(72);
		pom1.setVremeRodjenja(new GregorianCalendar(1992, 0, 12, 11, 2));
		bebe[1] = pom1;
		Beba pom2 = new Beba();
		pom2.setIme("Marina Brankovic");
		pom2.setTezina(5230);
		pom2.setDuzina(68);
		pom2.setVremeRodjenja(new GregorianCalendar(1998, 3, 4, 22, 36));
		bebe[2] = pom2;
	}

	// Sta treba uraditi ako je niz prazan
	@Test(timeout = 2000)
	public void metoda_upisiEkstreme_fajl() {

		dodajBebe();
		Beba[] bebeValue = (Beba[]) TestUtil.getFieldValue(instance, "bebe");
		Beba najteza = null;

		// Implementacija resenja sa sajta
		// radi jedino ako je niz sa beba u potpunosti popunjen

		for (Beba beba : bebeValue) {
			if (beba != null) {
				if (najteza == null || najteza.getTezina() < beba.getTezina()) {
					najteza = beba;
				}
			}
		}

		String naziv = "porodiliste";
		instance.upisiEkstreme(naziv);

		try (DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(naziv)));) {

			String ime = input.readUTF();
			char tab1 = input.readChar();
			int tezina = input.readInt();
			char tab2 = input.readChar();
			int duzina = input.readInt();

			assertEquals("Ime najteze bebe je \""+najteza.getIme()+"\", a u fajl je upisano \""+ime+"\"", najteza.getIme(), ime);
			assertTrue("Pogresan format, nakon imena bebe nije znak <tab>", tab1 == '\t');
			assertTrue("Tezine najteze bebe je "+najteza.getTezina()+", a u fajl je upisano "+tezina, najteza.getTezina() == tezina);
			assertTrue("Pogresan format, nakon tezine bebe nije znak <tab>", tab2 == '\t');
			assertTrue("Duzine najteze bebe je "+najteza.getDuzina()+", a upisano je "+duzina, najteza.getDuzina() == duzina);

		} catch (FileNotFoundException e) {
			assertTrue("Fajl " + naziv + " nije sacuvan", false);
		} catch (IOException e) {
			assertTrue("Progresan format", false);
		}
	}

	@Test
	public void metoda_otpustiIzPorodilista_vidljivost() {
		assertTrue("Nije definisana javna metoda otpustiIzPorodilista", TestUtil.hasMethodModifier(Porodiliste.class,
				"otpustiIzPorodilista", new Class<?>[] { GregorianCalendar.class }, Modifier.PUBLIC));
	}

	@Test(timeout = 2000)
	public void metoda_otpustiIzPorodilista() {

		dodajBebe();
		Beba[] bebe = (Beba[]) TestUtil.getFieldValue(instance, "bebe");
		GregorianCalendar vreme = new GregorianCalendar(1996, 2, 3);
		LinkedList<Beba> otpusteneValue = instance.otpustiIzPorodilista(vreme);
		LinkedList<Beba> otpustene = new LinkedList<>();
		
		// Implementacija resenja sa sajta
		// radi jedino ako je niz sa beba u potpunosti popunjen
		
		for (Beba beba : bebe) {
			if (beba != null && beba.getVremeRodjenja().before(vreme)) {
				otpustene.add(beba);
				assertTrue("Za uneti argument: "+vreme.getTime()+", vracena lista otpustenih beba ne sadrzi bebu: "+beba, otpusteneValue.contains(beba));
			}
		}
		
		for(Beba beba : otpusteneValue) {
			assertTrue("Za uneti argument: "+vreme.getTime()+", vracena lista otpustenih beba sadrzi bebu: "+beba, otpustene.contains(beba));	
		}
		
	}

	@Test
	public void metoda_vratiFrekvencijskuTabelu_vidljivost() {
		assertTrue("Nije definisana javna metoda vratiFrekvencijskuTabelu",
				TestUtil.hasMethodModifier(Porodiliste.class, "vratiFrekvencijskuTabelu", null, Modifier.PUBLIC));
	}

	@Test(timeout = 2000)
	public void metoda_vratiFrekvencijskuTabelu() {
		dodajBebe();
		Beba[] bebe = (Beba[]) TestUtil.getFieldValue(instance, "bebe");
		int[] niz = new int[24];

		for (int i = 0; i < niz.length; i++)
			niz[i] = 0;

		for (int i = 0; i < bebe.length; i++) {
			int satRodjenja = bebe[i].getVremeRodjenja().get(GregorianCalendar.HOUR_OF_DAY);
			niz[satRodjenja]++;
		}

		int[] nizVracen = instance.vratiFrekvencijskuTabelu();

		assertTrue("Frekvencijska tabela ima " + nizVracen.length + " elemenata, a potrebno je da ima " + niz.length,
				niz.length == nizVracen.length);

		int duzina = niz.length > nizVracen.length ? nizVracen.length : niz.length;

		for (int i = 0; i < duzina; i++) {
			assertTrue("Za bebe rodjene izmedju " + i + ":00 i " + i + ":59, frekvencijska tabele ima vrednost "
					+ nizVracen[i] + ", a potrebno je da ima vrednost " + niz[i], niz[i] == nizVracen[i]);
		}
	}
}
