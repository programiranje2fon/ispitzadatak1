package porodiliste.bebe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Modifier;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import porodiliste.bebe.Beba;
import test.TestUtil;

public class BebaTest {

	Beba instance;

	@Before
	public void setUp() throws Exception {
		instance = new Beba();
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
	}

	@Test
	public void atribut_ime() {
		assertTrue("U klasi nije definisan atribut ime", TestUtil.doesFieldExist(Beba.class, "ime"));
	}

	@Test
	public void atribut_ime_pocetnaVrednost() {
		String pocetna = null;
		String imeValue = (String) TestUtil.getFieldValue(instance, "ime");
		assertTrue("Atribut ime pocetnu vrednost " + imeValue + " , a potrebno je da ima vrednost " + pocetna,
				imeValue == pocetna);
	}

	@Test
	public void atribut_ime_vidljivost() {
		assertTrue("Atribut ime nije privatan", TestUtil.hasFieldModifier(Beba.class, "ime", Modifier.PRIVATE));
	}

	@Test
	public void atribut_vremeRodjenja() {
		assertTrue("U klasi nije definisan atribut vremeRodjenja",
				TestUtil.doesFieldExist(Beba.class, "vremeRodjenja"));
	}

	@Test
	public void atribut_vremeRodjenja_vidljivost() {
		assertTrue("Atribut vremeRodjenja nije privatan",
				TestUtil.hasFieldModifier(Beba.class, "vremeRodjenja", Modifier.PRIVATE));
	}

	@Test
	public void atribut_vremeRodjenja_pocetnaVrednost() {
		LocalDateTime pocetna = null;
		LocalDateTime vremeRodjenjaValue = (LocalDateTime) TestUtil.getFieldValue(instance, "vremeRodjenja");
		assertTrue("Atribut vremeRodjenja ima pocetnu vrednost "
				+ (vremeRodjenjaValue == null ? null : vremeRodjenjaValue)
				+ ", a potrebno je da ima vrednost " + pocetna, vremeRodjenjaValue == pocetna);
	}

	@Test
	public void atribut_tezina() {
		assertTrue("U klasi nije definisan atribut tezina", TestUtil.doesFieldExist(Beba.class, "tezina"));
	}

	@Test
	public void atribut_tezina_vidljivost() {
		assertTrue("Atribut tezina nije privatan", TestUtil.hasFieldModifier(Beba.class, "tezina", Modifier.PRIVATE));
	}

	@Test
	public void atribut_tezina_pocetnaVrednost() {
		int pocetna = 0;
		int tezinaValue = (int) TestUtil.getFieldValue(instance, "tezina");
		assertTrue("Atribut tezina ima pocetnu vrednost " + tezinaValue + ", a potrebno je da ima vrednost " + pocetna,
				tezinaValue == pocetna);
	}

	@Test
	public void atribut_duzina() {
		assertTrue("U klasi nije definisan atribut duzina", TestUtil.doesFieldExist(Beba.class, "duzina"));
	}

	@Test
	public void atribut_duzina_vidljivost() {
		assertTrue("Atribut duzina nije privatan", TestUtil.hasFieldModifier(Beba.class, "duzina", Modifier.PRIVATE));
	}

	@Test
	public void atribut_duzina_pocetnaVrednost() {
		int pocetna = 0;
		int duzinaValue = (int) TestUtil.getFieldValue(instance, "duzina");
		assertTrue("Atribut duzina ima pocetnu vrednost " + duzinaValue + ", a potrebno je da ima vrednost " + pocetna,
				duzinaValue == pocetna);
	}

	@Test(expected = RuntimeException.class)
	public void metoda_setIme_null() {
		String ime = null;
		try {
			instance.setIme(ime);
		} catch (RuntimeException e) {
			if (e.getClass() == RuntimeException.class) {
				throw e;
			}
			e.printStackTrace();
		}
		assertTrue("Za prosledjeni argument " + ime + " metoda setIme ne baca neproveravani izuzetak", false);
	}

	@Test(expected = RuntimeException.class)
	public void metoda_setIme_kraciOd5Znakova() {
		String ime = "asdf";
		try {
			instance.setIme(ime);
		} catch (RuntimeException e) {
			if (e.getClass() == RuntimeException.class) {
				throw e;
			}
			e.printStackTrace();
		}
		assertTrue("Za prosledjeni argument " + ime + " metoda setIme ne baca neproveravani izuzetak", false);
	}

	@Test
	public void metoda_setIme_MarkoJankovic() {
		String ime = "Marko Jankovic";
		instance.setIme(ime);

		String imeValue = (String) TestUtil.getFieldValue(instance, "ime");
		assertEquals("Za prosledjen argument " + ime + " atribut ime ima vrednost " + imeValue, ime, imeValue);
	}

