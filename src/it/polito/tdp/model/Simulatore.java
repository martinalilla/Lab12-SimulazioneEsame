package it.polito.tdp.model;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.model.Evento.tipoEvento;



public class Simulatore {
	
	//Tipi di eventi/coda degli eventi
	PriorityQueue<Evento> queue;
	
	//Modello del mondo
	private District centrale;
	private Graph<District, DefaultWeightedEdge> grafo;
	private Map<Integer, Agente> idMapAgenti;
	private List<Event> crimini;
	private Model model;
	
	//Statistiche di simulazione
	private int malgestiti;
	
	public void init(Graph<District, DefaultWeightedEdge> grafo, District centrale, int nAgenti, List<Event> crimini) {
	this.queue=new PriorityQueue<Evento>();
	this.centrale=centrale;
	this.grafo=grafo;
	this.idMapAgenti=new TreeMap<Integer, Agente>();
	System.out.println("Carico gli agenti...");
	for(int i=1; i<=nAgenti; i++) {
		Agente ag=new Agente(i, centrale);
		System.out.println(ag.toString());
		idMapAgenti.put(i,ag);
	}
	this.crimini=crimini;
	System.out.println("Aggiungo tutti i crimini di quel giorno alla coda degli eventi...");
	for(Event c: crimini) {
		Evento ev=new Evento(c.getReported_date(), tipoEvento.CRIMINE, c);
		System.out.println(ev.toString());
		queue.add(ev);
	}
	model=new Model();
	

}
	public void run() {
		System.out.println("Estraggo tutti gli eventi dalla coda...");
		Evento e;
		while((e=queue.poll())!=null) {
			System.out.println(e.toString());
			District luogo=null;
			Agente scelto=null;
			double piuvicino=10000;
			for(District d: grafo.vertexSet()) {
				if(d.getId()==e.getCrimine().getDistrict_id()) {
					luogo=d;
				}
			}
			
			
			switch(e.getTipoEvento()) {
			case CRIMINE :
				for(Agente a: idMapAgenti.values()) {
					if(model.calcolaDistanza(luogo, a.getDoveSiTrova())<piuvicino) {
						if(a.libero==true) {
						scelto=a;
						a.occupaAgente();
						a.setDoveSiTrova(luogo);
						piuvicino=model.calcolaDistanza(luogo, a.getDoveSiTrova());
						e.setAgente(scelto);
						}
					}
				}
				if(piuvicino>15 || scelto==null) {
					malgestiti++;
				} else {
				if(e.getCrimine().getOffense_category_id().compareTo("all_other_crimes")==0) {
					Random r = new Random();
					if(r.nextDouble()>0.5) {
						Evento nuovo=new Evento(e.getCrimine().getReported_date().plusMinutes(60), tipoEvento.LIBERA_AGENTE, e.getCrimine());
						nuovo.setAgente(scelto);
						queue.add(nuovo);
					} else {
						Evento nuovo=new Evento(e.getCrimine().getReported_date().plusMinutes(120), tipoEvento.LIBERA_AGENTE, e.getCrimine());
						nuovo.setAgente(scelto);
						queue.add(nuovo);
						
					}
				}
				else {
					Evento nuovo=new Evento(e.getCrimine().getReported_date().plusMinutes(120), tipoEvento.LIBERA_AGENTE, e.getCrimine());
					nuovo.setAgente(scelto);
					queue.add(nuovo);
				}
				}
				break;
			case LIBERA_AGENTE :
				e.getAgente().setLibero(true);
				break;
			
		}
			
	}
	}
	public int getMalgestiti() {
		return malgestiti;
	}
	public void setMalgestiti(int malgestiti) {
		this.malgestiti = malgestiti;
	}
	
		
	
}


