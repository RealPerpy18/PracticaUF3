package perpinya_arnau;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

/**
 * Connexió HTTP.
 * Llegeix una petició HTTP, la processa i s'encarrega
 * d'enviar resposta.
 * Sols processa una petició, després d'enviar la resposta tanca la connexió.
 * 
 * @author pererdg
 *
 */
public class HTTPConnection implements Runnable {

	private Socket sck;
	private BufferedReader in;
	private HTTPResponse response;
	
	
	/**
	 * Constructor
	 * 
	 * @param sck Socket de la connexió HTTP
	 * @throws Exception
	 */
	public HTTPConnection(Socket sck) throws Exception {
		sck=this.sck;
	}
	
	/**
	 * Punt d'entrada del fil.
	 * Processa la petició i després tanca recursos.
	 */
	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.sendInternalServerError();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				// Alliberar recursos
				in.close();
				sck.close();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}
	
	/**
	 * Processa una petició.
	 * Llegeix del socket la petició i construeix un HTTPRequest.
	 * El camp body del HTTPRequest sols es carrega si Content-Type: application/x-www-form-urlencoded.
	 * Crea objecte HTTPRequest i demana enviar resposta. 
	 * 
	 * @throws Exception
	 */
	private void processRequest() throws Exception {
		//response.sendResponse(request);
	}
	
	/**
	 * Llegeix el body de la petició.
	 * Aquest mètode carrega el body en un String, per tant no és apte
	 * per a dades que no siguin de tipus text, com per exemple un fitxer.
	 * Es pot utilitzar per Content-Type: application/x-www-form-urlencoded.
	 * 
	 * @param length Longitud del body (Content-Length)
	 * @return Body
	 * @throws Exception
	 */
	private String readBody(int length) throws Exception {
		in=new BufferedReader(new InputStreamReader(sck.getInputStream()));
		String body=in.readLine();
		debug(body);
		return body;
	}
	
	/**
	 * Llegeix del socket una línia.
	 * Fa debug i mostra el que ha llegit per consola.
	 * 
	 * @return
	 * @throws Exception
	 */
	/*private String readLine() throws Exception {
		if (line != null) debug(line);
		return line;
	}*/
	
	/**
	 * Mostra per consola un text.
	 * 
	 * @param txt Text a mostrar
	 */
	private void debug(String txt) {
		System.out.println(sck.getRemoteSocketAddress() + " >> " + txt);
	}
}
