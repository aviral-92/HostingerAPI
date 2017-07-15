package com.hostinger.access.impl;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.hostinger.access.HostingerAccess;
import com.hostinger.entity.Details;

@Service
public class HostingerAccessImpl implements HostingerAccess {

	static Logger log = Logger.getLogger(HostingerAccessImpl.class);
	private WebClient webClient = new WebClient(BrowserVersion.CHROME);
	private HtmlButton submitBtn;
	private HtmlPage htmlPage;

	@Override
	public HtmlPage loginToHostingerPage(Details details)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		log.debug("Message");
		htmlPage = webClient.getPage("https://cpanel.hostinger.in/auth");
		HtmlInput username = (HtmlInput) htmlPage.getElementByName("email");
		HtmlInput password = (HtmlInput) htmlPage.getElementByName("password");
		username.setValueAttribute(details.getEmail());
		password.setValueAttribute(details.getPassword());
		submitBtn = (HtmlButton) htmlPage.getElementsByTagName("button").get(1);
		htmlPage = submitBtn.click();
		log.info(htmlPage);
		return htmlPage;
	}

	public HtmlPage afterLogin(Details details) throws InterruptedException, IOException {

		htmlPage = loginToHostingerPage(details);
		Thread.sleep(3000);
		HtmlAnchor htmlAnchor = htmlPage.getAnchorByHref("/databases/remote-mysql/aid/25664857");
		Thread.sleep(3000);
		log.info(htmlAnchor);
		htmlPage = htmlAnchor.click();
		return htmlPage;
	}

	// By this method one can simply add IP
	public String addIpToDb(Details details)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {

		htmlPage = afterLogin(details);
		Thread.sleep(1500);
		HtmlInput db_host = (HtmlInput) htmlPage.getElementByName("db_host");
		HtmlSelect htmlSelect = (HtmlSelect) htmlPage.getElementById("db_name");
		HtmlOption htmlOption = htmlSelect.getOption(details.getSelectOption());
		htmlOption.setSelected(true);
		db_host.setValueAttribute(details.getIp());
		submitBtn = (HtmlButton) htmlPage.getElementsByTagName("button").get(7);
		htmlPage = submitBtn.click();
		return "Successfully added";
	}
}