	@Test
	public void metoda_setIme_asdfg() {
		String ime = "asdfg";
		instance.setIme(ime);

		String imeValue = (String) TestUtil.getFieldValue(instance, "ime");
		assertEquals("Za prosledjen argument " + ime + " atribut ime ima vrednost " + imeValue, ime, imeValue);
	}

	@Test(expected = RuntimeException.class)
	public void metoda_setVremeRodjenja_nakonTrenutnogDatuma() {
		LocalDateTime kalendar = LocalDateTime.now().plusDays(2);
		try {
			instance.setVremeRodjenja(kalendar);
		} catch (RuntimeException e) {
			if (e.getClass() == RuntimeException.class) {
				throw e;
			}
			e.printStackTrace();
		}
		assertTrue("Za prosledjeni argument " + kalendar
				+ " metoda setVremeRodjenja ne baca neproveravani izuzetak", false);
	}

	@Test
	public void metoda_setVremeRodjenja_preTrenutnogDatuma() {
		LocalDateTime kalendar = LocalDateTime.now().minusDays(2);

		instance.setVremeRodjenja(kalendar);
		LocalDateTime vremeRodjenjaValue = (LocalDateTime) TestUtil.getFieldValue(instance, "vremeRodjenja");

		assertTrue(
				"Za prosledjeni argument " + (kalendar == null ? null : kalendar)
						+ " atribut vremeRodjenja ima vrednost "
						+ (vremeRodjenjaValue == null ? null : vremeRodjenjaValue),
				vremeRodjenjaValue.equals(kalendar));
	}

	@Test(expected = RuntimeException.class)
	public void metoda_setTezina_499() {
		int tezina = 499;
		try {
			instance.setTezina(tezina);
		} catch (RuntimeException e) {
			if (e.getClass() == RuntimeException.class) {
				throw e;
			}
			e.printStackTrace();
		}
		assertTrue("Za prosedjeni argument " + tezina + " metoda setTezina ne baca neproveravani izuzetak", false);

	}

	@Test(expected = RuntimeException.class)
	public void metoda_setTezina_8001() {
		int tezina = 8001;
		try {
			instance.setTezina(tezina);
		} catch (RuntimeException e) {
			if (e.getClass() == RuntimeException.class) {
				throw e;
			}
			e.printStackTrace();
		}
		assertTrue("Za prosedjeni argument " + tezina + " metoda setTezina ne baca neproveravani izuzetak", false);
	}

	@Test
	public void metodad_setTezina_8000() {
		int tezina = 8000;
		instance.setTezina(tezina);

		int tezinaValue = (int) TestUtil.getFieldValue(instance, "tezina");

		assertTrue("Za prosledjeni argument " + tezina + " atribut tezina ima vrednost " + tezinaValue,
				tezinaValue == tezina);
	}

	@Test
	public void metodad_setTezina_500() {
		int tezina = 500;
		instance.setTezina(tezina);

		int tezinaValue = (int) TestUtil.getFieldValue(instance, "tezina");

		assertTrue("Za prosledjeni argument " + tezina + " atribut tezina ima vrednost " + tezinaValue,
				tezinaValue == tezina);
	}

	@Test
	public void metodad_setTezina_1673() {
		int tezina = 1673;
		instance.setTezina(tezina);

		int tezinaValue = (int) TestUtil.getFieldValue(instance, "tezina");

		assertTrue("Za prosledjeni argument " + tezina + " atribut tezina ima vrednost " + tezinaValue,
				tezinaValue == tezina);
	}

	@Test(expected = RuntimeException.class)
	public void metoda_setDuzina_25() {
		int duzina = 25;
		try {
			instance.setDuzina(duzina);
		} catch (RuntimeException e) {
			if (e.getClass() == RuntimeException.class) {
				throw e;
			}
			e.printStackTrace();
		}
		assertTrue("Za prosledjeni argument " + duzina + " metoda setDuzina ne baca neproveravani izuzetak", false);
	}

	@Test
	public void metoda_setDuzina_26() {
		int duzina = 26;
		instance.setDuzina(duzina);

		int duzinaValue = (int) TestUtil.getFieldValue(instance, "duzina");

		assertTrue("Za prosledjeni argument " + duzina + " atribut duzina ima vrednost " + duzinaValue,
				duzinaValue == duzina);
	}

	@Test
	public void metoda_setDuzina_1000() {
		int duzina = 1000;
		instance.setDuzina(duzina);

		int duzinaValue = (int) TestUtil.getFieldValue(instance, "duzina");

		assertTrue("Za prosledjeni argument " + duzina + " atribut duzina ima vrednost " + duzinaValue,
				duzinaValue == duzina);
	}

