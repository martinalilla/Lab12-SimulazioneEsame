package it.polito.tdp.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum tipoEvento{
		CRIMINE,
		LIBERA_AGENTE
	
	}
	private LocalDateTime data;
	private tipoEvento tipoEvento;
	private Event Crimine;
	private Agente agente;
	
	
	

	public Evento(LocalDateTime data, tipoEvento tipoEvento, Event crimine) {
		super();
		this.data = data;
		this.tipoEvento = tipoEvento;
		Crimine = crimine;
		this.agente=null;
	}




	public LocalDateTime getData() {
		return data;
	}




	public void setData(LocalDateTime data) {
		this.data = data;
	}




	public tipoEvento getTipoEvento() {
		return tipoEvento;
	}




	public void setTipoEvento(tipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}




	public Event getCrimine() {
		return Crimine;
	}




	public void setCrimine(Event crimine) {
		Crimine = crimine;
	}




	@Override
	public int compareTo(Evento altro) {
		
		return this.data.compareTo(altro.data);
	}




	public Agente getAgente() {
		return agente;
	}




	public void setAgente(Agente agente) {
		this.agente = agente;
	}




	@Override
	public String toString() {
		return "Evento [data=" + data + ", tipoEvento=" + tipoEvento + ", Crimine=" + Crimine + "]";
	}
	

}
