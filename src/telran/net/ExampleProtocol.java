package telran.net;

import java.io.Serializable;

public class ExampleProtocol implements Protocol {

	@Override
	public Response getResponse(Request request) {
		return switch (request.type) {
		case "reverse" -> reverse(request.data);
		case "length" -> length(request.data);
		default -> new Response(ResponseCode.WRONG_REQUEST, request.type + " wrong request");
		};
	}

	private Response length(Serializable data) {
		return new Response(ResponseCode.OK, data.toString().length());
	}

	Response reverse(Serializable data) {
		Serializable responseData = new StringBuilder(data.toString()).reverse().toString();
		return new Response(ResponseCode.OK, responseData);
	}
}