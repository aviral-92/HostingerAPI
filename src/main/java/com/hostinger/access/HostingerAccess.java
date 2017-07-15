package com.hostinger.access;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hostinger.entity.Details;

public interface HostingerAccess {

	public HtmlPage loginToHostingerPage(Details details)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException;

	public HtmlPage afterLogin(Details details) throws InterruptedException, IOException;

	public String addIpToDb(Details details)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException;

}
