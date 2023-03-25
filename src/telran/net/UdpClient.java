package telran.net;

import java.io.*;
import static telran.net.UdpUtills.*;
import java.net.*;

public class UdpClient extends AbstractClient {

	public UdpClient(String host, int port) throws IOException {
		super(host, port, new DatagramSocket());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T send(String type, Serializable requestData) {
		Request request = new Request(type, requestData);
		DatagramSocket DSsocket = (DatagramSocket) socket;
		try {
			byte[] bufferSend = toBytesArray(request);
			byte[] bufferReceive = new byte[MAX_BUFFER_LENGTH];
			DatagramPacket packetSend = new DatagramPacket(bufferSend, bufferSend.length, InetAddress.getByName(host),
					port);
			DatagramPacket packetReceive = new DatagramPacket(bufferReceive, MAX_BUFFER_LENGTH);

			DSsocket.send(packetSend);
			DSsocket.receive(packetReceive);

			Response response = (Response) toSerializable(packetReceive.getData(), packetReceive.getLength());

			if (response.code != ResponseCode.OK) {
				throw new Exception(response.data.toString());
			}
			return (T) response.data;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}