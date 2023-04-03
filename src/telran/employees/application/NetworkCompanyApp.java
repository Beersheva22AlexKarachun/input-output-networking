package telran.employees.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import telran.employees.*;
import telran.net.NetworkClient;
import telran.view.*;

public class NetworkCompanyApp {
	private static final String CLIENTS_PACKAGE = "telran.net.";
	private static final String[] REQUESTED_DATA = { "hostname", "transport", "port", "departments" };

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Must be at least 1 argument");

		} else if (Files.exists(Path.of(args[0]))) {
			Properties config = new Properties();
			config.load(new FileInputStream(args[0]));
			checkConfig(config);

			NetworkClient client = getNetworkClient(config.getProperty("transport"), config.getProperty("hostname"),
					config.getProperty("port"));

			Company company = new NetworkCompany(client);

			CompanyControllerItems controller = new CompanyControllerItems(company,
					new HashSet<String>(getDepartmentsSet(config.getProperty("departments"))));

			Menu menu = new Menu("Company app", controller.getAdminMenu(), controller.getUserMenu(),
					Item.of("Exit", io -> {
						try {
							client.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						io.writeLine("Connection has been closed.");
					}, true));

			menu.perform(new StandardInputOutput());
		} else {
			System.out.println("Config file doesn't exist.");
		}
	}

	private static Collection<? extends String> getDepartmentsSet(String departments) {
		return Arrays.asList(departments.trim().split("\\s*,\\s*"));
	}

	private static void checkConfig(Properties config) {
		if (Arrays.stream(REQUESTED_DATA).anyMatch(data -> !config.containsKey(data))) {
			throw new IllegalArgumentException("Config file doesn't contain necessary data");
		}
	}

	@SuppressWarnings("unchecked")
	private static NetworkClient getNetworkClient(String transport, String hostname, String port) throws Exception {
		transport = transport.substring(0, 1).toUpperCase() + transport.substring(1, transport.length()).toLowerCase();
		Class<NetworkClient> clientClazz = (Class<NetworkClient>) Class.forName(CLIENTS_PACKAGE + transport + "Client");
		Constructor<NetworkClient> constructor = clientClazz.getConstructor(String.class, int.class);
		return constructor.newInstance(hostname, Integer.parseInt(port));
	}
}
