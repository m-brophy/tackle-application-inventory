package io.tackle.applicationinventory.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockControlsServices implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(options().proxyVia("tackle-controls",8080));
        wireMockServer.start();

        wireMockServer.stubFor(get(urlPathEqualTo("/controls/tag"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "[\n" +
                                        "      {\n" +
                                        "        \"id\": 1,\n" +
                                        "        \"name\": \"RHEL 8\",\n" +
                                        "        \"tagType\": {\n" +
                                        "          \"id\": 1,\n" +
                                        "          \"name\": \"Operating System\"\n" +
                                        "        }\n" +
                                        "      }," +
                                        "      {\n" +
                                        "        \"id\": 2,\n" +
                                        "        \"name\": \"Oracle\",\n" +
                                        "        \"tagType\": {\n" +
                                        "          \"id\": 2,\n" +
                                        "          \"name\": \"Database\"\n" +
                                        "        }\n" +
                                        "      }," +
                                        "      {\n" +
                                        "        \"id\": 3,\n" +
                                        "        \"name\": \"Java EE\",\n" +
                                        "        \"tagType\": {\n" +
                                        "          \"id\": 3,\n" +
                                        "          \"name\": \"Language\"\n" +
                                        "        }\n" +
                                        "      }," +
                                        "      {\n" +
                                        "        \"id\": 4,\n" +
                                        "        \"name\": \"Tomcat\",\n" +
                                        "        \"tagType\": {\n" +
                                        "          \"id\": 4,\n" +
                                        "          \"name\": \"Runtime\"\n" +
                                        "        }\n" +
                                        "      }]"
                        )));

        wireMockServer.stubFor(get(urlPathEqualTo("/controls/business-service"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "[\n" +
                                        "      {\n" +
                                        "        \"id\": 1,\n" +
                                        "        \"name\": \"Food2Go\"\n" +
                                        "      }," +
                                        "      {\n" +
                                        "        \"id\": 2,\n" +
                                        "        \"name\": \"BS 1\"\n" +
                                        "      }]"
                        )));

        //stubFor(get(urlMatching(".*")).atPriority(10).willReturn(aResponse().proxiedFrom("https://restcountries.eu/rest")));

        HashMap<String, String> returnedMap =new HashMap<>();
        returnedMap.put("io.tackle.applicationinventory.services.TagService/mp-rest/uri", wireMockServer.baseUrl());
        returnedMap.put("io.tackle.applicationinventory.services.BusinessServiceService/mp-rest/uri", wireMockServer.baseUrl());
        return returnedMap;
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}


