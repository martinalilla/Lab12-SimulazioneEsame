package it.polito.tdp.model;

public class Vicini implements Comparable<Vicini>{
	private District d1;
	private District d2;
	private double distanza;
	public Vicini(District d1, District d2, double distanza) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.distanza = distanza;
	}
	public District getD1() {
		return d1;
	}
	public void setD1(District d1) {
		this.d1 = d1;
	}
	public District getD2() {
		return d2;
	}
	public void setD2(District d2) {
		this.d2 = d2;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	@Override
	public int compareTo(Vicini altro) {
		// TODO Auto-generated method stub
		return (int)(this.distanza-altro.distanza);
	}
	@Override
	public String toString() {
		return "Vicini [d1=" + d1 + ", d2=" + d2 + ", distanza=" + distanza + "]";
	}
	
	
	
	

}
