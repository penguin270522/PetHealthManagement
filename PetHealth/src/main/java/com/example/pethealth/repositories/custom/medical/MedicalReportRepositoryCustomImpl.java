package com.example.pethealth.repositories.custom.medical;

import com.example.pethealth.constant.QueryConstant;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.MedicalReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Map;

public class MedicalReportRepositoryCustomImpl implements MedicalReportRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public SimpleResponese<MedicalReport> findByCondition(Map<String, String> params, Pageable pageable) {
        int limit = pageable.getPageSize();
        int page = pageable.getPageNumber() + 1;
        int totalItem = countTotal(params);

        StringBuilder sql = new StringBuilder(buildQueryFilter(params))
                .append(" ORDER BY a.created_date desc")
                .append(QueryConstant.LIMIT).append(limit)
                .append(QueryConstant.OFFSET).append(pageable.getOffset());
        Query query = entityManager.createNativeQuery(sql.toString(), MedicalReport.class);
        List<MedicalReport> reportList = query.getResultList();
        SimpleResponese<MedicalReport> simpleResponese = SimpleResponese.<MedicalReport>builder()
                .results(reportList)
                .totalPage((int) Math.ceil((double) totalItem / (double) limit))
                .page(page)
                .totalItem(totalItem)
                .limit(limit)
                .build();
        return simpleResponese;
    }

    private String buildQueryFilter(Map<String , String> params){
        StringBuilder sql = new StringBuilder(QueryConstant.SELECT_FROM_MEDICAL_REPORT)
                .append(QueryConstant.WHERE_ONE_EQUAL_ONE);
        sql = buildFilter(params, sql);
        return sql.toString();
    }

    private int countTotal(Map<String, String> params){
        Query queryRow = entityManager.createNativeQuery(buildQueryFilter(params));
        return queryRow.getResultList().size();
    }
    private StringBuilder buildFilter(Map<String, String> params, StringBuilder sql){
        StringBuilder queryFilter = sql;
        String followSchedule  = params.get("followSchudele");
        if (followSchedule != null && !followSchedule.isEmpty()) {
            sql.append(" AND a.follow_schedule ").append(QueryConstant.IS_NOT_NULL);
        }
        return queryFilter;
    }
}
