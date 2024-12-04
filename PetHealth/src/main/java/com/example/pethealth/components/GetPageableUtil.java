package com.example.pethealth.components;


import com.example.pethealth.constant.EnvironmentConstant;
import com.example.pethealth.exception.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class GetPageableUtil {
    public static Pageable getPageable(Map<String, String> params) {
        int page = EnvironmentConstant.PAGE_DEFAULT_INDEX;
        int limit = EnvironmentConstant.LIMIT_DEFAULT;

        try{
            if(params.containsKey("page")){
                page = Integer.parseInt(params.get("page")) - 1;
            }
            if(params.containsKey("limit")){
                limit = Integer.parseInt(params.get("limit"));
            }
        }catch(NumberFormatException e){
            throw new BadRequestException("Invalid page or limit values");
        }

        if(page < 0){
            throw new BadRequestException("Page number must > 0");
        }
        if(limit < 0){
            throw new BadRequestException("Limit number must > 0");
        }

        // sort limit
        Sort sort = Sort.by("start_date", "id").descending();
        String sortDir = params.get("sort");
        if(sortDir != null){
            if(sortDir.equals("asc")){
                sort = sort.ascending();
            }
        }
        return PageRequest.of(page,limit,sort);
    }
}
