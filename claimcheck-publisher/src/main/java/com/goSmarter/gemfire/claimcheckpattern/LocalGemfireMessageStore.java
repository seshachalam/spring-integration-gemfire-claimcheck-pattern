package com.goSmarter.gemfire.claimcheckpattern;

import java.util.Map;
import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.Message;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.store.MessageStore;
import org.springframework.util.Assert;

import com.gemstone.gemfire.cache.Region;

public class LocalGemfireMessageStore implements MessageStore {
    private final Region<UUID,  Map<String,Object>> region;
    private Converter<Message<?>, Map<String,Object>> inboundConverter;
    private Converter< Map<String,Object>, Message<?>> outboundConverter;

    public LocalGemfireMessageStore(Region<UUID, Map<String,Object>> region) {
        Assert.notNull(region, "region must not be null");
        this.region = region;
        this.inboundConverter = new InboundConverter();
        this.outboundConverter = new OutboundConverter();
    }

    public Message<?> getMessage(UUID id) {
        Assert.notNull(this.region.get(id), String.format("id %s not found",id.toString()));
        return this.outboundConverter.convert(this.region.get(id));
    }

    public <T> Message<T> addMessage(Message<T> message) {
        this.region.put(message.getHeaders().getId(),this.inboundConverter.convert(message));
        return message;
    }

    public Message<?> removeMessage(UUID id) {
        Assert.notNull(this.region.get(id), String.format("id %s not found",id.toString()));
        return this.outboundConverter.convert(this.region.remove(id));
    }

    public long getMessageCount() {
        return this.region.size();
    }

    public void setInboundConverter(Converter<Message<?>,  Map<String, Object>> inboundConverter) {
        this.inboundConverter = inboundConverter;
    }

    public void setOutboundconverter(Converter< Map<String, Object>, Message<?>> outboundconverter) {
        this.outboundConverter = outboundconverter;
    }

    public static class InboundConverter implements Converter< Message<?>, Map<String,Object> > {

        public  Map<String, Object> convert(Message<?> message) {

            CachedMessageContents cachedMessageContents = new CachedMessageContents(message);

            return cachedMessageContents.asMap();
        }
    }

    public static class OutboundConverter implements Converter<  Map<String,Object>, Message<?> > {

        public Message<?> convert( Map<String, Object> map) {
            CachedMessageContents cachedMessageContents = new CachedMessageContents(map);
            return new GenericMessage<Object>(cachedMessageContents.getPayload(),cachedMessageContents.getHeaders());
        }
    }
}
