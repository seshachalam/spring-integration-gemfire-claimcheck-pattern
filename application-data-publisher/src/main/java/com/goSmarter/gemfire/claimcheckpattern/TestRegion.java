package com.goSmarter.gemfire.claimcheckpattern;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;

import com.gemstone.bp.edu.emory.mathcs.backport.java.util.Collections;
import com.gemstone.gemfire.cache.AttributesMutator;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.CacheStatistics;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.EntryExistsException;
import com.gemstone.gemfire.cache.EntryNotFoundException;
import com.gemstone.gemfire.cache.InterestResultPolicy;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionAttributes;
import com.gemstone.gemfire.cache.RegionExistsException;
import com.gemstone.gemfire.cache.RegionService;
import com.gemstone.gemfire.cache.StatisticsDisabledException;
import com.gemstone.gemfire.cache.TimeoutException;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;

public final class TestRegion implements Region<UUID, Object>, BeanNameAware {
	
	private static Log logger = LogFactory.getLog(TestRegion.class);
	
	private Map<UUID, Object> map = new HashMap<UUID, Object>();
	private String name;
	
	/**
	 * Default constructor, wraps an empty HashMap
	 */
	public TestRegion() {
	}
	
	/**
	 * Constructor injecting a map
	 * @param map
	 */
	public TestRegion(Map<UUID,Object> map){
	    this.map = map;
	}

	/**
	 * @return
	 * @see java.util.Map#size()
	 */
	public int size() {
		return this.map.size();
	}

	/**
	 * @return
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object key) {
		return this.map.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(UUID key, Object value) {
		return this.map.put(key, value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Object remove(Object key) {
		return this.map.remove(key);
	}

	/**
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends UUID, ? extends Object> m) {
		map.putAll(m);
	}

	/**
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	public Set<UUID> keySet() {
		return this.map.keySet();
	}

	/**
	 * @return
	 * @see java.util.Map#values()
	 */
	public Collection<Object> values() {
		return this.map.values();
	}

	/**
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	public Set<java.util.Map.Entry<UUID, Object>> entrySet() {
		return this.map.entrySet();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return this.map.equals(o);
	}

	/**
	 * @return
	 * @see java.util.Map#hashCode()
	 */
	public int hashCode() {
		return this.map.hashCode();
	}

	public void becomeLockGrantor() {
		
	}

	public void close() {
		
	}

	public boolean containsKeyOnServer(Object arg0) {
		return containsKey(arg0);
	}

	public boolean containsValueForKey(Object arg0) {
		return containsKey(arg0);
	}

	
	public void create(UUID arg0, Object arg1) throws TimeoutException,
			EntryExistsException, CacheWriterException {
		this.map.put(arg0, arg1);
	}

	
	public void create(UUID arg0, Object arg1, Object arg2)
			throws TimeoutException, EntryExistsException, CacheWriterException {
		this.map.put(arg0, arg1);
	}

	
	public <SK, SV> Region<SK, SV> createSubregion(String arg0,
			RegionAttributes<SK, SV> arg1) throws RegionExistsException,
			TimeoutException {
		throw new UnsupportedOperationException();
	}

	
	public Object destroy(Object arg0) throws TimeoutException,
			EntryNotFoundException, CacheWriterException {
		return this.map.remove(arg0);
	}

	
	public Object destroy(Object arg0, Object arg1) throws TimeoutException,
			EntryNotFoundException, CacheWriterException {
		return this.map.remove(arg0);
	}

	
	public void destroyRegion() throws CacheWriterException, TimeoutException {
		this.map.clear();
	}

	
	public void destroyRegion(Object arg0) throws CacheWriterException,
			TimeoutException {
		this.map.clear();
	}

	
	public Set<com.gemstone.gemfire.cache.Region.Entry<?, ?>> entries(
			boolean arg0) {
		return this.entrySet(false);
	}

	@SuppressWarnings("unchecked")
	
