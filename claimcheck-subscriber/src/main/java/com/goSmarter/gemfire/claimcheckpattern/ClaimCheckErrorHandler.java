package com.goSmarter.gemfire.claimcheckpattern;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;

import com.gemstone.gemfire.cache.Region;

/**
 * 
 * @author David Turanski
 *
 */
public class ClaimCheckErrorHandler {
	private static Logger logger = Logger.getLogger(ClaimCheckErrorHandler.class);
	private final Region<UUID,Object> region;

	public ClaimCheckErrorHandler(Region<UUID,Object> region){
		this.region = region;
	}

 	public void handleError(Throwable t) {
		MessagingException me = (MessagingException)t;
		logger.debug(me.getMessage(),me);
		Message<?> message = me.getFailedMessage();
		UUID payloadId = (UUID) message.getHeaders().get(CachedMessageContents.PAYLOAD_ID);
		if (payloadId != null) {
			logger.error( t.getMessage() + " - destroying claimcheck [" +payloadId +"]");
			region.destroy(payloadId);
		} else {
			logger.warn("Cannot destroy claimcheck. message does not contain a header key [" + CachedMessageContents.PAYLOAD_ID + "]");
		}
		return;
	}


}
