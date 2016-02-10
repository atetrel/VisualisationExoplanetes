package model;

public class Planete {
	String methode;
	String periode_orbitale;
	String semi_grand_axe;
	String masse;
	String distance;
	String masse_etoile;
	String rayon_planete;
	String densite;

	public String getMethode() {
		return methode;
	}
	public void setMethode(String methode) {
		this.methode = methode;
	}
	public String getPeriode_orbitale() {
		return periode_orbitale;
	}
	public void setPeriode_orbitale(String periode_orbitale) {
		this.periode_orbitale = periode_orbitale;
	}
	public String getSemi_grand_axe() {
		return semi_grand_axe;
	}
	public void setSemi_grand_axe(String semi_grand_axe) {
		this.semi_grand_axe = semi_grand_axe;
	}
	public String getMasse() {
		return masse;
	}
	public void setMasse(String masse) {
		this.masse = masse;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getMasse_etoile() {
		return masse_etoile;
	}
	public void setMasse_etoile(String masse_etoile) {
		this.masse_etoile = masse_etoile;
	}

	public String getRayon_planete () {return rayon_planete;}
	public void setRayon_planete(String rayon_planete) {this.rayon_planete = rayon_planete;}

    public String getDensite() {
        return densite;
    }

    public void setDensite(String densite) {
        this.densite = densite;
    }

    public Planete(String methode, String periode_orbitale, String semi_grand_axe, String masse, String distance,
                   String masse_etoile, String rayon_planete, String densite) {
		super();
		this.methode = methode;
		this.periode_orbitale = periode_orbitale;
		this.semi_grand_axe = semi_grand_axe;
		this.masse = masse;
		this.distance = distance;
		this.masse_etoile = masse_etoile;
		this.rayon_planete = rayon_planete;
		this.densite = densite;
	}
	

	
	
}