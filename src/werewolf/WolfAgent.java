package werewolf;
import jade.core.Agent;

import java.util.Random;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;


public class WolfAgent extends Agent {
	private String type;
	AMSAgentDescription[] agents = null;
	
	protected void setup(){
		System.out.println("I am the wolf!");
		setType("wolf");
		addBehaviour(new WolfBehaviour());
		
	}
	
	protected void takeDown(){
		System.out.println("O lobo " + getAID().getName() + " morreu! " );
	}
	
	void setType(String type){
		this.type = type;
	}
	
	String getType(){
		return type;
	}
	
	void getAgents(){
		try {
	        SearchConstraints c = new SearchConstraints();
	        c.setMaxResults ( new Long(-1) );
	        agents = AMSService.search(this, new AMSAgentDescription (), c );
	    }
	    catch (Exception e) { 
	    	e.printStackTrace();
	    }
	}
	
	private class WolfBehaviour extends CyclicBehaviour {
		public void action(){
			ACLMessage sentence = new ACLMessage(ACLMessage.REQUEST);
			getAgents();
			Random rand = new Random();
			int random_integer = rand.nextInt(agents.length);
			if(agents.length != 0){
				sentence.addReceiver(agents[random_integer].getName());
				sentence.setLanguage("English");
				sentence.setContent("Die");
				send(sentence);
			}
			ACLMessage msg = myAgent.receive();
			if (msg != null){
				String title = msg.getContent();
				if(title.equals("Are you the wolf?")){
					doDelete();
				}
			
			}else{
			}
			
		}
	}
}
