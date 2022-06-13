              package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Connection;
import util.ServerTime;
import util.Time;

public class MainClient {

	public static void main(String[] args) {
		try {

			final Time time = new Time();

			// Lista de conexão 
			final List<Connection> connections = new ArrayList<>();
			connections.add(new Connection("192.168.1.116", 4500));
		

			// Lista de tempo e data 
			final Map<Connection, ServerTime> servers = new HashMap<>();

			for (Connection c : connections) {
				Registry registry = LocateRegistry.getRegistry(c.getAddress(), c.getPort());
				ServerTime st = (ServerTime) registry.lookup("ServerTimeImpl");
				System.out.println(String.format("Server time %s: %s", c.getAddress(), st.getTime().getTime()));
				servers.put(c, st);
			}

			System.out.println("Client time: " + time);

			// Algoritmo de Berkeley
			long coordinatorSeconds = time.getEpochSeconds();
			long diffServer = 0;
			long sum = 0;
			long timeServer = 0;
			for (Map.Entry<Connection, ServerTime> entry : servers.entrySet()) {
				timeServer = (entry.getValue().getTime().getEpochSeconds());
				diffServer = timeServer - coordinatorSeconds;
				sum += diffServer;
			}
			long average = sum / (servers.size() + 1);

			long resultSeconds = (average + (-1 * diffServer) + timeServer);

			LocalDateTime resultDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(resultSeconds),
					ZoneOffset.UTC);
			System.out.println("Result Berkeley: " + resultDateTime);

			// Set novas Datas e Tempo 
			for (Map.Entry<Connection, ServerTime> entry : servers.entrySet()) {
				entry.getValue().setTime(resultSeconds);
				System.out.println(String.format("Server time %s: %s ", entry.getKey().getAddress(),
						entry.getValue().getTime().getTime()));
			}
			time.setTime(resultDateTime);
			System.out.println("Client time: " + time);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}