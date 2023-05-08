package fr.ul.miage.sd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Outils pour HTTP
 *
 */
public class HTTPTools {
	// temps minimum d'une requête HTTP en ms (4 seconds)
	private int mt = 4000;
	
	// dernière requête HTTP
	private long last;

	/**
	 * Envoi une requête GET
	 * @param URL de la requête
	 * @return reponse
	 */
	public String sendGet(String url) {
		// vérifie le temps écoulé depuis la requête précédente
		while (System.currentTimeMillis() - last < mt);
		last = System.currentTimeMillis();
		
		try {
			// préparation de la requête
			StringBuilder result = new StringBuilder();
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF8");
			BufferedReader br = new BufferedReader(isr);
			
			// obtention de la réponse
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			
			// fermeture du lecteur et retour
			br.close();
			isr.close();
			return result.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}