import com.mkyong.client.*;

import javassist.bytecode.Opcode;

import java.util.List;

import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;





public class practice {

	private String idEquipe;
	private String idPartie;
	private String plateau;
	private JSONObject plateauJeu;
	
	private String resp = "";
	private String attack = "";
	private int nbTour = 1;
	private int index = 0;
	
	
	String[] team = new String[3];
//	Fighter[] oponent = new Fighter[6]; // revoir la taille du tableau
	List<Fighter> oponent = new ArrayList<Fighter>();
	
	String currentOp = "E1";
	
	int i = 0;
	 
	static JCliGet client = new JCliGet();

	Board board;
	
	public practice()
	{	
		team[0] = "GUARD";
		team[1] ="ORC";
		team[2] = "PRIEST";
		newGame();
	}
	
	
//initialise la partie
	private void newGame()
	{
		this.idEquipe = client.connect(); // recupération de l'identifiant du client	
		this.idPartie = client.affrontementBot(5, this.idEquipe); // creation de la nouvelle partie contre l'ia
		board = client.plateau(idPartie, idEquipe);
		jouerPartie(idPartie, idEquipe);
		}
	
	public void jouerPartie(String idPartie, String idEquipe) 
	{
	//JSONArray bot = plateauJeu.getJSONArray("playerBoards").getJSONObject(1).getJSONArray("fighters");
		
	//	System.out.println("bot "+bot);
		resp = client.partie(idPartie, idEquipe); // recupère le statu de la partie
		
		do {
			try {
				switch (resp)
				{
					case "CANPLAY":
						board = client.plateau(idPartie, idEquipe);

						   if (nbTour >3 && board != null) {
							   for(PlayerBoard playerBoards : board.getPlayerBoards()) {
								   System.out.println();
								   System.out.println("nom de l'équipe: "+playerBoards.getPlayerName());
								   System.out.println();
								   
								   for(Fighter fight : playerBoards.getFighters()) {
									   System.out.println(fight.getFighterClass()+" est mort :"+fight.getIsDead()+"/ point de vie "+fight.getCurrentLife()+" / point d'action: "+fight.getCurrentMana());
								   }     
							   }
						   }
						this.action();
						
						System.out.println("enemie  actuel " + currentOp);
						break;
						
					case "CANTPLAY":
							Thread.sleep(500);
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
				case 1: client.move(this.idPartie, this.idEquipe, team[0]);break;
				case 2:client.move(this.idPartie, this.idEquipe, team[1]);break;
				case 3: client.move(this.idPartie, this.idEquipe, team[2]);break;
				}
			}
			
			
			else 
			{
			    board = client.plateau(idPartie);
			    int j=0;
			    int n=0;
			    
			    for(PlayerBoard playerBoards : board.getPlayerBoards()) 
			    {
			    	for(Fighter fight : playerBoards.getFighters()) 
			    		{  
			    			 if (playerBoards.getPlayerName().equals("NoelEtSesAmisInferieurs"))
					    	 {
								System.out.println("aaaaaaaaaaaaaaaa");
							 }	  
			    			 else 
			    			 {
			    				 oponent.add(fight);
			    			 }
						}     
			    }
			    	
			    if (nbTour%2 == 0) {
			    
			    	attack = client.move(idPartie, idEquipe,"A1,PROTECT,A2"
							+ "$A2,ATTACK,"+currentOp
							+ "$A3,HEAL,A2"
							);
			    }
			    else 
			    {
			    	attack = client.move(idPartie, idEquipe,"A1,ATTACK,"+currentOp
							+ "$A2,ATTACK,"+currentOp
							+ "$A3,REST,A3"
							);
			    }

				switch (attack)
				{
				case "OK":
					try {
						if (oponent.get(0).getIsDead()) 
							{	
							System.out.println("enemie 1 mort");
								currentOp = "E2";
							}
						if (oponent.get(1).getIsDead()) 
							{
							System.out.println("enemie 2 mort");
								currentOp ="E3";
							}
						
					}catch(ArrayIndexOutOfBoundsException e) 
					{
						e.printStackTrace();
					}
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
				
				oponent.clear(); // on vide la liste pour la reremplir au tour d'après
			}			
	}
	
}

