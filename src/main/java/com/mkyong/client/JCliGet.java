package com.mkyong.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

import java.util.Random;

public class JCliGet {

   public void test() {
	   Client client = ClientBuilder.newClient();
	  
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/ping");
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	   
	   String resp = response.readEntity(String.class);
	   System.out.println(resp);
   }
   
   /**
 *  retourne l'identifiant de l'�quipe
 *  fonction � modifier (la remettre en void)
 */
public String connect() {
	
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/player/getIdEquipe/NoelEtSesAmisInferieurs/KJheror883!");
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	   
	   String resp = response.readEntity(String.class);
	   System.out.println(resp);
	   return resp;
   }
   
   
   /**
	//Retourne l�identifiant de la partie � laquelle l��quipe doit participer  "NA" si aucune partie n'est ouverte pour cette �quipe.
 * @param idEquipe
 * @return
 */
public String affrontementJoueur(String idEquipe)   {
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/versus/next/"+idEquipe);
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	   
	   String resp = response.readEntity(String.class);
	   System.out.println(resp);

	   return resp;
	   
   }
   
   /**
   * //Retourne l�identifiant de la partie � laquelle l��quipe doit participer  "NA" si aucune partie n'est ouverte pour cette �quipe.
 * @param nbIA
 * @param idEquipe
 * @return
 */
public String affrontementBot(int nbIA, String idEquipe)  {
	    String resp = "";
		if (0<nbIA && nbIA<6 ){
			Client client = ClientBuilder.newClient();
			   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/practice/new/"+nbIA+"/"+idEquipe);
			   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
			   Response response = invocationBuilder.get();
			   
			   resp = response.readEntity(String.class);
			   System.out.println(resp);
		}
	   return resp;
   }
   

//DEROULEMENT DE PARTIE


/**
 * retourne l'�tat de la partie 
   s pouvez jouer 
   "CANTPLAY" si vous ne pouvez pas encore jouer 
   "VICTORY" si vous avez gagn� la partie 
   "DEFEAT" si vous avez perdu la partie 
   "DRAW" si la partie s'est finie sur un match nul 
   "CANCELLED" si la partie a �t� annul�e
* @param idPartie
* @param idEquipe
* @return"CANPLAY" 
*/
public String partie(String idPartie , String idEquipe)   {
	
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/status/"+idPartie+"/"+idEquipe);
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	  
	   String resp = response.readEntity(String.class);
	   response.getStatus();
	   return resp;
}


//Retourne le plateau de jeu de la partie concern�e.
// le plateau est au format JSON
public String plateau(String idPartie) 
{
	   Client client = ClientBuilder.newClient();
	//   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/board/"+idPartie+"?format=(JSON|String|XML)");
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/board/"+idPartie);
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	  
	   String resp = response.readEntity(String.class);
	 
	  
	   return resp;
}

//Retourne le plateau de jeu de la partie concern�e.
//La premi�re �quipe retourn�e est celle dont l'id est renseign�.
public String plateau(String idPartie, String idEquipe)
{
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/board/"+idPartie+"?format=JSON");
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	  
	   String resp = response.readEntity(String.class);
	
	   return resp;
}

//Retourne le dernier coup jou� sur le plateau de la partie indiqu�e
//
//Retourne le dernier coup de l'adversaire au tour pr�c�dent (ex: ORC (choix h�ros) A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3 (ordre d'actions))
//Si aucun coup n'a �t� jou� sur cette partie, retourne "NA".
//
//Exemple de retour: A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3 Le pr�c�dent coup de l'adversaire �tait : 
//son h�ros 1 attaque votre h�ros 1 
//son h�ros 2 lance une fl�che enflamm�e sur votre h�ros 1 
//son h�ros 3 se repose 
public String dernierCoup(String idPartie , String idEquipe)
{
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/getlastmove/"+idPartie+"/"+idEquipe);
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	  
	   String resp = response.readEntity(String.class);
	   response.getStatus();
	   return resp;
}
   

//Joue un coup dans la partie concern�e pour l��quipe indiqu�e.
//Forme du coup : 
//S�lection d'un personnage (Tours 1 � 3) : {Nom du personnage}. Par exemple : ORC. La conversion des diff�rents h�ros est disponible ici.
//R�alisation des actions (Tours 4 � 54) : {Num�ro de personnage 1},{Coup 1},{Cible 1}${Num�ro de personnage 2},{Coup 2},{Cible 2}${Num�ro de personnage 3},{Coup 3},{Cible 3}. Par exemple : A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3. La conversion des diff�rentes actions est disponible ici.
//Retourne : 
//"OK" si le coup est accept�
//"FORBIDDEN" si le coup est refus�/interdit (entraine la d�faite sur cette partie)
//"NOTYET" si ce n'est pas au tour du joueur
//"GAMEOVER" si le coup est jou� sur une partie finie, quel que soit son �tat

public String move(String idPartie , String idEquipe , String move)
{
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/play/"+idPartie+"/"+idEquipe+"/"+move);
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	  
	   String resp = response.readEntity(String.class);
	   response.getStatus();
	   return resp;
}


public String nomAdversaire(String idPartie, String idEquipe)
{
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/opponent/"+idPartie+"/"+idEquipe);
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	  
	   String resp = response.readEntity(String.class);
	   response.getStatus();
	   return resp;
}


}
