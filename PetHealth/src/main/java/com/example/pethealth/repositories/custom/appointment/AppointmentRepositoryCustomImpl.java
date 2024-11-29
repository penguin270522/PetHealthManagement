package com.example.pethealth.repositories.custom.appointment;

import com.example.pethealth.constant.QueryConstant;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class AppointmentRepositoryCustomImpl implements AppointmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SimpleResponese<Appointment> findByCondition(Map<String, String> params, Pageable pageable) {
        int limit = pageable.getPageSize();
        int page = pageable.getPageNumber() + 1;
        StringBuilder sqlAppoiment = new StringBuilder(buildQueryFilter(params))
                                            .append(" ORDER BY a.start_date DESC")
                                            .append(QueryConstant.LIMIT).append(limit)
                .append(QueryConstant.OFFSET).append(pageable.getOffset());

        Query query = entityManager.createNativeQuery(sqlAppoiment.toString(), Appointment.class);
        List<Appointment> data = query.getResultList();
        int totalPending = countTotalItemPending();
        int totalActive = countTotalItemActive();
        int totalDisable = countTotalItemDisable();
        int totalItem = coutTotalItem(params);
        SimpleResponese<Appointment> results = new SimpleResponese<>();
        results.results = data;
        results.page = page;
        results.limit = limit;
        results.totalItem = totalItem;
        results.totalActive = totalActive;
        results.totalDisable = totalDisable;
        results.totalPending = totalPending;
        results.totalPage = (int) Math.ceil((double) totalItem / (double) limit);
        return results;
    }

    private String buildQueryFilter(Map<String,String> params){
        StringBuilder sql = new StringBuilder(QueryConstant.SELECT_FROM_APPOINTMENT).append(QueryConstant.WHERE_ONE_EQUAL_ONE);
        builderSQLCommon(params, sql);
        return sql.toString();
    }


    private int coutTotalItem(Map<String , String > prams){
        Query countRow = entityManager.createNativeQuery(buildQueryFilter(prams));
        return countRow.getResultList().size();
    }

    private int countTotalItemPending(){
        Query countPending = entityManager.createNativeQuery("SELECT COUNT(*) FROM Appointment WHERE appointment_status = 'PENDING'");
        Number result = (Number) countPending.getSingleResult();
        return result.intValue();
    }

    private int countTotalItemDisable(){
        Query countPending = entityManager.createNativeQuery("SELECT COUNT(*) FROM Appointment WHERE appointment_status = 'DISABLE'");
        Number result = (Number) countPending.getSingleResult();
        return result.intValue();
    }

    private int countTotalItemActive(){
        Query countPending = entityManager.createNativeQuery("SELECT COUNT(*) FROM Appointment WHERE appointment_status = 'ACTIVE'");
        Number result = (Number) countPending.getSingleResult();
        return result.intValue();
    }

    private StringBuilder builderSQLCommon(Map<String , String> params , StringBuilder sql){
        String statusAppointment = params.get("status");
        String nameUser = params.get("nameUser");
        String doctor = params.get("doctorId");
        String fromDay = params.get("fromDay");
        String toDay = params.get("toDay");
        if (fromDay != null && !fromDay.isEmpty() && toDay != null && !toDay.isEmpty()) {
            sql.append(" and a.start_date BETWEEN '")
                    .append(fromDay)
                    .append("' AND '")
                    .append(toDay)
                    .append("' ");
        }
        if(statusAppointment != null){
            if(!statusAppointment.isEmpty()){
                sql.append(String.format(" and a.appointment_status = '%s'",statusAppointment));
            }
        }
        if (nameUser != null && !nameUser.isEmpty()) {
            sql.append(String.format(" AND a.name_user LIKE '%%%s%%'", nameUser));
        }
        if(doctor != null && !doctor.isEmpty()){
            sql.append(String.format(" And a.doctor_id = '%s'", doctor));
        }
        return sql;
    }
}
