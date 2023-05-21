package fr.ul.miage.sd.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ul.miage.sd.App;

/**
 * Outils pour HTTP
 *
 */
public class HTTPTools {
	// temps minimum d'une requête HTTP en ms (4 seconds)
	private static int mt = 4000;

	private static Logger logger = LoggerFactory.getLogger(HTTPTools.class);
	
	// dernière requête HTTP
	private static long last;

	private HTTPTools () {}

	/**
	 * Envoi une requête GET
	 * @param URL de la requête
	 * @return reponse
	 * @throws IOException
	 */
	public static String sendGet(String url, boolean timer, boolean auth) throws IOException {
		logger.info(String.format("Appel vers : %s", url));
		url = url.replace(" ", "%20");
        url += "&api_key=" + App.API_KEY;
		if (auth) {
			if (Objects.nonNull(App.getSignature())) {
				url += String.format("&api_sig=%s", App.getSignature());
			}
			if(Objects.nonNull(App.getSession())) {
				url += String.format("sk=%s", App.getSession().getKey());
			}
		}
		url += "&format=json";

		// vérifie le temps écoulé depuis la requête précédente
		while (timer && (System.currentTimeMillis() - last < mt));
		last = System.currentTimeMillis();
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		try {
			// préparation de la requête
			StringBuilder result = new StringBuilder();
			conn.setRequestMethod("GET");
			conn.addRequestProperty("Accept", "application/json");
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
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
		} catch (IOException e) {
			if (conn.getResponseCode() == 403) {
				return null;
			}else {
				e.printStackTrace();
			}
		}
		return null;
	}
}