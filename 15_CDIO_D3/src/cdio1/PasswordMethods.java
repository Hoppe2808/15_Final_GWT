package cdio1;

import java.util.ArrayList;
import java.util.Scanner;

public class PasswordMethods {

	private PasswordData AD = new PasswordData();
	private Operatoer o;

	private ArrayList<String> character = AD.getCharacter();
	private boolean smallLetters;
	private boolean kapLetters;
	private boolean numbers;
	private boolean symbols;
	private int different;

	public PasswordMethods(Operatoer o) {
		this.o = o;
	}
	
	/**
	 * Metode der tester hvorvidt en bruger nye password stemmer overens
	 * med de satte regler
	 * @param id Operatørens ID
	 * @param sc scanner objekt der sendes videre
	 * @return returnere koden, når den er gået igennem kontroltjekket
	 */
	public String checkPass(int id, Scanner sc){
		String password;
		do{
			password = sc.next();
			if(!this.checkPassLength(password)){
				System.out.print("Din adgangskode skal bestå af mellem 7-8 karakterer!");
			}
			if(!this.checkPass(password)){
				System.out.println("Din adgangskode skal indholde mindst 3 følgende: Tal, Specialtegn, Stort Bogstav, Lille Bogstav");
			}
		}while(!this.checkPass(password));
		return password;
	}
	
	/**
	 * @param password Indsæt adgangskoden som skal kontrolleres
	 * @return true hvis den er på 6 tegn eller derover
	 */
	public boolean checkPassLength(String password){
		if(password.length()<7&&password.length()>8){
			//			System.out.print("Din adgangskode skal bestå af mindst 6 karakterer!");
			return false;
		}else if(password.length()>=6){
			return true;
		}else{
			System.out.println("Fejl i kontrolKodeLaengde");
			return false;
		}
	}

	/**
	 * @param password Indsæt adgangskoden som skal kontrolleres
	 * @return true hvis den består...
	 */
	public boolean checkPass(String password){

		AD.array();

		numbers=false;
		symbols= false;
		smallLetters=false;
		kapLetters=false;
		different = 0;

		for(int j=0; j<=9; j++){
			if(password.contains(character.get(j))){
				numbers = true;
				different++;
				break;
			}
		}
		
		for(int j=10; j<=35; j++){
			if(password.contains(character.get(j))){
				smallLetters = true;
				different++;
				break;
			}
		}
		
		for(int j=36; j<=61; j++){
			if(password.contains(character.get(j))){
				kapLetters = true;
				different++;
				break;
			}
		}
		
		for(int j=62; j<=68; j++){
			if(password.contains(character.get(j))){
				symbols = true;
				different++;
				break;
			}
		}

		if(!numbers||!smallLetters||!kapLetters||!symbols){
			if(different<3){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param password1 Første kode
	 * @param password2 Anden kode
	 * @return true hvis koderne er ens METODEN ER TIL OPRETTELSE AF NY KODE
	 */
	public boolean samePass(String password1, String password2) {

		if(password1.equals(password2)) return true;
		else return false;
	}
	
	/**
	 * 
	 * @return true hvis koden stemmer med brugerens adgangskode
	 */
	public boolean correctUserPassword(int iD, String password){

		int index = -1;	
		for (int i = 0 ; i < o.getOperatoerArrayLaengde() ; i++){		
			if (iD == o.getOprId(i)){			
				index = i;			
			}		
		}

		if(o.getAdgangskode(index).equals(password)){
			return true;
		}else{
			return false;
		}
	}
	public String getNewPassword(int passwordLength){
		String password = AD.getNewKode(passwordLength);
		return password;
	}
}