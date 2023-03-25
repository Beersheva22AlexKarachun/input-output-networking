package telran.net;

import java.net.*;
import static telran.net.UdpUtills.*;

public class UdpServer extends AbstractServer {

	public UdpServer(Protocol protocol, int port) throws Exception {
		super(protocol, port, new DatagramSocket(port));
	}

	@Override
	public void run() {
		System.out.println("UDP server running on port " + port);
		try {
			byte[] bufferReceive = new byte[MAX_BUFFER_LENGTH];
			byte[] bufferSend = null;
			DatagramSocket DSsocket = (DatagramSocket) socket;
			while (true) {
				DatagramPacket packetReceive = new DatagramPacket(bufferReceive, MAX_BUFFER_LENGTH);
				DSsocket.receive(packetReceive);
				Request request = (Request) toSerializable(packetReceive.getData(), packetReceive.getLength());
				Response response = protocol.getResponse(request);
				bufferSend = toBytesArray(response);
				DatagramPacket packetSend = new DatagramPacket(bufferSend, bufferSend.length,
						packetReceive.getAddress(), packetReceive.getPort());
				DSsocket.send(packetSend);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}

	}

}