package com.orangefunction.tomcat.redissessions;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.util.LifecycleSupport;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Loader;
import org.apache.catalina.Valve;
import org.apache.catalina.Session;
import org.apache.catalina.session.ManagerBase;

import java.io.IOException;
import java.util.Arrays;

import java.util.Enumeration;
import java.util.EnumSet;
import java.util.Iterator;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.netty.util.concurrent.FastThreadLocal;

public class RedisSessionManager extends ManagerBase implements Lifecycle {

	enum SessionPersistPolicy {
		DEFAULT, SAVE_ON_CHANGE, ALWAYS_SAVE_AFTER_REQUEST;

		static SessionPersistPolicy fromName(String name) {
			for (SessionPersistPolicy policy : SessionPersistPolicy.values()) {
				if (policy.name().equalsIgnoreCase(name)) {
					return policy;
				}
			}
			throw new IllegalArgumentException("Invalid session persist policy [" + name + "]. Must be one of "
					+ Arrays.asList(SessionPersistPolicy.values()) + ".");
		}
	}

	protected byte[] NULL_SESSION = "null".getBytes();

	private final Log log = LogFactory.getLog(RedisSessionManager.class);

	protected RedisSessionHandlerValve handlerValve;
	protected FastThreadLocal<RedisSession> currentSession = new FastThreadLocal<RedisSession>();
	protected FastThreadLocal<SessionSerializationMetadata> currentSessionSerializationMetadata = new FastThreadLocal<SessionSerializationMetadata>();
	protected FastThreadLocal<String> currentSessionId = new FastThreadLocal<String>();
	protected FastThreadLocal<Boolean> currentSessionIsPersisted = new FastThreadLocal<Boolean>();
	protected Serializer serializer;
	protected static String name = "RedisSessionManager";
	protected String serializationStrategyClass = "com.orangefunction.tomcat.redissessions.JavaSerializer";
	protected EnumSet<SessionPersistPolicy> sessionPersistPoliciesSet = EnumSet.of(SessionPersistPolicy.DEFAULT);

	/**
	 * The lifecycle event support for this component.
	 */
	protected LifecycleSupport lifecycle = new LifecycleSupport(this);

	public void setSerializationStrategyClass(String strategy) {
		this.serializationStrategyClass = strategy;
	}

	public String getSessionPersistPolicies() {
		StringBuilder policies = new StringBuilder();
		for (Iterator<SessionPersistPolicy> iter = this.sessionPersistPoliciesSet.iterator(); iter.hasNext();) {
			SessionPersistPolicy policy = iter.next();
			policies.append(policy.name());
			if (iter.hasNext()) {
				policies.append(",");
			}
		}
		return policies.toString();
	}

	public void setSessionPersistPolicies(String sessionPersistPolicies) {
		String[] policyArray = sessionPersistPolicies.split(",");
		EnumSet<SessionPersistPolicy> policySet = EnumSet.of(SessionPersistPolicy.DEFAULT);
		for (String policyName : policyArray) {
			SessionPersistPolicy policy = SessionPersistPolicy.fromName(policyName);
			policySet.add(policy);
		}
		this.sessionPersistPoliciesSet = policySet;
	}

	public boolean getSaveOnChange() {
		return this.sessionPersistPoliciesSet.contains(SessionPersistPolicy.SAVE_ON_CHANGE);
	}

	public boolean getAlwaysSaveAfterRequest() {
		return this.sessionPersistPoliciesSet.contains(SessionPersistPolicy.ALWAYS_SAVE_AFTER_REQUEST);
	}

	@Override
	public int getRejectedSessions() {
		// Essentially do nothing.
		return 0;
	}

	public void setRejectedSessions(int i) {
		// Do nothing.
	}

	@Override
	public void load() throws ClassNotFoundException, IOException {

	}

	@Override
	public void unload() throws IOException {

	}

