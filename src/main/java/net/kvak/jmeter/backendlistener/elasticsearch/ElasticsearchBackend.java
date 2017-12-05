package net.kvak.jmeter.backendlistener.elasticsearch;

import okhttp3.*;
import org.apache.http.client.HttpClient;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
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
import java.util.List;

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

    }

    @Override
    public void teardownTest(BackendListenerContext context) throws Exception {
        // Not implemented
    }

    @Override
    public void handleSampleResults(List<SampleResult> sampleResults, BackendListenerContext ctx) {

    }

    private Response callES(String url, String json, OkHttpClient client) throws IOException {
        Response rep = null;
        return rep;
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
    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
        return new OkHttpClient();
    }
}