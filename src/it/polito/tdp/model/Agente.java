package it.polito.tdp.model;

public class Agente {
	
	private int id;
	private District doveSiTrova;
	boolean libero;
	public Agente(int id, District d) {
		super();
		this.id = id;
		this.libero = true;
		this.doveSiTrova=d;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isLibero() {
		return libero;
	}
	public void setLibero(boolean libero) {
		this.libero = libero;
	}
	public void occupaAgente() {
		this.libero = false;
	}
	public District getDoveSiTrova() {
		return doveSiTrova;
	}
	public void setDoveSiTrova(District doveSiTrova) {
		this.doveSiTrova = doveSiTrova;
	}
	@Override
	public String toString() {
		return "Agente [id=" + id + ", doveSiTrova=" + doveSiTrova + ", libero=" + libero + "]";
	}
	
	
	
	
	
	
	

}
