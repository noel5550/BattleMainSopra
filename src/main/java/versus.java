import com.mkyong.client.*;



import javassist.bytecode.Opcode;

import java.util.List;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class versus {

	private String idEquipe;
	private String idPartie;
	private String idAdversaire;
	private String plateau;
	private JSONObject plateauJeu;

	private String resp = "";
	private String attack = "";
	private int nbTour = 1;
	private int index = 0;

	String[] team = new String[3];

	List<Fighter> equipe = new ArrayList<Fighter>();
	List<Fighter> oponent = new ArrayList<Fighter>();
	String currentOp;

	int i = 0;

	static JCliGet client = new JCliGet();

	Board board;

	public versus()
	{	
		team[0] = "GUARD";
		team[1] ="ORC";
		team[2] = "PRIEST";
		nextGame();

	}


//initialise la partie

	private void nextGame()
	{
		this.idEquipe = client.connect(); // recupération de l'identifiant du client	
		this.idPartie = client.affrontementJoueur(this.idEquipe);
		board = client.plateau(idPartie, idEquipe);
		jouerPartie(idPartie, idEquipe);
	}
	
	private String Play(Fighter f , Fighter op , String spe)
	{
		String a ="";
		int pa = f.getCurrentMana();
		
		
		if (pa == 0)
		{
			a = "A"+f.getOrderNumberInTeam()+",REST,"+"A"+op.getOrderNumberInTeam();
		}
			
		else if (pa == 1)
		{
			a = "A"+f.getOrderNumberInTeam()+",ATTACK,"+"E"+op.getOrderNumberInTeam();
		}
			
		else 
		{
			if (spe.equals("HEAL") || spe.equals("PROTECT"))
				a = "A"+f.getOrderNumberInTeam()+","+spe+",A"+f.getOrderNumberInTeam();
			else
				a = "A"+f.getOrderNumberInTeam()+","+spe+",E"+f.getOrderNumberInTeam();
		}
				
		return a ;
	}

	public void jouerPartie(String idPartie, String idEquipe) 

	{
		resp = client.partie(idPartie, idEquipe); // recupère le statu de la partie
		do {
			try {
				switch (resp)
				{
					case "CANPLAY":

						board = client.plateau(idPartie, idEquipe);			   

						this.action();

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

						break;
					case "CANTPLAY":
							Thread.sleep(500);
						break;
//					case "VICTORY":System.out.println("victoire");break;
//					case "DEFEAT":System.out.println("defaite");return;
//					case "DRAW":System.out.println("draw");return;
//					case "CANCELLED":System.out.println("annulé");return;	
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
			    				 equipe.add(fight);
			    			 }	 
			    			 else 
			    			 {
			    				 if (fight.getFighterClass().equals("PRIEST") && !fight.getIsDead()) 
			    				 {
			    					if (!oponent.isEmpty())
			    					{
			    						Fighter tmp;
			    						tmp = oponent.get(0);
				    					oponent.set(0, fight);
				    					oponent.add(tmp);
			    					}
			    					else oponent.add(fight);
			    				 }
			    				 else  if(!fight.getIsDead())
			    					 oponent.add(fight);
			    			 }
						}     
			    }
			    currentOp ="E"+oponent.get(0).getOrderNumberInTeam();	 
			    
			    System.out.println("aaaaaaaaaaaaaaaaaa "+currentOp);
//			String a1 = Play(equipe.get(0),oponent.get(0),"PROTECT");
//			String a2 = Play(equipe.get(1),oponent.get(0),"YELL");
//			String a3 = Play(equipe.get(2),oponent.get(0),"HEAL");
//			    
//			 String a = "A1,ATTACK,"+currentOp
//			    		+"$"+a2
//			    		+"$"+a3;
//			  
//			  attack = client.move(idPartie, idEquipe, a);
		
			    if (nbTour%2 == 0) {
			    	attack = client.move(idPartie, idEquipe,"A1,ATTACK,"+currentOp
															+ "$A2,ATTACK,"+currentOp
															+ "$A3,HEAL,A3"
							);
			    }
			    else 
			    {
			    	attack = client.move(idPartie, idEquipe,"A1,PROTECT,A3"
							+ "$A2,ATTACK,"+currentOp
							+ "$A3,REST,A3"
							);
			    }
			    
				switch (attack)
				{
				case "OK":
					try {
					}catch(ArrayIndexOutOfBoundsException e) 
					{
						e.printStackTrace();
					}
					break;
//				case "FORBIDDEN":resp = "DEFEAT";break;			

//				case "NOTYET":System.out.println("pas encore");break;

//				case "GAMEOVER":System.out.println("partie fini");

				}	
				oponent.clear(); // on vide la liste pour la reremplir au tour d'après

			}			
	}
	
	

	

}