package com.example.pethealth.repositories.custom.post;

import com.example.pethealth.constant.QueryConstant;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public SimpleResponese<Post> getAllPost(Map<String, String> params, Pageable pageable) {
        int limit = pageable.getPageSize();
        int page = pageable.getPageNumber();
        StringBuilder sqlQuery = new StringBuilder(buildSql(params))
                .append(QueryConstant.LIMIT).append(limit)
                .append(QueryConstant.OFFSET).append(pageable.getOffset());
        Query query = entityManager.createNativeQuery(sqlQuery.toString(), Post.class);
        List<Post> postList = query.getResultList();
        int totalItem = countTotal(params);
        SimpleResponese<Post> results = SimpleResponese.<Post>builder()
                .results(postList)
                .page(page)
                .limit(limit)
                .totalItem(totalItem)
                .totalPage((int) Math.ceil((double) totalItem / limit))
                .build();
        return results;
    }

    private int countTotal(Map<String,String> params){
        Query queryRow = entityManager.createNativeQuery(buildSql(params));
        return queryRow.getResultList().size();
    }

    private String buildSql(Map<String,String>params){
        StringBuilder sql = new StringBuilder(QueryConstant.SELECT_FROM_POST).append(QueryConstant.WHERE_ONE_EQUAL_ONE);
        sql = filter(params,sql);
        return sql.toString();
    }

    private StringBuilder filter(Map<String,String> params, StringBuilder sql){
        String user = params.get("user_id");
        String status = params.get("status");
        String typePostId = params.get("type_post");
        String titlePost = params.get("title");
        if(user != null && !user.isEmpty()){
            sql.append(String.format(" and a.user_id = '%s'", user));
        }
        if(status != null && !status.isEmpty()){
            sql.append(String.format(" and a.post_status = '%s'", status));
        }
        if(typePostId != null && !typePostId.isEmpty()){
            sql.append(String.format(" and a.type_post_id = '%s'", typePostId));
        }
        if (titlePost != null && !titlePost.isEmpty()) {
            sql.append(String.format(" and a.title like '%s'", titlePost));
        }
        return sql;
    }
}
