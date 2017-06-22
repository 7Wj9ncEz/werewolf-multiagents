package werewolf;
import jade.core.Agent;
import jade.core.Runtime;
import jade.core.AgentContainer;
import jade.core.ProfileImpl;

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
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class CreatorAgents extends Agent {
	protected void setup(){
		System.out.println("Creating Agents");
		createAgents();
		super.takeDown();
	}
	void createAgents(){
		Runtime rt = Runtime.instance();
	    ProfileImpl p = new ProfileImpl(false);
	    ContainerController cc = rt.createAgentContainer(p);

	    for (int j=0; j < 10;j++){
	        try{ 
	        	Object reference = new Object(); 
	            Object args[] = new Object[1]; 
	            args[0] = reference; 
	            AgentController dummy;
	            AgentController dummy2;
	            
	            dummy = cc.createNewAgent("Villager"+j, "werewolf.VillagerAgent", args);
	            dummy.start();  
	            
	            dummy2 = cc.createNewAgent("Wolf"+j, "werewolf.WolfAgent", args);
	            dummy2.start();  
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
