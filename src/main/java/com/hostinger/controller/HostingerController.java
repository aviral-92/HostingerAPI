package com.hostinger.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.hostinger.access.HostingerAccess;
import com.hostinger.entity.Details;
import com.hostinger.entity.Response;

@RestController
public class HostingerController {

	@Autowired
	private HostingerAccess hostingerAccess;

	@RequestMapping(value = "/addIP", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response addIP(@RequestBody Details details) {
		try {
			return new Response(hostingerAccess.addIpToDb(details));
		} catch (FailingHttpStatusCodeException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return new Response("Please check your credentials");
	}
}
