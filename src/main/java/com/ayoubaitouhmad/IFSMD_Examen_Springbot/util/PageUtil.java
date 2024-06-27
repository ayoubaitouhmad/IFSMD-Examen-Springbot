package com.ayoubaitouhmad.IFSMD_Examen_Springbot.util;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.BaseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PageUtil {

    public static Integer PAGINATION_PAGE_SIZE;

    public static String ALERT;
    public static String ALERT_PRIMARY = "primary";
    public static String ALERT_SUCCESS = "success";
    public static String ALERT_SECONDARY = "secondary";
    public Map<String, Object> alertDetails;

    @Value("${page.pagination.size}")
    public void setPaginationPageSize(Integer pageSize) {
        PageUtil.PAGINATION_PAGE_SIZE = pageSize;
    }



    /***
     * Paginate list of model
     * Calculate boundaries page index and how much user can see for every paginated list
     * @param pageList
     * @param page
     * @return
     */
    public static Page<BaseModel> PageList(List<? extends BaseModel> pageList, Integer page) {
        int start = (int) PageRequest.of(page, PAGINATION_PAGE_SIZE).getOffset();
        int end = Math.min((start + PageRequest.of(page, PAGINATION_PAGE_SIZE).getPageSize()), pageList.size());
        return (Page<BaseModel>) new PageImpl<>(pageList.subList(start, end), PageRequest.of(page, PAGINATION_PAGE_SIZE), pageList.size());
    }

    public static PageUtil alert(String alert_type) {
        ALERT = alert_type;
        return new PageUtil();
    }



    public PageUtil setMessage(String message) {
        alertDetails = new HashMap<>();
        alertDetails.put("message", message);
        alertDetails.put("type", ALERT);
        return this;
    }

    public Map<String, Object> getAlert() {
        return  alertDetails;
    }

}
