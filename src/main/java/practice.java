import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mkyong.client.JCliGet;



public class practice {

	private String idEquipe;
	private String idPartie;
	private String idAdversaire;
	private String plateau;
	
	private String resp = "";
	private String attack = "";
	private int nbTour = 1;
	
	
	private JSONArray[] perso = new JSONArray[3];
	 
	static JCliGet client = new JCliGet();
	Gson gson = new Gson();
	
	
	public practice()
	{
		newGame();
	}
	
	
//initialise la partie
		private void newGame()
		{
			this.idEquipe = client.connect(); // recupération de l'identifiant du client	
			this.idPartie = client.affrontementBot(5, this.idEquipe); // creation de la nouvelle partie contre l'ia
		//	this.plateau = client.plateau(this.idPartie); // creation du tableau
			jouerPartie(idPartie, idEquipe);
		}
	
	public void jouerPartie(String idPartie, String idEquipe) 
	{
		resp = client.partie(idPartie, idEquipe); // recupère le statu de la partie
		do {
			try {
				switch (resp)
				{
					case "CANPLAY":
						this.action();
						System.out.println("dernier coup joueur : "+nbTour +" "+ client.dernierCoup(idPartie, idEquipe));
						break;
						
					case "CANTPLAY":
							Thread.sleep(500);
							System.out.println("can't play");
						break;
						
					case "VICTORY":
						System.out.println("victoire");
						break;
						
					case "DEFEAT":
						System.out.println("defaite");
						return;
						
					
					case "DRAW":
						System.out.println("draw");
						return;
					
					case "CANCELLED":
						System.out.println("annulé");
						return;	
				}
				
				System.out.println("etat plateau : "+client.plateau(idPartie));
				resp = client.partie(idPartie, idEquipe); // recupère le statu de la partie
				System.out.println(resp);
				
				
				nbTour++;
				
				
			}catch(InterruptedException e) {e.printStackTrace();}
			
		}while(resp != "VICTORY" && resp != "DEFEAT" &&resp != "DRAW" && resp != "CANCELLED");
	}
	
	// apelle le serveur avec l'attaque et regarde la valeur de retour
	private void action()
	{
		//	attack = client.move(this.idPartie, this.idEquipe, move);
			if (nbTour<4)
			{
				switch(nbTour)
				{
				case 1: client.move(this.idPartie, this.idEquipe, "PRIEST");break;
				case 2: client.move(this.idPartie, this.idEquipe, "ORC");break;
				case 3: client.move(this.idPartie, this.idEquipe, "GUARD");break;
				}
			}
			
			else 
			{
				String a = client.move(idPartie, idEquipe, "A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3");
				switch (a)
				{
				case "OK":
					System.out.println("ok");
					break;
				
				case "FORBIDDEN":// defaite sur la partie
					System.out.println("interdit"); 
				//	resp = "DEFEAT";
					break;
					
				case "NOTYET":
					System.out.println("pas encore");
					break;
				
				case "GAMEOVER":
					System.out.println("partie fini");
				}	
			}
//					
	}
	
	

}
