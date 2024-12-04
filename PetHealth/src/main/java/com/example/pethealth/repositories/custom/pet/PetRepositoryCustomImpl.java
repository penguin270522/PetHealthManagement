package com.example.pethealth.repositories.custom.pet;


import com.example.pethealth.constant.QueryConstant;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.Pet;
import com.example.pethealth.service.profile.ProfileService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class PetRepositoryCustomImpl implements PetRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final ProfileService profileService;

    public PetRepositoryCustomImpl(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public SimpleResponese<Pet> getAllPet(Map<String, String> maps, Pageable pageable) {
        int limit = pageable.getPageSize();
        int page = pageable.getPageNumber() + 1;
        StringBuilder sqlPet = new StringBuilder(buildQueryFilter(maps))
                .append(QueryConstant.LIMIT).append(limit)
                .append(QueryConstant.OFFSET).append(pageable.getOffset());

        Query query = entityManager.createNativeQuery(sqlPet.toString(), Pet.class);
        List<Pet> data = query.getResultList();
        int totalItem = countTotal(maps);
        SimpleResponese<Pet> results = new SimpleResponese<>();
        results.results = data;
        results.page = page;
        results.limit = limit;
        results.totalItem = totalItem;
        results.totalPage = (int) Math.ceil((double) totalItem / limit);
        return results;
    }


    private String buildQueryFilter(Map<String, String> params){
        StringBuilder sql = new StringBuilder(QueryConstant.SELECT_FROM_PET)
                .append(QueryConstant.WHERE_ONE_EQUAL_ONE);
        sql = buildFilter(params,sql);
        return sql.toString();
    }
    private int countTotal(Map<String, String> params){
        Query queryRow = entityManager.createNativeQuery(buildQueryFilter(params));
        return queryRow.getResultList().size();
    }
    private StringBuilder buildFilter(Map<String, String> params, StringBuilder sql){
        String namePet = params.get("name");
        String userId = params.get("userId");
        if(namePet != null){
            if(!namePet.isEmpty()){
                sql.append(String.format(" and a.name LIKE '%%%s%%'", namePet));
            }
        }
        if(userId != null){
            if(!userId.isEmpty()){
                sql.append(String.format(" and a.user_id = %s", userId));
            }
        }
        return sql;
    }

    @Override
    public SimpleResponese<Pet> getPetWithStatus(Map<String, String> maps, Pageable pageable) {
        int limit = pageable.getPageSize();
        int page = pageable.getPageNumber() + 1;
        StringBuilder sql = new StringBuilder("SELECT p.*\n" +
                "FROM pet p\n" +
                "JOIN appointment lk ON p.id = lk.pet_id\n" +
                "WHERE lk.appointment_status = 'ACTIVE'\n" +
                "GROUP BY p.id");
        Query query = entityManager.createNativeQuery(sql.toString(), Pet.class);
        List<Pet> data = query.getResultList();
        int totalItem = countTotal(maps);
        SimpleResponese<Pet> results = new SimpleResponese<>();
        results.results = data;
        results.page = page;
        results.limit = limit;
        results.totalItem = totalItem;
        results.totalPage = (int) Math.ceil((double) totalItem / limit);
        return results;
    }

    private String buildQueryWithAppointmentStatus(Map<String,String> params){
        StringBuilder sql = new StringBuilder(QueryConstant.SELCET_FORM_PET_APPOINTMENT)
                .append(QueryConstant.JOIN_APPOINTMENT)
                .append(QueryConstant.WHERE);
        sql = builderPetAndAppointment(params,sql);
        return sql.toString();
    }

    private StringBuilder builderPetAndAppointment(Map<String,String> params,StringBuilder sql){
        String statusPet = params.get("status");
        String user_id = params.get("userId");
        if(statusPet != null && !statusPet.isEmpty()){
            sql.append(String.format(QueryConstant.APPOINTMENT_STATUS, statusPet));
        }
        if(user_id != null && !user_id.isEmpty()){
            sql.append(String.format(" and a.user_id = %s", user_id));
        }
        return sql;
    }
}
