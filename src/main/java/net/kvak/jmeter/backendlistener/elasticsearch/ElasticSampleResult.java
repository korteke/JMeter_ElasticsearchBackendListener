package net.kvak.jmeter.backendlistener.elasticsearch;

import com.google.gson.annotations.SerializedName;

public class ElasticSampleResult {

	@SerializedName("Timestamp")
	private String Timestamp;
	
	@SerializedName("StartTime")
	private String StartTime;
	
	@SerializedName("EndTime")
	private String EndTime;
	
	@SerializedName("Time")
	private Long Time;
	
	@SerializedName("Latency")
	private Long Latency;
	
	@SerializedName("ConnectTime")
	private Long ConnectTime;
	
	@SerializedName("IdleTime")
	private Long IdleTime;
	
	@SerializedName("SampleLabel")
	private String SampleLabel;
	
	@SerializedName("GroupName")
	private String GroupName;
	
	@SerializedName("ResponseCode")
	private String ResponseCode;
	
	@SerializedName("IsResponseCodeOk")
	private Boolean IsResponseCodeOk;
	
	@SerializedName("IsSuccessful")
	private Boolean IsSuccessful;
	
	@SerializedName("SampleCount")
	private Integer SampleCount;
	
	@SerializedName("ErrorCount")
	private Integer ErrorCount;
	
	@SerializedName("ContentType")
	private String ContentType;
	
	@SerializedName("MediaType")
	private String MediaType;
	
	@SerializedName("DataType")
	private String DataType;
	
	@SerializedName("RequestHeaders")
	private String RequestHeaders;
	
	@SerializedName("ResponseHeaders")
	private String ResponseHeaders;
	
	@SerializedName("HeadersSize")
	private Integer HeadersSize;
	
	@SerializedName("SamplerData")
	private String SamplerData;
	
	@SerializedName("ResponseMessage")
	private String ResponseMessage;
	
	@SerializedName("ResponseData")
	private String ResponseData;
	
	@SerializedName("BodySize")
	private Integer BodySize;
	
	@SerializedName("Bytes")
	private Integer Bytes;

	/**
	 * 
	 * @return The Timestamp
	 */
	public String getTimestamp() {
		return Timestamp;
	}

	/**
	 * 
	 * @param Timestamp
	 *            The Timestamp
	 */
	public void setTimestamp(String Timestamp) {
		this.Timestamp = Timestamp;
	}

	/**
	 * 
	 * @return The StartTime
	 */
	public String getStartTime() {
		return StartTime;
	}

	/**
	 * 
	 * @param StartTime
	 *            The StartTime
	 */
	public void setStartTime(String StartTime) {
		this.StartTime = StartTime;
	}

	/**
	 * 
	 * @return The EndTime
	 */
	public String getEndTime() {
		return EndTime;
	}

	/**
	 * 
	 * @param EndTime
	 *            The EndTime
	 */
	public void setEndTime(String EndTime) {
		this.EndTime = EndTime;
	}

	/**
	 * 
	 * @return The Time
	 */
	public Long getTime() {
		return Time;
	}

	/**
	 * 
	 * @param Time
	 *            The Time
	 */
	public void setTime(Long Time) {
		this.Time = Time;
	}

	/**
	 * 
	 * @return The Latency
	 */
	public Long getLatency() {
		return Latency;
	}

	/**
	 * 
	 * @param Latency
	 *            The Latency
	 */
	public void setLatency(Long Latency) {
		this.Latency = Latency;
	}

	/**
	 * 
	 * @return The ConnectTime
	 */
	public Long getConnectTime() {
		return ConnectTime;
	}

	/**
	 * 
	 * @param ConnectTime
	 *            The ConnectTime
	 */
	public void setConnectTime(Long ConnectTime) {
		this.ConnectTime = ConnectTime;
	}

	/**
	 * 
	 * @return The IdleTime
	 */
	public Long getIdleTime() {
		return IdleTime;
	}

	/**
	 * 
	 * @param IdleTime
	 *            The IdleTime
	 */
	public void setIdleTime(Long IdleTime) {
		this.IdleTime = IdleTime;
	}

	/**
	 * 
	 * @return The SampleLabel
	 */
	public String getSampleLabel() {
		return SampleLabel;
	}

	/**
	 * 
	 * @param SampleLabel
	 *            The SampleLabel
	 */
	public void setSampleLabel(String SampleLabel) {
		this.SampleLabel = SampleLabel;
	}

	/**
	 * 
	 * @return The GroupName
	 */
	public String getGroupName() {
		return GroupName;
	}

	/**
	 * 
	 * @param GroupName
	 *            The GroupName
	 */
	public void setGroupName(String GroupName) {
		this.GroupName = GroupName;
	}

	/**
	 * 
	 * @return The ResponseCode
	 */
	public String getResponseCode() {
		return ResponseCode;
	}

	/**
	 * 
	 * @param ResponseCode
	 *            The ResponseCode
	 */
	public void setResponseCode(String ResponseCode) {
		this.ResponseCode = ResponseCode;
	}

