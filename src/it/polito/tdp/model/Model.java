package it.polito.tdp.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	List<Event> eventi;
	EventsDao dao;
	Graph<District, DefaultWeightedEdge> grafo;
	List<Vicini> vicini;
	
	public Model() {
		this.dao=new EventsDao();
		eventi=new LinkedList<Event>();
		eventi.addAll(dao.listAllEvents());
	}
	
	
	
	
	public List<Integer> caricaDate(){
		List<Integer> anni = new LinkedList<Integer>();
		
			anni.addAll(dao.caricaAnni());
		
		return anni;
	}
	
	public void creaGrafo(int anno) {
		grafo=new SimpleWeightedGraph<District, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.caricaDistretti(anno));
		vicini=new LinkedList<Vicini>();
		
		for(District d1: grafo.vertexSet()) {
			for(District d2: grafo.vertexSet()) {
				if(!d1.equals(d2) && !grafo.containsEdge(d1, d2)) {
				double distanza=calcolaDistanza(d1, d2);
				Graphs.addEdge(grafo, d1, d2, distanza);
				vicini.add(new Vicini(d1, d2, distanza));
				}
			}
		}
		System.out.println("Grafo creato!");
		System.out.println("# vertici: " + this.grafo.vertexSet().size());
		System.out.println("# archi: " + this.grafo.edgeSet().size());
		System.out.println(grafo.toString());
		
	}
	
	public double calcolaDistanza(District d1, District d2) {
		double distanza=LatLngTool.distance(d1.getCentro(), d2.getCentro(), LengthUnit.KILOMETER);
		return distanza;
		
	}




	public Graph<District, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}




	public void setGrafo(Graph<District, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}
	
	public String visualizzaAdiacenti() {
		List<Vicini> v;
		String s="";
		for(District d1: grafo.vertexSet()) {
			s+="Elenco dei vicini del distretto "+d1.toString()+" : \n";
			v=new LinkedList<Vicini>();
			for(District d2: Graphs.neighborListOf(grafo, d1)){
				v.add(new Vicini(d1, d2, grafo.getEdgeWeight(grafo.getEdge(d1, d2))));
			}
			Collections.sort(v);
			for(Vicini vi: v) {
				s+=vi.toString()+"\n";
			}
			
	}
		return s;
	
	
}
	public int simula( int nAgenti, int anno, int mese, int giorno) {
		Simulatore sim=new Simulatore();
		District centrale=dao.minorCriminalita(anno, grafo);
		System.out.println("Centrale: "+centrale.toString());
		sim.init(grafo, centrale, nAgenti, dao.eventiDATA(anno, mese, giorno));
		sim.run();
		System.out.println(sim.getMalgestiti());
		return sim.getMalgestiti();
	}
}