	private void postaviVrednosti() {
		instance.setIme("Marko Jankovic");
		instance.setDuzina(73);
		instance.setTezina(4078);
		instance.setVremeRodjenja(LocalDateTime.of(1998, 3, 23, 0, 0, 0));
	}

	@Test
	public void metoda_equals_null() {
		postaviVrednosti();
		Object uneti = null;
		boolean rezultat = instance.equals(uneti);
		assertTrue(
				"Za prosledjeni argument " + uneti + " metoda equals nad objektom " + instance + " vraca " + rezultat,
				!rezultat);
	}

	@Test
	public void metoda_equals_objekatDrugeKlase() {
		postaviVrednosti();
		Object uneti = new String("asdf");
		boolean rezultat = instance.equals(uneti);

		assertTrue(
				"Za prosledjeni argument " + uneti + " metoda equals nad objektom " + instance + " vraca " + rezultat,
				!rezultat);
	}

	@Test
	public void metoda_equals_razlicitiDatumiRodjenja() {
		String ime = "Marko Jovanovic";
		instance.setIme(ime);
		instance.setVremeRodjenja(LocalDateTime.of(1998, 2, 23, 0, 0, 0));
		Object uneti = new Beba();
		((Beba) uneti).setIme(ime);
		((Beba) uneti).setVremeRodjenja(LocalDateTime.of(1996, 3, 24, 0 ,0 ,0));
		boolean rezultat = instance.equals(uneti);
		assertTrue(
				"Za prosledjeni argument " + uneti + " metoda equals nad objektom " + instance + " vraca " + rezultat,
				!rezultat);
	}

	@Test
	public void metoda_equals_razlicitaImena() {
		instance.setIme("Marko Jovanovic");
		LocalDateTime vreme = LocalDateTime.of(1998, 2, 23, 0 ,0 ,0);
		instance.setVremeRodjenja(vreme);
		Object uneti = new Beba();
		((Beba) uneti).setIme("Milos Simic");
		((Beba) uneti).setVremeRodjenja(vreme);
		boolean rezultat = instance.equals(uneti);
		assertTrue(
				"Za prosledjeni argument " + uneti + " metoda equals nad objektom " + instance + " vraca " + rezultat,
				!rezultat);
	}

	@Test
	public void metoda_equals_isti() {
		String ime = "Marko Jovanovic";
		instance.setIme(ime);
		LocalDateTime vreme = LocalDateTime.of(1998, 2, 23, 0 ,0 ,0);
		instance.setVremeRodjenja(vreme);
		Object uneti = new Beba();
		((Beba) uneti).setIme(ime);
		((Beba) uneti).setVremeRodjenja(vreme);
		boolean rezultat = instance.equals(uneti);
		assertTrue(
				"Za prosledjeni argument " + uneti + " metoda equals nad objektom " + instance + " vraca " + rezultat,
				rezultat);
	}

	@Test
	public void metoda_toString() {
		instance.setIme("Marko Jankovic");
		instance.setTezina(4320);
		instance.setDuzina(64);
		instance.setVremeRodjenja(LocalDateTime.of(1995, 7, 23, 13 ,14 ,16));

		String rezultat = instance.toString();

		assertTrue("String koji vraca metoda to String ne sadrzi vrednost atributa ime",
				rezultat.indexOf(instance.getIme()) != -1);
		assertTrue("String koji vraca metoda to String ne sadrzi vrednost atributa tezina",
				rezultat.indexOf(((Integer) instance.getTezina()).toString()) != -1);
		assertTrue("String koji vraca metoda to String ne sadrzi vrednost atributa duzina",
				rezultat.indexOf(((Integer) instance.getDuzina()).toString()) != -1);
		assertTrue("String koji vraca metoda to String ne sadrzi godinu rodjenja bebe",
				rezultat.indexOf("1995") != -1);
        assertTrue("String koji vraca metoda to String ne sadrzi mesec rodjenja bebe",
                rezultat.indexOf("7") != -1);
        assertTrue("String koji vraca metoda to String ne sadrzi dan rodjenja bebe",
                rezultat.indexOf("23") != -1);
        assertTrue("String koji vraca metoda to String ne sadrzi sat rodjenja bebe",
                rezultat.indexOf("13") != -1);
        assertTrue("String koji vraca metoda to String ne sadrzi minut rodjenja bebe",
                rezultat.indexOf("14") != -1);
        assertTrue("String koji vraca metoda to String ne sadrzi sekundu rodjenja bebe",
                rezultat.indexOf("16") != -1);
	}
}