	public Set<com.gemstone.gemfire.cache.Region.Entry<?, ?>> entrySet(
			boolean arg0) {
		Set<TestEntry> entries = new HashSet<TestEntry>();
		for (UUID uuid : this.map.keySet()) {
			entries.add(new TestEntry(uuid, this.map.get(uuid), this));
		}
		return Collections.unmodifiableSet(entries);
	}

	
	public boolean existsValue(String arg0) throws FunctionDomainException,
			TypeMismatchException, NameResolutionException,
			QueryInvocationTargetException {
		throw new UnsupportedOperationException();
	}

	
	public void forceRolling() {
	}

	
	public Object get(Object arg0, Object arg1) throws TimeoutException,
			CacheLoaderException {
		return this.get(arg0);
	}

	@SuppressWarnings("unchecked")
	
	public Map<UUID, Object> getAll(Collection<?> arg0) {
		return Collections.unmodifiableMap(this.map);
	}

	
	public RegionAttributes<UUID, Object> getAttributes() {
		return null;
	}

	
	public AttributesMutator<UUID, Object> getAttributesMutator() {
		return null;
	}

	
	public Cache getCache() {
		return null;
	}

	
	public Lock getDistributedLock(Object arg0) throws IllegalStateException {
		return null;
	}

	
	public com.gemstone.gemfire.cache.Region.Entry<UUID, Object> getEntry(
			Object arg0) {
		UUID uuid = (UUID) arg0;
		return new TestEntry(uuid, this.map.get(uuid), this);
	}

	
	public String getFullPath() {
		return "/";
	}

	
	public List<UUID> getInterestList() {
		return null;
	}

	
	public List<String> getInterestListRegex() {
		return null;
	}

	
	public String getName() {
		return this.name;
	}

	
	public <PK, PV> Region<PK, PV> getParentRegion() {
		return null;
	}

	
	public Lock getRegionDistributedLock() throws IllegalStateException {
		return null;
	}

	
	public RegionService getRegionService() {
		return null;
	}

	
	public CacheStatistics getStatistics() throws StatisticsDisabledException {
		return null;
	}

	
	public <SK, SV> Region<SK, SV> getSubregion(String arg0) {
		return null;
	}

	
	public Object getUserAttribute() {
		return null;
	}

	
	public void invalidate(Object arg0) throws TimeoutException,
			EntryNotFoundException {
		this.map.remove(arg0);
	}

	
	public void invalidate(Object arg0, Object arg1) throws TimeoutException,
			EntryNotFoundException {
		this.map.remove(arg0);
	}

	
	public void invalidateRegion() throws TimeoutException {
		this.map.clear();
	}

	
	public void invalidateRegion(Object arg0) throws TimeoutException {
		this.map.clear();
	}

	
	public boolean isDestroyed() {
		return false;
	}

	
	public Set<UUID> keySetOnServer() {
		return this.map.keySet();
	}

	
	public Set<UUID> keys() {
		return this.map.keySet();
	}

	
	public void loadSnapshot(InputStream arg0) throws IOException,
			ClassNotFoundException, CacheWriterException, TimeoutException {
		throw new UnsupportedOperationException();
	}

	
	public void localClear() {
		this.map.clear();
	}

	
	public void localDestroy(Object arg0) throws EntryNotFoundException {
		this.map.remove(arg0);
	}

	
	public void localDestroy(Object arg0, Object arg1)
			throws EntryNotFoundException {
		this.map.remove(arg0);
	}

	
	public void localDestroyRegion() {
		this.map.clear();
	}

	
	public void localDestroyRegion(Object arg0) {
		this.map.clear();
	}

	
	public void localInvalidate(Object arg0) throws EntryNotFoundException {
		this.map.remove(arg0);
	}

	
	public void localInvalidate(Object arg0, Object arg1)
			throws EntryNotFoundException {
		this.map.remove(arg0);
	}

	
	public void localInvalidateRegion() {
		this.map.clear();
	}

	
	public void localInvalidateRegion(Object arg0) {
		this.map.clear();
	}

	
	public Object put(UUID arg0, Object arg1, Object arg2)
			throws TimeoutException, CacheWriterException {
		return this.put(arg0, arg1);
	}

	
	public Object putIfAbsent(UUID arg0, Object arg1) {
		if (!this.map.containsKey(arg0)) {
			return this.map.put(arg0, arg1);
		} else {
			return this.map.get(arg0);
		}
	}

	
	public <E> SelectResults<E> query(String arg0)
			throws FunctionDomainException, TypeMismatchException,
			NameResolutionException, QueryInvocationTargetException {
		throw new UnsupportedOperationException();
	}

	
	public void registerInterest(UUID arg0) {
	}

	
	public void registerInterest(UUID arg0, InterestResultPolicy arg1) {
	}

	
	public void registerInterest(UUID arg0, boolean arg1) {
	}

	
	public void registerInterest(UUID arg0, boolean arg1, boolean arg2) {
	}

	
	public void registerInterest(UUID arg0, InterestResultPolicy arg1,
			boolean arg2) {
	}

	
	public void registerInterest(UUID arg0, InterestResultPolicy arg1,
			boolean arg2, boolean arg3) {
	}

	
	public void registerInterestRegex(String arg0) {
	}

	
	public void registerInterestRegex(String arg0, InterestResultPolicy arg1) {
	}

	
	public void registerInterestRegex(String arg0, boolean arg1) {
	}

	
	public void registerInterestRegex(String arg0, boolean arg1, boolean arg2) {
	}

	
	public void registerInterestRegex(String arg0, InterestResultPolicy arg1,
			boolean arg2) {
	}

	
	public void registerInterestRegex(String arg0, InterestResultPolicy arg1,
			boolean arg2, boolean arg3) {
	}

	
	public boolean remove(Object arg0, Object arg1) {
		if (this.map.containsKey(arg0) && this.map.get(arg0).equals(arg1)) {
			this.map.remove(arg0);
			return true;
		} else {
			return false;
		}
	}

	
	public Object replace(UUID arg0, Object arg1) {
		return this.map.put(arg0, arg1);
	}

	
	public boolean replace(UUID arg0, Object arg1, Object arg2) {
		if (this.remove(arg0, arg1)) {
			this.put(arg0, arg2);
			return true;
		} else {
			return false;
		}
	}

	
	public void saveSnapshot(OutputStream arg0) throws IOException {
		throw new UnsupportedOperationException();
	}

	
	public Object selectValue(String arg0) throws FunctionDomainException,
			TypeMismatchException, NameResolutionException,
			QueryInvocationTargetException {
		return null;
	}

	
	public void setUserAttribute(Object arg0) {
	}

	
	public Set<Region<?, ?>> subregions(boolean arg0) {
		return null;
	}

	
	public void unregisterInterest(UUID arg0) {
	}

	
	public void unregisterInterestRegex(String arg0) {
	}

	
	public void writeToDisk() {
	}
	
	public static class TestEntry implements Entry<UUID, Object> {

		private final UUID uuid;
		private final Object value;
		private final TestRegion region;
		
		public TestEntry(UUID uuid, Object value, TestRegion region) {
			super();
			this.uuid = uuid;
			this.value = value;
			this.region = region;
		}

		
		public UUID getKey() {
			return this.uuid;
		}

		
		public Region<UUID, Object> getRegion() {
			return this.region;
		}

		
		public CacheStatistics getStatistics() {
			return null;
		}

		
		public Object getUserAttribute() {
			return null;
		}

		
		public Object getValue() {
			return this.value;
		}

		
		public boolean isDestroyed() {
			return false;
		}

		
		public boolean isLocal() {
			return true;
		}

		
		public Object setUserAttribute(Object arg0) {
			return null;
		}

		
		public Object setValue(Object arg0) {
			return null;
		}
		
	}

	
	public void setBeanName(String name) {
		this.name = name;
	}

}
