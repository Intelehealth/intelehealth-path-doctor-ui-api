package org.openmrs.module.doctor.ui.api.api;

import java.util.Map;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.doctor.ui.api.utils.HttpResponse;

public interface BundleService extends OpenmrsService {
	
	HttpResponse getBundle(String resourceType, Map<String, String> reqParam) throws Exception;
	
}
