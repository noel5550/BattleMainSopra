import com.mkyong.client.JCliGet;

import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;





public class practice {

	private String idEquipe;
	private String idPartie;
	private String idAdversaire;
	private String plateau;
	private JSONObject plateauJeu;
	
	private String resp = "";
	//private String attack = "";
	private int nbTour = 1;
	
	
	private JSONArray[] perso = new JSONArray[3];
	private String[] attack = new String[3];
	int i = 0;
	 
	static JCliGet client = new JCliGet();

	
	
	
	public practice()
	{	
		newGame();
	}
	
	
//initialise la partie
		private void newGame()
		{
			this.idEquipe = client.connect(); // recupération de l'identifiant du client	
			
			
			this.idPartie = client.affrontementBot(5, this.idEquipe); // creation de la nouvelle partie contre l'ia
			this.plateau = client.plateau(this.idPartie); // creation du plateau
			this.plateauJeu = new JSONObject(this.plateau);
			
		//	System.out.println(plateauJeu);
			jouerPartie(idPartie, idEquipe);
		}
	
	public void jouerPartie(String idPartie, String idEquipe) 
	{
		JSONArray team = plateauJeu.getJSONArray("playerBoards").getJSONObject(0).getJSONArray("fighters");
		JSONArray bot = plateauJeu.getJSONArray("playerBoards").getJSONObject(1).getJSONArray("fighters");
		System.out.println("team "+ team);
		System.out.println("bot "+bot);
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
						break;
			
						
//					case "VICTORY":
//						System.out.println("victoire");
//						break;
//						
//					case "DEFEAT":
//						System.out.println("defaite");
//						return;
//						
//					
//					case "DRAW":
//						System.out.println("draw");
//						return;
//					
//					case "CANCELLED":
//						System.out.println("annulé");
//						return;	
				}
				
				
		
			
				resp = client.partie(idPartie, idEquipe); // recupère le statu de la partie
				System.out.println(resp);
				
				nbTour++;
				
				
			}catch(InterruptedException e) {resp = "CANCELLED";}
			
		}while(!resp.equals("DEFEAT") && !resp.equals("VICTORY") && !resp.equals("DRAW"));
	}
	
	// apelle le serveur avec l'attaque et regarde la valeur de retour
	private void action()
	{
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
				//String a = client.move(idPartie, idEquipe, "A1,ATTACK,E1$A2,DEFEND,E1$A3,REST,A3");
				String a = client.move(idPartie, idEquipe, attack[i]);
		
				switch (a)
				{
				case "OK":
				
					break;
				
				case "FORBIDDEN":// defaite sur la partie
					resp = "DEFEAT";
					break;
					
				case "NOTYET":
					System.out.println("pas encore");
					break;
				
				case "GAMEOVER":
					System.out.println("partie fini");
				}	
			}			
	}
	
	

}
