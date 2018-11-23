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
 *  retourne l'identifiant de l'équipe
 *  fonction à modifier (la remettre en void)
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
	//Retourne l’identifiant de la partie à laquelle l’équipe doit participer  "NA" si aucune partie n'est ouverte pour cette équipe.
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
   * //Retourne l’identifiant de la partie à laquelle l’équipe doit participer  "NA" si aucune partie n'est ouverte pour cette équipe.
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
 * retourne l'état de la partie 
   s pouvez jouer 
   "CANTPLAY" si vous ne pouvez pas encore jouer 
   "VICTORY" si vous avez gagné la partie 
   "DEFEAT" si vous avez perdu la partie 
   "DRAW" si la partie s'est finie sur un match nul 
   "CANCELLED" si la partie a été annulée
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


//Retourne le plateau de jeu de la partie concernée.
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

//Retourne le plateau de jeu de la partie concernée.
//La première équipe retournée est celle dont l'id est renseigné.
public String plateau(String idPartie, String idEquipe)
{
	   Client client = ClientBuilder.newClient();
	   WebTarget webTarget = client.target("http://codeandplay.pw/epic-ws/epic/game/board/"+idPartie+"?format=JSON");
	   Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	   Response response = invocationBuilder.get();
	  
	   String resp = response.readEntity(String.class);
	
	   return resp;
}

//Retourne le dernier coup joué sur le plateau de la partie indiquée
//
//Retourne le dernier coup de l'adversaire au tour précédent (ex: ORC (choix héros) A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3 (ordre d'actions))
//Si aucun coup n'a été joué sur cette partie, retourne "NA".
//
//Exemple de retour: A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3 Le précédent coup de l'adversaire était : 
//son héros 1 attaque votre héros 1 
//son héros 2 lance une flèche enflammée sur votre héros 1 
//son héros 3 se repose 
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
   

//Joue un coup dans la partie concernée pour l’équipe indiquée.
//Forme du coup : 
//Sélection d'un personnage (Tours 1 à 3) : {Nom du personnage}. Par exemple : ORC. La conversion des différents héros est disponible ici.
//Réalisation des actions (Tours 4 à 54) : {Numéro de personnage 1},{Coup 1},{Cible 1}${Numéro de personnage 2},{Coup 2},{Cible 2}${Numéro de personnage 3},{Coup 3},{Cible 3}. Par exemple : A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3. La conversion des différentes actions est disponible ici.
//Retourne : 
//"OK" si le coup est accepté
//"FORBIDDEN" si le coup est refusé/interdit (entraine la défaite sur cette partie)
//"NOTYET" si ce n'est pas au tour du joueur
//"GAMEOVER" si le coup est joué sur une partie finie, quel que soit son état

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
