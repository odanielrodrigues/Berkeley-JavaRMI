package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import util.ServerTime;
import util.ServerTimeImpl;

public class MainServer {

	public static void main(String[] args) {
		final int serverPort1 = 4500;

		try {
			ServerTime severTime1 = new ServerTimeImpl();
			Registry registry1 = LocateRegistry.createRegistry(serverPort1);
			registry1.rebind("ServerTimeImpl", severTime1);
			System.out.println("Server started");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}
}