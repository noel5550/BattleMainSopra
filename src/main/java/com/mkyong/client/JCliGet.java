package com.mkyong.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class JCliGet {

   public void test() {
	   Client client = ClientBuilder.newClient();
	  
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/ping");
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	   
	   String resp = response.readEntity(String.class);
	   System.out.println(resp);
   }
   
   public void connect() {
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/player/getIdEquipe/NoelEtSesAmisInferieurs/KJheror883!");
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	   
	   String resp = response.readEntity(String.class);
	   System.out.println(resp);
   }
   
   
   //Retourne l’identifiant de la partie à laquelle l’équipe doit participer  "NA" si aucune partie n'est ouverte pour cette équipe.
   public String affrontementJoueur(String idEquipe)
   {

	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/versus/next/"+idEquipe);
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	   
	   String resp = response.readEntity(String.class);
	   System.out.println(resp);

	   return resp;
	   
   }
   
   public String affrontementBot()
   {
	   
   }
   
   
}
