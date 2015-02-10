/**
 *
 */
package de.zalando.platform.awsutilizationmonitor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Regions;

/**
 * @author jloeffler
 *
 */
public class AwsCollectorThread extends Thread {
	public static final Logger LOG = LoggerFactory.getLogger(AwsCollectorThread.class);

	private String accountId;
	private AWSCredentials credentials;
	private Regions region;
	private AwsResourceType resourceType;
	private AwsStats stats;

	/**
	 * @param stats
	 * @param credentials
	 * @param region
	 * @param resourceType
	 */
	public AwsCollectorThread(AwsStats stats, AWSCredentials credentials, String accountId, Regions region, AwsResourceType resourceType) {
		this.stats = stats;
		this.credentials = credentials;
		this.accountId = accountId;
		this.region = region;
		this.resourceType = resourceType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			LOG.debug("Start thread for " + resourceType + " in region " + region.getName());
			AwsStatsCollector.scanResources(stats, credentials, accountId, region, resourceType);
		} catch (Exception e) {
			LOG.error("Exception in thread for " + resourceType + " in region " + region.getName() + ": " + e.getMessage());
		}
	}
}
