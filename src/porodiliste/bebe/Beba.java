package porodiliste.bebe;

import java.util.GregorianCalendar;

public class Beba {

	private String ime = null;
	private GregorianCalendar vremeRodjenja = null;
	private int tezina = 0;
	private int duzina = 0;

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		if (ime == null || ime.length() < 5)
			throw new RuntimeException("Ime je null ili je prekratko.");

		this.ime = ime;
	}

	public GregorianCalendar getVremeRodjenja() {
		return vremeRodjenja;
	}

	public void setVremeRodjenja(GregorianCalendar vremeRodjenja) {
		if (vremeRodjenja.after(new GregorianCalendar()))
			throw new RuntimeException("Vreme rodjenja ne moze biti u buducnosti");

		this.vremeRodjenja = vremeRodjenja;
	}

	public int getTezina() {
		return tezina;
	}

	public void setTezina(int tezina) {
		if (tezina < 500 || tezina > 8000)
			throw new RuntimeException("Tezina je van raspona");

		this.tezina = tezina;
	}

	public int getDuzina() {
		return duzina;
	}

	public void setDuzina(int duzina) {
		if (duzina <= 25)
			throw new RuntimeException("Duzina mora biti veca od 25");

		this.duzina = duzina;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Beba))
			return false;
		Beba other = (Beba) obj;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (vremeRodjenja == null) {
			if (other.vremeRodjenja != null)
				return false;
		} else if (!vremeRodjenja.equals(other.vremeRodjenja))
			return false;
		return true;
	}

	public String toString() {
		return "Beba [ime=" + ime + ", vremeRodjenja=" + vremeRodjenja.getTime() + ", tezina=" + tezina + ", duzina="
				+ duzina + "]";
	}
}