/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.doctor.ui.api.api.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.doctor.ui.api.ExternalAppointment;
import org.openmrs.module.doctor.ui.api.dto.ExternalAppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ExternalAppointmentDao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public ExternalAppointment save(ExternalAppointment appointment) throws APIException {
		DbSession session = sessionFactory.getCurrentSession();
		
		Serializable id = null;
		
		if (appointment.getId() == null) {
			id = session.save(appointment);
		} else {
			session.saveOrUpdate(appointment);
			id = appointment.getId();
		}
		if (id != null) {
			return (ExternalAppointment) session.get(ExternalAppointment.class, id);
		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalAppointmentDTO> findAllByPatient(String patientId) throws APIException {
		
		List<ExternalAppointmentDTO> appointments = new ArrayList<ExternalAppointmentDTO>();
		
		String sql = " SELECT  id , facility_name facilityName ,facility_uuid facilityUuid  , status status ,"
		        + " request_id requestId,requester_id requesterId, patient_uuid patientUuid ,patient_name  patientName, "
		        + " patient_identifier patientIdentifier,date_created requestDate,practitioner_name practitionerName,"
		        + " appointment_date appointmentDate,appointment_time appointmentTime "
		        + " from external_appointment where patient_uuid= :patientId ";
		
		System.out.println(sql);
		
		appointments = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
		        .addScalar("facilityName", StandardBasicTypes.STRING).addScalar("facilityUuid", StandardBasicTypes.STRING)
		        .addScalar("status", StandardBasicTypes.STRING).addScalar("requestId", StandardBasicTypes.STRING)
		        .addScalar("requesterId", StandardBasicTypes.STRING).addScalar("patientUuid", StandardBasicTypes.STRING)
		        .addScalar("patientName", StandardBasicTypes.STRING)
		        .addScalar("patientIdentifier", StandardBasicTypes.STRING)
		        .addScalar("requestDate", StandardBasicTypes.STRING)
		        .addScalar("practitionerName", StandardBasicTypes.STRING)
		        .addScalar("appointmentDate", StandardBasicTypes.STRING)
		        .addScalar("appointmentTime", StandardBasicTypes.STRING)
		        .setResultTransformer(new AliasToBeanResultTransformer(ExternalAppointmentDTO.class))
		        .setParameter("patientId", patientId).list();
		return appointments;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalAppointment> findAllByRequestId(String requestId) throws APIException {
		
		List<ExternalAppointment> externalAppointments = sessionFactory.getCurrentSession()
		        .createQuery(" from ExternalAppointment where requestId = :requestId ").setString("requestId", requestId)
		        .list();
		return externalAppointments;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalAppointment> search(String startDate, String endDate, String requester) throws APIException {
		
		List<ExternalAppointment> externalAppointments = sessionFactory
		        .getCurrentSession()
		        .createQuery(
		            " from ExternalAppointment where cast(date_created as date) > :start and  cast(date_created as date) < :enddate and requesterId= :requesterId ")
		        .setString("start", startDate).setString("enddate", endDate).setString("requesterId", requester).list();
		return externalAppointments;
		
	}
	
}
