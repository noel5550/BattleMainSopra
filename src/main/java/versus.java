import com.google.gson.JsonObject;
import com.mkyong.client.JCliGet;

public class versus {
	
	private String idEquipe;
	private String idPartie;
	private String idAdversaire;
	private String plateau;
	
	private String resp = "";
	private String attack = "";
	
	JCliGet client = new JCliGet();
	
	public versus()
	{
		nextGame();
	}
	
	
//initialise la partie
		private void nextGame()
		{
			this.idEquipe = client.connect(); // recupération de l'identifiant du client	
			this.idPartie = client.affrontementJoueur(this.idEquipe);
			this.plateau = client.plateau(this.idPartie); // creation du tableau
			do {
				jouerPartie(idPartie, idEquipe);
			}
			while(resp != "VICTORY" && resp != "DEFEAT" &&resp != "DRAW" && resp != "CANCELLED");
		}
	
//	"CANPLAY" si vous pouvez jouer 
//	"CANTPLAY" si vous ne pouvez pas encore jouer  pause de 500 milis (thread)
//	"VICTORY" si vous avez gagné la partie 
//	"DEFEAT" si vous avez perdu la partie 
//	"DRAW" si la partie s'est finie sur un match nul 
//	"CANCELLED" si la partie a été annulée 
	
	public void jouerPartie(String idPartie, String idEquipe) 
	{
		try {
			resp = client.partie(idPartie, idEquipe); // recupère le statu de la partie
			switch (resp)
			{
				case "CANPLAY":
					this.Attack("ORC");
					Thread.sleep(500);
					break;
					
				case "CANTPLAY":
						System.out.println("canttt");
						Thread.sleep(500);
						System.out.println("can't play");
					break;
					
				case "VICTORY":
					System.out.println("victoire");
					break;
					
				case "DEFEAT":
					System.out.println("defaite");
					break;
				
				case "DRAW":
					System.out.println("draw");
					break;
				
				case "CANCELLED":
					System.out.println("annulé");
					break;	
			}
		}catch(InterruptedException e) {e.printStackTrace();}
	}
	
	// apelle le serveur avec l'attaque et regarde la valeur de retour
	private void Attack(String attack)
	{
		
		attack =client.move(this.idPartie, this.idEquipe, attack);
		switch (attack)
		{
		case "OK":
			System.out.println("ok");
			break;
		
		case "FORBIDDEN":// defaite sur la partie
			System.out.println("interdit"); 
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
