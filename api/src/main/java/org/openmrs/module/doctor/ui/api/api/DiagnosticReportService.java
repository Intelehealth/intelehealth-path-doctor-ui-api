/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.doctor.ui.api.api;

import java.util.Map;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.doctor.ui.api.DiagnosticReport;
import org.openmrs.module.doctor.ui.api.utils.HttpResponse;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface DiagnosticReportService extends OpenmrsService {
	
	HttpResponse saveDiagnosticReport(Map<String, String> reqParam) throws Exception;
	
	DiagnosticReport getReportData(String patientId, String resourceId) throws Exception;
	
}
