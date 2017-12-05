package net.kvak.jmeter.backendlistener.elasticsearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.backend.AbstractBackendListenerClient;
import org.apache.jmeter.visualizers.backend.BackendListenerContext;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
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
    private static final String ES_TRUSTALLSSL = "elasticsearch.trustAllSslCertificates";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private boolean elasticsearchState = false;

    @Override
    public Arguments getDefaultParameters() {
        Arguments parameters = new Arguments();
        parameters.addArgument(ES_PROTOCOL, "https");
        parameters.addArgument(ES_HOST, null);
        parameters.addArgument(ES_PORT, "9200");
        parameters.addArgument(ES_INDEX, "jmeter");
        parameters.addArgument(ES_TYPE, null);
        parameters.addArgument(ES_TIMESTAMP, "yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        parameters.addArgument(ES_STATUSCODE, "531");
        parameters.addArgument(ES_TRUSTALLSSL, "true");
        return parameters;
    }

    @Override
    public void setupTest(BackendListenerContext ctx) throws Exception {
        OkHttpClient client = trustAllSslClient();
        String elasticSearchURL = ctx.getParameter(ES_PROTOCOL) + "://" + ctx.getParameter(ES_HOST) + ":" + ctx.getParameter(ES_PORT);
        Request request = new Request.Builder().url(elasticSearchURL).build();
        Response response = null;

        try {
            client = trustAllSslClient();
            response = client.newCall(request).execute();
            elasticsearchState = response.isSuccessful();
            response.close();
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    @Override
    public void handleSampleResults(List<SampleResult> sampleResults, BackendListenerContext ctx) {
        if(!elasticsearchState)
            return;
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OkHttpClient client = trustAllSslClient();
        SimpleDateFormat sdf = new SimpleDateFormat(ctx.getParameter(ES_TIMESTAMP));
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
        int index = 0;
        
        //foreach sampler
        for(SampleResult sr : sampleResults) {
            ElasticSampleResult elasticData = new ElasticSampleResult();
            Date elapsedDate = new Date();
            int BuildNumber = (JMeterUtils.getProperty("BuildNumber") != null && JMeterUtils.getProperty("BuildNumber").trim() != "") ? Integer.parseInt(JMeterUtils.getProperty("BuildNumber")) : 0;
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
                printStackTrace(e);
            }
            elasticData.setBodySize(sr.getBodySize());
            elasticData.setBytes(sr.getBytes());
            elasticData.setConnectTime(sr.getConnectTime());
            elasticData.setContentType(sr.getContentType());
            elasticData.setDataType(sr.getDataType());
            elasticData.setEndTime(Long.toString(sr.getTime()));
            elasticData.setErrorCount(sr.getErrorCount());
            elasticData.setGrpThreads(sr.getGroupThreads());
            elasticData.setIdleTime(sr.getIdleTime());
            elasticData.setLatency(sr.getLatency());
            elasticData.setResponseMessage(sr.getResponseMessage());
            elasticData.setResponseTime((sr.getEndTime() - sr.getStartTime()));
            elasticData.setSampleCount(sr.getSampleCount());
            elasticData.setSampleLabel(sr.getSampleLabel());
            elasticData.setStartTime(sdf.format(new Date(sr.getStartTime())));
            elasticData.setThreadName(sr.getThreadName());
            elasticData.setURL(sr.getUrlAsString());
            elasticData.setTimestamp(sdf.format(new Date(sr.getTimeStamp())));
            elasticData.setBuildNumber(BuildNumber);
            elasticData.setElapsedTime(sdf.format(elapsedDate.getTime()));
            elasticData.setResponseCode((sr.isResponseCodeOK() && StringUtils.isNumeric(sr.getResponseCode())) ? sr.getResponseCode() : ctx.getParameter(ES_STATUSCODE));

            String elasticDataJson = gson.toJson(elasticData);
            String esEndpoint = ctx.getParameter(ES_PROTOCOL) + "://" + ctx.getParameter(ES_HOST) + ":"
                    + ctx.getParameter(ES_PORT) + "/" + ctx.getParameter(ES_INDEX) + "/"
                    + ctx.getParameter(ES_TYPE);
            Response response = null;

            try {
                response = callES(esEndpoint, elasticDataJson, client);
                response.close();
            } catch (IOException e) {
                printStackTrace(e);
            }
            index++;
        }
    }

    private Response callES(String url, String json, OkHttpClient client) throws IOException {
        RequestBody postBody = RequestBody.create(JSON, json);
        Request req = new Request.Builder().url(url).post(postBody).build();

        try {
            return client.newCall(req).execute();
        } catch (Exception e) {
            printStackTrace(e);
            return null;
        }
    }

    private static final TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }
    };
    private static final SSLContext trustAllSslContext;
    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

    /*
     * This should not be used in production unless you really don't care
     * about the security. Use at your own risk.
     */
    public static OkHttpClient trustAllSslClient() {
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = client.newBuilder();
        ConnectionPool cp = new ConnectionPool(10, 15000, TimeUnit.MILLISECONDS);
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier((hostname, session) -> true);
        builder.connectionPool(cp);
        return builder.build();
    }

    public static void printStackTrace(Exception e) {
        System.out.println(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
    }
}