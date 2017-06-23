package net.kvak.jmeter.backendlistener.elasticsearch;

import com.google.gson.annotations.SerializedName;

public class ElasticSampleResult {
	@SerializedName("AllThreads")
	private Long AllThreads;
	
	@SerializedName("BodySize")
	private int BodySize;
	
	@SerializedName("Bytes")
	private int Bytes;
	
	@SerializedName("ConnectTime")
	private Long ConnectTime;
	
	@SerializedName("ContentType")
	private String ContentType;
	
	@SerializedName("DataType")
	private String DataType;
	
	@SerializedName("EndTime")
	private String EndTime;
	
	@SerializedName("ErrorCount")
	private int ErrorCount;
	
	@SerializedName("GrpThreads")
	private int GrpThreads;
	
	@SerializedName("IdleTime")
	private Long IdleTime;
	
	@SerializedName("Latency")
	private Long Latency;
	
	@SerializedName("ResponseCode")
	private String ResponseCode;
	
	@SerializedName("ResponseMessage")
	private String ResponseMessage;
	
	@SerializedName("ResponseTime")
	private Long ResponseTime;
	
	@SerializedName("SampleCount")
	private int SampleCount;
	
	@SerializedName("SampleLabel")
	private String SampleLabel;
	
	@SerializedName("StartTime")
	private String StartTime;
	
	@SerializedName("Success")
	private String Success;
	
	@SerializedName("ThreadName")
	private String ThreadName;
	
	@SerializedName("URL")
	private String URL;
	
	@SerializedName("Timestamp")
	private String Timestamp;
	
	@SerializedName("NormalizedTimestamp")
	private String NormalizedTimestamp;

	@SerializedName("BuildNumber")
	private int BuildNumber;

	@SerializedName("ElapsedTime")
	private String ElapsedTime;

	public Long getAllThreads() {
		return AllThreads;
	}

	public void setAllThreads(Long allThreads) {
		AllThreads = allThreads;
	}

	public int getBodySize() {
		return BodySize;
	}

	public void setBodySize(int bodySize) {
		BodySize = bodySize;
	}

	public int getBytes() {
		return Bytes;
	}

	public void setBytes(int bytes) {
		Bytes = bytes;
	}

	public Long getConnectTime() {
		return ConnectTime;
	}

	public void setConnectTime(Long connectTime) {
		ConnectTime = connectTime;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}

	public String getDataType() {
		return DataType;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}

	public String getElapsedTime() {
		return ElapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		ElapsedTime = elapsedTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public int getErrorCount() {
		return ErrorCount;
	}

	public void setErrorCount(int errorCount) {
		ErrorCount = errorCount;
	}

	public int getGrpThreads() {
		return GrpThreads;
	}

	public void setGrpThreads(int grpThreads) {
		GrpThreads = grpThreads;
	}

	public Long getIdleTime() {
		return IdleTime;
	}

	public void setIdleTime(Long idleTime) {
		IdleTime = idleTime;
	}

	public Long getLatency() {
		return Latency;
	}

	public void setLatency(Long latency) {
		Latency = latency;
	}

	public String getResponseCode() {
		return ResponseCode;
	}

	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}

	public String getResponseMessage() {
		return ResponseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
	}

	public Long getResponseTime() {
		return ResponseTime;
	}

	public void setResponseTime(Long responseTime) {
		ResponseTime = responseTime;
	}

	public int getSampleCount() {
		return SampleCount;
	}

	public void setSampleCount(int sampleCount) {
		SampleCount = sampleCount;
	}

	public String getSampleLabel() {
		return SampleLabel;
	}

	public void setSampleLabel(String sampleLabel) {
		SampleLabel = sampleLabel;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String success) {
		Success = success;
	}

	public String getThreadName() {
		return ThreadName;
	}

	public void setThreadName(String threadName) {
		ThreadName = threadName;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	public String getTimestamp() {
		return Timestamp;
	}

	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}

	public String getNormalizedTimestamp() {
		return NormalizedTimestamp;
	}

	public void setNormalizedTimestamp(String normalizedTimestamp) {
		NormalizedTimestamp = normalizedTimestamp;
	}

	public int getBuildNumber() {
		return BuildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		BuildNumber = buildNumber;
	}
}