	/**
	 * 
	 * @return The IsResponseCodeOk
	 */
	public Boolean getIsResponseCodeOk() {
		return IsResponseCodeOk;
	}

	/**
	 * 
	 * @param IsResponseCodeOk
	 *            The IsResponseCodeOk
	 */
	public void setIsResponseCodeOk(Boolean IsResponseCodeOk) {
		this.IsResponseCodeOk = IsResponseCodeOk;
	}

	/**
	 * 
	 * @return The IsSuccessful
	 */
	public Boolean getIsSuccessful() {
		return IsSuccessful;
	}

	/**
	 * 
	 * @param IsSuccessful
	 *            The IsSuccessful
	 */
	public void setIsSuccessful(Boolean IsSuccessful) {
		this.IsSuccessful = IsSuccessful;
	}

	/**
	 * 
	 * @return The SampleCount
	 */
	public Integer getSampleCount() {
		return SampleCount;
	}

	/**
	 * 
	 * @param SampleCount
	 *            The SampleCount
	 */
	public void setSampleCount(Integer SampleCount) {
		this.SampleCount = SampleCount;
	}

	/**
	 * 
	 * @return The ErrorCount
	 */
	public Integer getErrorCount() {
		return ErrorCount;
	}

	/**
	 * 
	 * @param ErrorCount
	 *            The ErrorCount
	 */
	public void setErrorCount(Integer ErrorCount) {
		this.ErrorCount = ErrorCount;
	}

	/**
	 * 
	 * @return The ContentType
	 */
	public String getContentType() {
		return ContentType;
	}

	/**
	 * 
	 * @param ContentType
	 *            The ContentType
	 */
	public void setContentType(String ContentType) {
		this.ContentType = ContentType;
	}

	/**
	 * 
	 * @return The MediaType
	 */
	public String getMediaType() {
		return MediaType;
	}

	/**
	 * 
	 * @param MediaType
	 *            The MediaType
	 */
	public void setMediaType(String MediaType) {
		this.MediaType = MediaType;
	}

	/**
	 * 
	 * @return The DataType
	 */
	public String getDataType() {
		return DataType;
	}

	/**
	 * 
	 * @param DataType
	 *            The DataType
	 */
	public void setDataType(String DataType) {
		this.DataType = DataType;
	}

	/**
	 * 
	 * @return The RequestHeaders
	 */
	public String getRequestHeaders() {
		return RequestHeaders;
	}

	/**
	 * 
	 * @param RequestHeaders
	 *            The RequestHeaders
	 */
	public void setRequestHeaders(String RequestHeaders) {
		this.RequestHeaders = RequestHeaders;
	}

	/**
	 * 
	 * @return The ResponseHeaders
	 */
	public String getResponseHeaders() {
		return ResponseHeaders;
	}

	/**
	 * 
	 * @param ResponseHeaders
	 *            The ResponseHeaders
	 */
	public void setResponseHeaders(String ResponseHeaders) {
		this.ResponseHeaders = ResponseHeaders;
	}

	/**
	 * 
	 * @return The HeadersSize
	 */
	public Integer getHeadersSize() {
		return HeadersSize;
	}

	/**
	 * 
	 * @param HeadersSize
	 *            The HeadersSize
	 */
	public void setHeadersSize(Integer HeadersSize) {
		this.HeadersSize = HeadersSize;
	}

	/**
	 * 
	 * @return The SamplerData
	 */
	public String getSamplerData() {
		return SamplerData;
	}

	/**
	 * 
	 * @param SamplerData
	 *            The SamplerData
	 */
	public void setSamplerData(String SamplerData) {
		this.SamplerData = SamplerData;
	}

	/**
	 * 
	 * @return The ResponseMessage
	 */
	public String getResponseMessage() {
		return ResponseMessage;
	}

	/**
	 * 
	 * @param ResponseMessage
	 *            The ResponseMessage
	 */
	public void setResponseMessage(String ResponseMessage) {
		this.ResponseMessage = ResponseMessage;
	}

	/**
	 * 
	 * @return The ResponseData
	 */
	public String getResponseData() {
		return ResponseData;
	}

	/**
	 * 
	 * @param ResponseData
	 *            The ResponseData
	 */
	public void setResponseData(String ResponseData) {
		this.ResponseData = ResponseData;
	}

	/**
	 * 
	 * @return The BodySize
	 */
	public Integer getBodySize() {
		return BodySize;
	}

	/**
	 * 
	 * @param BodySize
	 *            The BodySize
	 */
	public void setBodySize(Integer BodySize) {
		this.BodySize = BodySize;
	}

	/**
	 * 
	 * @return The Bytes
	 */
	public Integer getBytes() {
		return Bytes;
	}

	/**
	 * 
	 * @param Bytes
	 *            The Bytes
	 */
	public void setBytes(Integer Bytes) {
		this.Bytes = Bytes;
	}

}