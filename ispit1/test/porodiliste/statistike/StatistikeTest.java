package porodiliste.statistike;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import porodiliste.statistika.Statistike;
import test.TestUtil;
import java.lang.reflect.Modifier;

public class StatistikeTest {

	@Test
	public void metoda_upisiEkstreme() {
		assertTrue("Metoda upisiEkstreme nije javna", TestUtil.hasMethodModifier(Statistike.class, "upisiEkstreme",
				new Class<?>[] { String.class }, Modifier.PUBLIC));
	}

	@Test
	public void metoda_vratiFrekvencijskuTabelue() {
		assertTrue("Metoda vratiFrekvencijskuTabelu nije javna",
				TestUtil.hasMethodModifier(Statistike.class, "vratiFrekvencijskuTabelu", null, Modifier.PUBLIC));
	}

}
