package com.goSmarter.gemfire.claimcheckpattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.integration.Message;
import org.springframework.integration.history.MessageHistory;

@SuppressWarnings("serial")
public final class CachedMessageContents extends ConcurrentHashMap<String, Object> {
 
	public static final String PAYLOAD_ID = "payloadId";
	public static final String PAYLOAD = "payload";
	public static final String HISTORY_AS_LIST = "historyAsList";

	public CachedMessageContents(Map<String,Object> map){
		super(map);
	}
	
	public CachedMessageContents(Message<?> message) {
		super(serializableHeaders(message));
	
	    put(PAYLOAD_ID, message.getHeaders().getId());	 
		put(PAYLOAD, message.getPayload());
			
		transformMessageHistory(message);
	}

	public Object getPayload() {
		return get(PAYLOAD);
	}

	public UUID getPayloadId() {
		return (UUID)get(PAYLOAD_ID);
	}
	

	public Map<String, Object> getHeaders() {
		Map<String, Object> headers = new ConcurrentHashMap<String, Object>();
		for (Entry<String, Object> entry : entrySet()) {
			if (!entry.getKey().equals(PAYLOAD)  && 
				!entry.getKey().equals(PAYLOAD_ID) && 
				!entry.getKey().equals(MessageHistory.HEADER_NAME)) {
				headers.put(entry.getKey(), entry.getValue());
			}
		}
		return Collections.unmodifiableMap(headers);
	}

	
	public Map<String, Object> asMap(){
		return new HashMap<String,Object>(this);
	}
	
	
	@SuppressWarnings("unchecked")
	private void transformMessageHistory(Message<?> message) {
		MessageHistory history = MessageHistory.read(message);
		if (history != null) {
		  remove(MessageHistory.HEADER_NAME);
		  List<Properties> historyAsList = null; 
		  if (containsKey(HISTORY_AS_LIST)){
			  historyAsList =  (List<Properties>)get(HISTORY_AS_LIST);
		  } else {
			  historyAsList = new ArrayList<Properties>(); 
		  }
		  for (Properties props : history.subList(0, history.size())) {
			  MessageHistory.Entry entry = (MessageHistory.Entry) props;
			  Properties p = new Properties();
			  p.putAll(entry);
			  historyAsList.add(p);
		  }
		  if (!containsKey(HISTORY_AS_LIST)){
		     put(HISTORY_AS_LIST, historyAsList);
		  }
		}
	}
	
	private static final Map<String,Object> serializableHeaders(Message<?> message) {
		Map<String, Object> headers = new ConcurrentHashMap<String, Object>();
		for (Entry<String,Object> entry: message.getHeaders().entrySet()) {
			if (entry.getValue() instanceof Serializable ){
				headers.put(entry.getKey(), entry.getValue());
			}
		}
		return Collections.unmodifiableMap(headers);
	}
}
