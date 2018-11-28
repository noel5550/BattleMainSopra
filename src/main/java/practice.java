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
	private int ia;
	private boolean ilYaPriest;
	private String resp = "";
	private String attack = "";
	private int nbTour = 1;
	private int index = 0;

	private boolean yell = false;

	String[] team = new String[3];

	List<Fighter> equipe = new ArrayList<Fighter>();
	List<Fighter> oponent = new ArrayList<Fighter>();
	String currentOp;

	int i = 0;


	static JCliGet client = new JCliGet();

	Board board;

	public practice()

	{	

		team[0] = "PALADIN";
		team[1] ="ORC";
		team[2] = "ARCHER";

		newGame();
	}


	//initialise la partie

	private void newGame()

	{
		ia = 21;
//		this.idEquipe = client.connect(); // recupération de l'identifiant du client	
//		this.idPartie = client.affrontementBot(ia, this.idEquipe); // creation de la nouvelle partie contre l'ia
//		
//		board = client.plateau(idPartie, idEquipe);
//		jouerPartie(idPartie, idEquipe);
		
		this.idEquipe = client.connect(); // recupération de l'identifiant du client	
		this.idPartie = client.affrontementJoueur(this.idEquipe);
		board = client.plateau(idPartie, idEquipe);
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
					//						for(Fighter f : equipe) {
					//							if(f != null) {
					//								f.SetMana(f.getMana()+1);
					//								
					//							}
					//						}
					board = client.plateau(idPartie, idEquipe);			   

					this.action(ia);

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
					nbTour++;
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
				
				
			}catch(InterruptedException e) {resp = "CANCELLED";}
		}while(!resp.equals("DEFEAT") && !resp.equals("VICTORY") && !resp.equals("DRAW"));
	}
	// apelle le serveur avec l'attaque et regarde la valeur de retour

	private void action(int ia)
	{
//		for(Fighter f : equipe) {
//		if(f != null) {
//			f.SetMana(f.getMana()+1);
//			
//		}
//		}
		
		if(ia != 6) {
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
							
							//			    				 System.out.println("+++++++++++++++"+fight.getFighterClass());
						}	 
						else 
						{
							if (fight.getFighterClass().equals("PRIEST") && !fight.getIsDead()) 
							{
								ilYaPriest = true;
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

				// a affiner
				Fighter cibleF =null;
				for(Fighter f : oponent) {
					if(ilYaPriest) {
						if(f.getFighterClass().equals("ARCHER") && !f.getIsDead()) {
							cibleF = f;
							break;
						}
						
						else if(f.getFighterClass().equals("CHAMAN")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}
						
						else if(f.getFighterClass().equals("PALADIN")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}

						else if (f.getFighterClass().equals("PRIEST")&& !f.getIsDead()) {
							cibleF = f;
						}
						else cibleF = oponent.get(0);
					}else {
						if(f.getFighterClass().equals("ARCHER")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}
						else if(f.getFighterClass().equals("ORC")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}
						
						else if (f.getFighterClass().equals("PALADIN")&& !f.getIsDead()) {
							cibleF = f;
						}else {
							cibleF = oponent.get(0);
						}
					}
				}
				currentOp ="E"+cibleF.getOrderNumberInTeam();	

				String a1 = attack(equipe.get(0), cibleF , "CHARGE");  
				String a2 = attack(equipe.get(1), cibleF , "YELL");
				String a3 = attack(equipe.get(2), cibleF , "FIREBOLT");

				String a = "$"+a1+"$"+a2+"$"+a3;


				attack = client.move(idPartie, idEquipe, a);

				switch (attack)
				{
				case "OK":
					try {
					}catch(ArrayIndexOutOfBoundsException e) 
					{
						e.printStackTrace();
					}
					break;
				}	
				oponent.clear(); // on vide la liste pour la reremplir au tour d'après

			}

			// tactique normal (en bas)
		}else {
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
							
							//			    				 System.out.println("+++++++++++++++"+fight.getFighterClass());
						}	 
						else 
						{
							if (fight.getFighterClass().equals("PRIEST") && !fight.getIsDead()) 
							{
								ilYaPriest = true;
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

				// a affiner
				Fighter cibleF =null;
				for(Fighter f : oponent) {
					
					if(ilYaPriest) {
						if(f.getFighterClass().equals("ARCHER") && !f.getIsDead()) {
							cibleF = f;
							break;
						}
						
						else if(f.getFighterClass().equals("CHAMAN")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}
						
						else if(f.getFighterClass().equals("PALADIN")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}

						else if (f.getFighterClass().equals("PRIEST")&& !f.getIsDead()) {
							cibleF = f;
						}
						else cibleF = oponent.get(0);
					}else {
						if(f.getFighterClass().equals("ARCHER")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}
						else if(f.getFighterClass().equals("ORC")&& !f.getIsDead()) {
							cibleF = f;
							break;
						}
						
						else if (f.getFighterClass().equals("PALADIN")&& !f.getIsDead()) {
							cibleF = f;
						}else {
							cibleF = oponent.get(0);
						}
					}
				}
				

				
				currentOp ="E"+cibleF.getOrderNumberInTeam();	

				String a1 = attack(equipe.get(0), cibleF , "CHARGE");  
				String a2 = attack(equipe.get(1), cibleF , "YELL");
				String a3 = attack(equipe.get(2), cibleF , "FIREBOLT");

				if(nbTour == 3 || nbTour == 6 ) {
					a3 = "$A3,DEFEND,A3";
				}
				String a = "$"+a1+"$"+a2+"$"+a3;


				attack = client.move(idPartie, idEquipe, a);

				switch (attack)
				{
				case "OK":
					try {
					}catch(ArrayIndexOutOfBoundsException e) 
					{
						e.printStackTrace();
					}
					break;
				}	
				oponent.clear(); // on vide la liste pour la reremplir au tour d'après

			}
		}

	}


	private String attack(Fighter f , Fighter op , String spe)
	{
		
		String a ="";
		int pa = f.getMana();	
		
		if ((nbTour%2 == 0 && pa == 2) || (f.getFighterClass().equals("ORC")&&nbTour%2 == 0 && pa == 2) ) 
		{
			a = "A"+f.getOrderNumberInTeam()+",REST,"+"A"+op.getOrderNumberInTeam();
			f.SetMana(f.getMana() +1);
		}
		else {
			System.out.println("cunrett pas "+pa);
			if (pa == 0)
			{
				a = "A"+f.getOrderNumberInTeam()+",REST,"+"A"+op.getOrderNumberInTeam();
				f.SetMana(f.getMana() +1);
			}
			else if (pa == 1)
			{	
				f.SetMana(f.getMana() -1);
				a = "A"+f.getOrderNumberInTeam()+",ATTACK,"+"E"+op.getOrderNumberInTeam();
			}	
			else 
			{
				//			if (f.getFighterClass().equals("ORC"))
				//			{
				if(f.getFighterClass().equals("PALADIN")) {
					f.SetMana(f.getMana() -1);
					
				}
								f.SetMana(f.getMana() -2);
					a = "A"+f.getOrderNumberInTeam()+","+spe+",E"+op.getOrderNumberInTeam();
			
				
				//			}

			}
		}

		
		f.SetMana(f.getMana()+1);
		return a ;
	}

	// soin + protect
	private String soinProt(Fighter f, Fighter c , String spe )
	{
		String a = "";
		int pa  = f.getMana();	
		if (!f.getFighterClass().equals("ORC"))
		{
			if (pa > 2)
			{
				a = "A"+f.getOrderNumberInTeam()+","+spe+",A"+c.getOrderNumberInTeam();
			}

			else
			{
				a = "A"+f.getOrderNumberInTeam()+",REST,"+"A"+f.getOrderNumberInTeam();
				f.SetMana(f.getMana() +1);
			}
		}
		return a;
	}






}