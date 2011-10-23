package com.tomakehurst.wiremock.client;

import com.tomakehurst.wiremock.http.RequestMethod;
import com.tomakehurst.wiremock.mapping.JsonMappingBinder;
import com.tomakehurst.wiremock.mapping.RequestResponseMapping;

public class WireMock {
	
	private static final int DEFAULT_PORT = 8080;
	private static final String DEFAULT_HOST = "localhost";

	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;
	private AdminClient adminClient;
	
	private static WireMock defaultInstance = new WireMock();
	
	public WireMock(String host, int port) {
		this.host = host;
		this.port = port;
		adminClient = new HttpAdminClient(host, port);
	}
	
	public WireMock() {
		adminClient = new HttpAdminClient(host, port);
	}
	
	void setAdminClient(AdminClient adminClient) {
		this.adminClient = adminClient;
	}
	
	public static void givenThat(MappingBuilder mappingBuilder) {
		defaultInstance.register(mappingBuilder);
	}
	
	public static void configureFor(String host, int port) {
		defaultInstance = new WireMock(host, port);
	}
	
	public static void resetHostAndPort() {
		defaultInstance = new WireMock();
	}
	
	public void register(MappingBuilder mappingBuilder) {
		RequestResponseMapping mapping = mappingBuilder.build();
		String json = JsonMappingBinder.buildJsonStringFor(mapping);
		adminClient.addResponse(json);
	}
	
	public static UrlMatchingStrategy urlEqualTo(String url) {
		UrlMatchingStrategy urlStrategy = new UrlMatchingStrategy();
		urlStrategy.setUrl(url);
		return urlStrategy;
	}
	
	public static UrlMatchingStrategy urlMatching(String url) {
		UrlMatchingStrategy urlStrategy = new UrlMatchingStrategy();
		urlStrategy.setUrlPattern(url);
		return urlStrategy;
	}
	
	public static MappingBuilder get(UrlMatchingStrategy urlMatchingStrategy) {
		return new MappingBuilder(RequestMethod.GET, urlMatchingStrategy);
	}
	
	public static MappingBuilder post(UrlMatchingStrategy urlMatchingStrategy) {
		return new MappingBuilder(RequestMethod.POST, urlMatchingStrategy);
	}
	
	public static MappingBuilder put(UrlMatchingStrategy urlMatchingStrategy) {
		return new MappingBuilder(RequestMethod.PUT, urlMatchingStrategy);
	}
	
	public static MappingBuilder delete(UrlMatchingStrategy urlMatchingStrategy) {
		return new MappingBuilder(RequestMethod.DELETE, urlMatchingStrategy);
	}
	
	public static MappingBuilder head(UrlMatchingStrategy urlMatchingStrategy) {
		return new MappingBuilder(RequestMethod.HEAD, urlMatchingStrategy);
	}
	
	public static MappingBuilder options(UrlMatchingStrategy urlMatchingStrategy) {
		return new MappingBuilder(RequestMethod.OPTIONS, urlMatchingStrategy);
	}
	
	public static MappingBuilder trace(UrlMatchingStrategy urlMatchingStrategy) {
		return new MappingBuilder(RequestMethod.TRACE, urlMatchingStrategy);
	}
	
	public static ResponseDefinitionBuilder aResponse() {
		return new ResponseDefinitionBuilder();
	}
	
	
}