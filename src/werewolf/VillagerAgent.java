package werewolf;
import jade.core.Agent;

import java.util.Random;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;


public class VillagerAgent extends Agent {
	private String type;
	AMSAgentDescription[] agents = null;
	
	protected void setup(){
		System.out.println("Hi, I am the villager: " + getAID().getName());
		setType("villager");
		addBehaviour(new VillagerBehaviour());
	}
	
	protected void takeDown(){
		System.out.println(getAID().getName() + ": morri");
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

	private class VillagerBehaviour extends CyclicBehaviour {
		public void action(){
			getAgents();
			
			ACLMessage accusation = new ACLMessage(ACLMessage.REQUEST);
			Random rand = new Random();
			int random_integer = rand.nextInt(agents.length);
			if(agents.length != 0){
				accusation.addReceiver(agents[random_integer].getName());
				accusation.setLanguage("English");
				accusation.setContent("Are you the wolf?");
				send(accusation);
			}
			ACLMessage msg = myAgent.receive();
			if (msg != null){
				if(msg.getContent().equals("Die")){
					doDelete();
				}else if(msg.getContent().equals("Are you the wolf?")){
					ACLMessage reply = msg.createReply();
					reply.setContent("Não sou, trouxa!");
					send(reply);
				}else{
//					System.out.println(msg.getContent());
				}
			}else{
			}
		}
	}
}