	/**
	 * Add a lifecycle event listener to this component.
	 *
	 * @param listener
	 *            The listener to add
	 */
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		lifecycle.addLifecycleListener(listener);
	}

	/**
	 * Get the lifecycle listeners associated with this lifecycle. If this
	 * Lifecycle has no listeners registered, a zero-length array is returned.
	 */
	@Override
	public LifecycleListener[] findLifecycleListeners() {
		return lifecycle.findLifecycleListeners();
	}

	/**
	 * Remove a lifecycle event listener from this component.
	 *
	 * @param listener
	 *            The listener to remove
	 */
	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		lifecycle.removeLifecycleListener(listener);
	}

	/**
	 * Start this component and implement the requirements of
	 * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
	 *
	 * @exception LifecycleException
	 *                if this component detects a fatal error that prevents this
	 *                component from being used
	 */
	@Override
	protected synchronized void startInternal() throws LifecycleException {
		super.startInternal();

		setState(LifecycleState.STARTING);

		Boolean attachedToValve = false;
		for (Valve valve : getContainer().getPipeline().getValves()) {
			if (valve instanceof RedisSessionHandlerValve) {
				this.handlerValve = (RedisSessionHandlerValve) valve;
				this.handlerValve.setRedisSessionManager(this);
				log.info("Attached to RedisSessionHandlerValve");
				attachedToValve = true;
				break;
			}
		}

		if (!attachedToValve) {
			String error = "Unable to attach to session handling valve; sessions cannot be saved after the request without the valve starting properly.";
			log.fatal(error);
			throw new LifecycleException(error);
		}

		try {
			initializeSerializer();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			log.fatal("Unable to load serializer", e);
			throw new LifecycleException(e);
		}

		try {
			RedisFactory.getInstance().init();
		} catch (Exception ex) {
			ex.getMessage();
		}

		log.info("Will expire sessions after " + getMaxInactiveInterval() + " seconds");

		setDistributable(true);
	}

	/**
	 * Stop this component and implement the requirements of
	 * {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
	 *
	 * @exception LifecycleException
	 *                if this component detects a fatal error that prevents this
	 *                component from being used
	 */
	@Override
	protected synchronized void stopInternal() throws LifecycleException {
		if (log.isDebugEnabled()) {
			log.debug("Stopping");
		}

		setState(LifecycleState.STOPPING);

		try {
			RedisFactory.getInstance().close();
		} catch (Exception e) {
			// Do nothing.
		}

		// Require a new random number generator if we are restarted
		super.stopInternal();
	}

	@Override
	public Session createSession(String requestedSessionId) {
		RedisSession session = null;
		String sessionId = null;
		String jvmRoute = getJvmRoute();

		// Ensure generation of a unique session identifier.
		if (null != requestedSessionId) {
			sessionId = sessionIdWithJvmRoute(requestedSessionId, jvmRoute);
			if (RedisFactory.getInstance().setnx(sessionId.getBytes(), NULL_SESSION) == 0L) {
				sessionId = null;
			}
		} else {
			do {
				// sessionId = sessionIdWithJvmRoute(generateSessionId(),
				// jvmRoute);
				sessionId = UUID32.getUUID() + System.currentTimeMillis();
			} while (RedisFactory.getInstance().setnx(sessionId.getBytes(), NULL_SESSION) == 0L);
		}

		if (null != sessionId) {
			session = (RedisSession) createEmptySession();
			session.setNew(true);
			session.setValid(true);
			session.setCreationTime(System.currentTimeMillis());
			session.setMaxInactiveInterval(getMaxInactiveInterval());
			session.setId(sessionId);
			session.tellNew();
		}

		currentSession.set(session);
		currentSessionId.set(sessionId);
		currentSessionIsPersisted.set(false);
		currentSessionSerializationMetadata.set(new SessionSerializationMetadata());

		if (null != session) {
			try {
				saveInternal(session, true);
			} catch (Exception ex) {
				log.error("Error saving newly created session: " + ex.getMessage());
				currentSession.set(null);
				currentSessionId.set(null);
				session = null;
			}
		}

		return session;
	}

	private String sessionIdWithJvmRoute(String sessionId, String jvmRoute) {
		if (jvmRoute != null) {
			String jvmRoutePrefix = '.' + jvmRoute;
			return sessionId.endsWith(jvmRoutePrefix) ? sessionId : sessionId + jvmRoutePrefix;
		}
		return sessionId;
	}

	@Override
	public Session createEmptySession() {
		return new RedisSession(this);
	}

	@Override
	public void add(Session session) {
		try {
			save(session);
		} catch (IOException ex) {
			log.warn("Unable to add to session manager store: " + ex.getMessage());
			throw new RuntimeException("Unable to add to session manager store.", ex);
		}
	}

	@Override
	public Session findSession(String id) throws IOException {
		RedisSession session = null;

		if (null == id) {
			currentSessionIsPersisted.set(false);
			currentSession.set(null);
			currentSessionSerializationMetadata.set(null);
			currentSessionId.set(null);
		} else if (id.equals(currentSessionId.get())) {
			session = currentSession.get();
		} else {
			byte[] data = loadSessionDataFromRedis(id);
			if (data != null) {
				DeserializedSessionContainer container = sessionFromSerializedData(id, data);
				session = container.session;
				currentSession.set(session);
				currentSessionSerializationMetadata.set(container.metadata);
				currentSessionIsPersisted.set(true);
				currentSessionId.set(id);
			} else {
				currentSessionIsPersisted.set(false);
				currentSession.set(null);
				currentSessionSerializationMetadata.set(null);
				currentSessionId.set(null);
			}
		}

		return session;
	}

	public byte[] loadSessionDataFromRedis(String id) throws IOException {
		log.trace("Attempting to load session " + id + " from Redis");
		byte[] data = RedisFactory.getInstance().getBytes(id.getBytes());
		if (data == null) {
			log.trace("Session " + id + " not found in Redis");
		}
		return data;
	}

	public DeserializedSessionContainer sessionFromSerializedData(String id, byte[] data) throws IOException {
		log.trace("Deserializing session " + id + " from Redis");

		if (Arrays.equals(NULL_SESSION, data)) {
			log.error("Encountered serialized session " + id + " with data equal to NULL_SESSION. This is a bug.");
			throw new IOException("Serialized session data was equal to NULL_SESSION");
		}

		RedisSession session = null;
		SessionSerializationMetadata metadata = new SessionSerializationMetadata();

		try {
			session = (RedisSession) createEmptySession();

			serializer.deserializeInto(data, session, metadata);

			session.setId(id);
			session.setNew(false);
			session.setMaxInactiveInterval(getMaxInactiveInterval());
			session.access();
			session.setValid(true);
			session.resetDirtyTracking();

			if (log.isTraceEnabled()) {
				log.trace("Session Contents [" + id + "]:");
				Enumeration<String> en = session.getAttributeNames();
				while (en.hasMoreElements()) {
					log.trace("  " + en.nextElement());
				}
			}
		} catch (ClassNotFoundException ex) {
			log.fatal("Unable to deserialize into session", ex);
			throw new IOException("Unable to deserialize into session", ex);
		}

		return new DeserializedSessionContainer(session, metadata);
	}

	public void save(Session session) throws IOException {
		save(session, false);
	}

	public void save(Session session, boolean forceSave) throws IOException {
		try {
			saveInternal(session, forceSave);
		} catch (IOException e) {
			throw e;
		}
	}

	protected boolean saveInternal(Session session, boolean forceSave) throws IOException {
		Boolean error = true;
		try {
			log.trace("Saving session " + session + " into Redis");

			RedisSession redisSession = (RedisSession) session;

			if (log.isTraceEnabled()) {
				log.trace("Session Contents [" + redisSession.getId() + "]:");
				Enumeration<String> en = redisSession.getAttributeNames();
				while (en.hasMoreElements()) {
					log.trace("  " + en.nextElement());
				}
			}

			byte[] binaryId = redisSession.getId().getBytes();

			Boolean isCurrentSessionPersisted;
			SessionSerializationMetadata sessionSerializationMetadata = currentSessionSerializationMetadata.get();
			byte[] originalSessionAttributesHash = sessionSerializationMetadata.getSessionAttributesHash();
			byte[] sessionAttributesHash = null;
			if (forceSave || redisSession.isDirty()
					|| null == (isCurrentSessionPersisted = this.currentSessionIsPersisted.get())
					|| !isCurrentSessionPersisted || !Arrays.equals(originalSessionAttributesHash,
							(sessionAttributesHash = serializer.attributesHashFrom(redisSession)))) {

				log.trace("Save was determined to be necessary");

				if (null == sessionAttributesHash) {
					sessionAttributesHash = serializer.attributesHashFrom(redisSession);
				}

				SessionSerializationMetadata updatedSerializationMetadata = new SessionSerializationMetadata();
				updatedSerializationMetadata.setSessionAttributesHash(sessionAttributesHash);

				RedisFactory.getInstance().set(binaryId,
						serializer.serializeFrom(redisSession, updatedSerializationMetadata));
				redisSession.resetDirtyTracking();
				currentSessionSerializationMetadata.set(updatedSerializationMetadata);
				currentSessionIsPersisted.set(true);
			} else {
				log.trace("Save was determined to be unnecessary");
			}

			log.trace(
					"Setting expire timeout on session [" + redisSession.getId() + "] to " + getMaxInactiveInterval());

			RedisFactory.getInstance().expire(binaryId, getMaxInactiveInterval());
			error = false;

			return error;
		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public void remove(Session session) {
		remove(session, false);
	}

	@Override
	public void remove(Session session, boolean update) {
		log.trace("Removing session ID : " + session.getId());
		RedisFactory.getInstance().del(session.getId());
	}

	public void afterRequest() {
		RedisSession redisSession = currentSession.get();
		if (redisSession != null) {
			try {
				if (redisSession.isValid()) {
					log.trace("Request with session completed, saving session " + redisSession.getId());
					save(redisSession, getAlwaysSaveAfterRequest());
				} else {
					log.trace("HTTP Session has been invalidated, removing :" + redisSession.getId());
					remove(redisSession);
				}
			} catch (Exception e) {
				log.error("Error storing/removing session", e);
			} finally {
				currentSession.remove();
				currentSessionId.remove();
				currentSessionIsPersisted.remove();
				log.trace("Session removed from ThreadLocal :" + redisSession.getIdInternal());
			}
		}
	}

	@Override
	public void processExpires() {
		// We are going to use Redis's ability to expire keys for session
		// expiration.

		// Do nothing.
	}

	private void initializeSerializer() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		log.info("Attempting to use serializer :" + serializationStrategyClass);
		serializer = (Serializer) Class.forName(serializationStrategyClass).newInstance();

		Loader loader = null;

		if (getContainer() != null) {
			loader = getContainer().getLoader();
		}

		ClassLoader classLoader = null;

		if (loader != null) {
			classLoader = loader.getClassLoader();
		}
		serializer.setClassLoader(classLoader);
	}

}

class DeserializedSessionContainer {
	public final RedisSession session;
	public final SessionSerializationMetadata metadata;

	public DeserializedSessionContainer(RedisSession session, SessionSerializationMetadata metadata) {
		this.session = session;
		this.metadata = metadata;
	}
}
