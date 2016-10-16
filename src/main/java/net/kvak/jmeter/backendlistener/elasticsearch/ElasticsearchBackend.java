package net.kvak.jmeter.backendlistener.elasticsearch;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.visualizers.backend.AbstractBackendListenerClient;
import org.apache.jmeter.visualizers.backend.BackendListenerContext;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * TODO: Add javadocs..
 * 
 * @author korteke
 *
 */
public class ElasticsearchBackend extends AbstractBackendListenerClient {

	private static final String ES_PROTOCOL = "elasticsearch.protocol";
	private static final String ES_HOST = "elasticsearch.host";
	private static final String ES_PORT = "elasticsearch.port";
	private static final String ES_INDEX = "elasticsearch.index";
	private static final String ES_TYPE = "elasticsearch.type";
	private static final String ES_TIMESTAMP = "elasticsearch.timestamp";

	/**
	 * Initialize logger
	 */
	private static final Logger LOGGER = LoggingManager.getLoggerForClass();

	/**
	 * Lock for syncro
	 */
	private static final Object STATIC_LOCK = new Object();

	/**
	 * Placeholder for ES connection status
	 */
	private boolean isESAlive = false;

	/**
	 * Mediatype JSON
	 */
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	@Override
	public Arguments getDefaultParameters() {
		Arguments parameters = new Arguments();
		parameters.addArgument(ES_PROTOCOL, "http");
		parameters.addArgument(ES_HOST, null);
		parameters.addArgument(ES_PORT, "9200");
		parameters.addArgument(ES_INDEX, "jmeter");
		parameters.addArgument(ES_TYPE, null);
		parameters.addArgument(ES_TIMESTAMP, "yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		return parameters;
	}

	@Override
	public void setupTest(BackendListenerContext ctx) throws Exception {
		OkHttpClient client = new OkHttpClient();

		String esEndpoint = ctx.getParameter(ES_PROTOCOL) + "://" + ctx.getParameter(ES_HOST) + ":"
				+ ctx.getParameter(ES_PORT);

		Request request = new Request.Builder().url(esEndpoint).build();

		Response response = null;

		try {
			response = client.newCall(request).execute();
			boolean resu = response.isSuccessful();

			isESAlive = resu ? true : false;

			if (isESAlive) {
				LOGGER.info("Elasticsearch is UP!");
			} else {
				LOGGER.info("Elasticsearch is DOWN!");
			}

		} catch (IOException e) {
			LOGGER.error("Error with the Elasticsearch connection.");
		}

	}

	@Override
	public void teardownTest(BackendListenerContext context) throws Exception {
		// Not implemented
	}

	@Override
	public void handleSampleResults(List<SampleResult> sampleResults, BackendListenerContext ctx) {

		if (!isESAlive) {
			LOGGER.info("Elasticsearch is DOWN. Quitting");
			return;
		}

		synchronized (STATIC_LOCK) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			OkHttpClient client = new OkHttpClient();
			SimpleDateFormat sdf = new SimpleDateFormat(ctx.getParameter(ES_TIMESTAMP));

			for (SampleResult sampleResult : sampleResults) {
				ElasticSampleResult esResu = new ElasticSampleResult();

				esResu.setTimestamp(sdf.format(new Date(sampleResult.getTimeStamp())));
				esResu.setStartTime(sdf.format(new Date(sampleResult.getStartTime())));
				esResu.setEndTime(Long.toString(sampleResult.getTime()));
				esResu.setTime(sampleResult.getTime());
				esResu.setLatency(sampleResult.getLatency());
				esResu.setConnectTime(sampleResult.getConnectTime());
				esResu.setIdleTime(sampleResult.getIdleTime());
				esResu.setSampleLabel(sampleResult.getSampleLabel());
				esResu.setGroupName(sampleResult.getSampleLabel(true).substring(0,
						sampleResult.getSampleLabel(true).indexOf(sampleResult.getSampleLabel()) - 1));
				esResu.setResponseCode(sampleResult.getResponseCode());
				esResu.setIsResponseCodeOk(sampleResult.isResponseCodeOK());
				esResu.setIsSuccessful(sampleResult.isSuccessful());
				esResu.setSampleCount(sampleResult.getSampleCount());
				esResu.setErrorCount(sampleResult.getErrorCount());
				esResu.setContentType(sampleResult.getContentType());
				esResu.setMediaType(sampleResult.getMediaType());
				esResu.setDataType(sampleResult.getDataType());
				esResu.setRequestHeaders(sampleResult.getRequestHeaders());
				esResu.setResponseHeaders(sampleResult.getResponseHeaders());
				esResu.setHeadersSize(sampleResult.getHeadersSize());
				esResu.setSamplerData(sampleResult.getSamplerData());
				esResu.setResponseMessage(sampleResult.getResponseMessage());
				esResu.setResponseData(sampleResult.getResponseDataAsString());
				esResu.setBodySize(sampleResult.getBodySize());
				esResu.setBytes(sampleResult.getBytes());

				String esResuJson = gson.toJson(esResu);

				String esEndpoint = ctx.getParameter(ES_PROTOCOL) + "://" + ctx.getParameter(ES_HOST) + ":"
						+ ctx.getParameter(ES_PORT) + "/" + ctx.getParameter(ES_INDEX) + "/" + ctx.getParameter(ES_TYPE);

				LOGGER.info("Elasticsearch request URL: " + esEndpoint);
				LOGGER.info("Elasticsearch request data:\n" + esResuJson);

				Response response = null;

				try {
					response = callES(esEndpoint, esResuJson, client);

					boolean resu = response.isSuccessful();
					isESAlive = resu ? true : false;

					if (isESAlive) {
						LOGGER.info("Elasticsearch response code: " + response.code());
						LOGGER.info("Elasticsearch response data:\n" + response.toString());
					} else {
						LOGGER.info("Elasticsearch response code: " + response.code());
						LOGGER.info("Elasticsearch is DOWN");
					}

				} catch (Exception e) {
					LOGGER.error("Error with the Elasticsearch connection.");
				}

			}

		}

	}

	/**
	 * Send sample to the Elasticsearch
	 * 
	 * @param url
	 *            ES host+port+index
	 * @param json
	 *            Serialized sample
	 * @param client
	 *            OkHttpClient
	 * @return OkHttpClient Response
	 * 
	 * @throws IOException
	 */
	private Response callES(String url, String json, OkHttpClient client) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client.newCall(request).execute()) {
			return response;
		}
	}
}