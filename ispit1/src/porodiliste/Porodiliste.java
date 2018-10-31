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
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(naziv)));

			// Pocetna vrednost za najtezu bebu je prva iz niza
			Beba najteza = bebe[0];

			// Petlja polazi od drugog mesta u nizu (prva beba je
			// postavljena kao pocetna vrednost za najtezu bebu)
			// i u svakom koraku proverava da li je trenutna beba
			// iz niza teza od najteze. Ako jeste onda ta nova beba
			// postaje najteza.
			for (int i = 1; i < bebe.length; i++) {
				if (bebe[i].getTezina() > najteza.getTezina()) {
					najteza = bebe[i];
				}
			}

			// Upisivanje podataka najteze bebe u data fajl.
			out.writeUTF(najteza.getIme());
			out.writeChar('\t');
			out.writeInt(najteza.getTezina());
			out.writeChar('\t');
			out.writeInt(najteza.getDuzina());

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Beba> otpustiIzPorodilista(GregorianCalendar datum) {
		// Prav se nova prazna lista
		LinkedList<Beba> novaLista = new LinkedList<Beba>();

		// Prolazi se kroz niz i svaka beba ciji je datum rodjenja
		// pre unetog datuma se dodaje (kopira) u listu.
		for (int i = 0; i < bebe.length; i++)
			if (bebe[i].getVremeRodjenja().before(datum))
				novaLista.add(bebe[i]);

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
			int satRodjenja = bebe[i].getVremeRodjenja().get(GregorianCalendar.HOUR_OF_DAY);
			niz[satRodjenja]++;
		}

		return niz;
	}

}