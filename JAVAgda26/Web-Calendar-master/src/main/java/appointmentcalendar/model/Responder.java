package appointmentcalendar.model;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import appointmentcalendar.model.dto.Dto;

/**
 * Responder. Packages a response and sends it to the client
 */
public class Responder {

	/**
	 * Respond with a json string
	 * 
	 * @param json
	 * @param response
	 */
	public static void send(String json, HttpServletResponse response) {
		sendResponse(json, response);
	}

	/**
	 * Respond with a Data Transfer Object
	 * 
	 * @param dto
	 * @param response
	 */
	public static void send(Dto dto, HttpServletResponse response) {
		Gson gson = new Gson();
		String json = gson.toJson(dto);

		sendResponse(json, response);
	}

	/**
	 * Respond with a list
	 * 
	 * @param list
	 * @param response
	 */
	public static void send(List<String> list, HttpServletResponse response) {
		sendResponse(list, response);
	}

	/*
	 * Package object as a json and respond
	 */
	private static void sendResponse(Object object, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			new Gson().toJson(object, response.getWriter());
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

}
