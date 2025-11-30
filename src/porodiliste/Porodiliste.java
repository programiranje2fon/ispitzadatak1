package porodiliste;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import porodiliste.bebe.Beba;
import porodiliste.statistika.Statistike;

public class Porodiliste implements Statistike {

	private Beba[] bebe;

	public Porodiliste(int kapacitet) throws Exception {
		if (kapacitet <= 0)
			throw new Exception("Kapacitet ne sme biti nula niti negativan");

		bebe = new Beba[kapacitet];
	}

	public void upisiEkstreme(String naziv) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(naziv));

			Beba najteza = null;
			// krecemo od pocetka niza i za svaku bebu proveravamo
			// da li je uneta(razlicita od null), prva beba koja nije
			// null ce biti najteza, a zatim se nastavlja do kraja niza
			// svaki put kada se pronadje beba koja je teza od trenutno najteze,
			// ona ce biti postati najteza
			for (int i = 0; i < bebe.length; i++) {
				if (bebe[i] != null) {
					if (najteza == null || bebe[i].getTezina() > najteza.getTezina()) {
						najteza = bebe[i];
					}
				}
			}

			// ako je najteza i dalje null
			// znaci da ni jedna beba nije uneta
			// pa se zato u fajl nista ne upisuje
			if (najteza != null) {
				out.write(najteza.getIme() + '\t' + najteza.getTezina() + '\t' + najteza.getDuzina());
			}

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Beba> otpustiIzPorodilista(GregorianCalendar datum) {
		// Pravi se nova prazna lista
		LinkedList<Beba> novaLista = new LinkedList<Beba>();

		// Prolazi se kroz niz i svaka beba ciji je datum rodjenja
		// pre unetog datuma se dodaje (kopira) u listu.
		for (int i = 0; i < bebe.length; i++) {
			if (bebe[i] != null && bebe[i].getVremeRodjenja().before(datum)) {
				novaLista.add(bebe[i]);
			}
		}

		// Na kraju, metoda vraca novu listu.
		return novaLista;
	}

	public int[] vratiFrekvencijskuTabelu() {
		// Pravi se novi niz sa tacno 24 elementa
		int[] niz = new int[24];

		// Svi elementi novog niza se postavljaju na 0 da bi
		// prebrojavanje moglo da pocne.
		for (int i = 0; i < niz.length; i++)
			niz[i] = 0;

		// Prolazi se kroz niz beba i za svaku od njih se
		// proverava u kojem satu je rodjena (GregorianCalendar.HOUR_OF_DAY).
		// Onda se odgovarajuci element novog niza uvecava za jedan.
		for (int i = 0; i < bebe.length; i++) {
			if (bebe[i] != null) {
				int satRodjenja = bebe[i].getVremeRodjenja().get(GregorianCalendar.HOUR_OF_DAY);
				niz[satRodjenja]++;
			}
		}

		return niz;
	}

}