package net.kvak.jmeter.backendlistener.elasticsearch;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
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
 */
public class ElasticsearchBackend extends AbstractBackendListenerClient {

    private static final String ES_PROTOCOL = "elasticsearch.protocol";
    private static final String ES_HOST = "elasticsearch.host";
    private static final String ES_PORT = "elasticsearch.port";
    private static final String ES_INDEX = "elasticsearch.index";
    private static final String ES_TYPE = "elasticsearch.type";
    private static final String ES_TIMESTAMP = "elasticsearch.timestamp";
    private static final String ES_STATUSCODE = "elasticsearch.statuscode";

    /**
     * Initialize logger
     */
    private static final Logger LOGGER = LoggingManager.getLoggerForClass();

    /**
     * Lock for syncro
     */
    private static final Object STATIC_LOCK = new Object();

    /**
     * ES connection status
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
        parameters.addArgument(ES_STATUSCODE, "531");
        return parameters;
    }

    @Override
    public void setupTest(BackendListenerContext ctx) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String esEndpoint = ctx.getParameter(ES_PROTOCOL) + "://" + ctx.getParameter(ES_HOST) + ":" + ctx.getParameter(ES_PORT);
        Request request = new Request.Builder().url(esEndpoint).build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
            boolean resu = response.isSuccessful();

            isESAlive = resu ? true : false;

            if (isESAlive) {
                LOGGER.info("Elasticsearch is UP!");
            } else {
                LOGGER.error("Elasticsearch is DOWN!");
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
            LOGGER.error("Elasticsearch is DOWN. Quitting");
            return;
        }

        synchronized (STATIC_LOCK) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            OkHttpClient client = new OkHttpClient();
            SimpleDateFormat sdf = new SimpleDateFormat(ctx.getParameter(ES_TIMESTAMP));
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");

            int index = 0;

            for (SampleResult sampleResult : sampleResults) {
                ElasticSampleResult esResu = new ElasticSampleResult();
                Date elapsedDate = new Date();
                int BuildNumber = (JMeterUtils.getProperty("BuildNumber") != null) ? Integer.parseInt(JMeterUtils.getProperty("BuildNumber")) : 0;

                //Calculate the elapsed time (Starting from midnight on a random day - enables us to compare of two loads over their duration)
                try {
					long start = JMeterContextService.getTestStartTime();
					long end = System.currentTimeMillis();
					long elapsed = (end - start);
                    long minutes = (elapsed / 1000) / 60;
                    long seconds = (elapsed / 1000) % 60;

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0); //If there is more than an hour of data, the number of minutes/seconds will increment this
                    cal.set(Calendar.MINUTE, (int) minutes);
                    cal.set(Calendar.SECOND, (int) seconds);
					String sElapsed = String.format("2017-01-01 %02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
					elapsedDate = formatter.parse(sElapsed);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}

                esResu.setBodySize(sampleResult.getBodySize());
                esResu.setBytes(sampleResult.getBytes());
                esResu.setConnectTime(sampleResult.getConnectTime());
                esResu.setContentType(sampleResult.getContentType());
                esResu.setDataType(sampleResult.getDataType());
                esResu.setEndTime(Long.toString(sampleResult.getTime()));
                esResu.setErrorCount(sampleResult.getErrorCount());
                esResu.setGrpThreads(sampleResult.getGroupThreads());
                esResu.setIdleTime(sampleResult.getIdleTime());
                esResu.setLatency(sampleResult.getLatency());
                esResu.setResponseMessage(sampleResult.getResponseMessage());
                esResu.setResponseTime((sampleResult.getEndTime() - sampleResult.getStartTime()));
                esResu.setSampleCount(sampleResult.getSampleCount());
                esResu.setSampleLabel(sampleResult.getSampleLabel());
                esResu.setStartTime(sdf.format(new Date(sampleResult.getStartTime())));
                esResu.setThreadName(sampleResult.getThreadName());
                esResu.setURL(sampleResult.getUrlAsString());
                esResu.setTimestamp(sdf.format(new Date(sampleResult.getTimeStamp())));
                esResu.setBuildNumber(BuildNumber);
                esResu.setElapsedTime(sdf.format(elapsedDate.getTime()));

                // For Elasticsearch mapping. Mapping states that the
                // ResponseCode must be numeric
                if (sampleResult.isResponseCodeOK() && StringUtils.isNumeric(sampleResult.getResponseCode())) {
                    esResu.setResponseCode(sampleResult.getResponseCode());
                } else {
                    esResu.setResponseCode(ctx.getParameter(ES_STATUSCODE));
                }

                String esResuJson = gson.toJson(esResu);
                String esEndpoint = ctx.getParameter(ES_PROTOCOL) + "://" + ctx.getParameter(ES_HOST) + ":"
                        + ctx.getParameter(ES_PORT) + "/" + ctx.getParameter(ES_INDEX) + "/"
                        + ctx.getParameter(ES_TYPE);

                LOGGER.debug("Elasticsearch request URL: " + esEndpoint);
                LOGGER.debug("Elasticsearch request data:\n" + esResuJson);

                Response response = null;

                try {
                    response = callES(esEndpoint, esResuJson, client);

                    LOGGER.debug("Elasticsearch response code: " + response.code());
                    LOGGER.debug("Elasticsearch response data:\n" + response.toString());

                } catch (IOException e) {
                    LOGGER.error("Error with the Elasticsearch connection.");
                }
                index++;
            }

        }

    }

    /**
     * Send sample to the Elasticsearch
     *
     * @param url    ES host+port+index
     * @param json   Serialized sample
     * @param client OkHttpClient
     * @return OkHttpClient Response
     * @throws IOException
     */
    private Response callES(String url, String json, OkHttpClient client) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            return response;
        } finally {
            if (response != null && response.body() != null)
                response.body().close();
        }

    }
}