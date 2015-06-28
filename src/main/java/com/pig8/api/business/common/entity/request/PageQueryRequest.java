package com.pig8.api.business.common.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pig8.api.platform.global.Constants;
import com.pig8.api.platform.global.DestType;
import com.pig8.api.platform.global.ExtensionType;
import com.pig8.api.platform.global.UserRole;
import com.pig8.api.platform.util.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author navy
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageQueryRequest {
    public Integer id;//可以是各种对象的ID
    /**
     * 页面显示数目
     */
    public Integer numPerPage = null;

    /**
     * 页码
     */
    public Long pageNum = null;

    /**
     * 用户角色
     */
    public UserRole userRole = UserRole.USER;

    public String refUrl;

    public String orderNo;

    public List<Integer> journeyIdList;

    public int authUserId;

    public ExtensionType extensionType = ExtensionType.PC;

    public ExtensionType getExtensionType() {
        return extensionType;
    }

    public DestType destType;

    public Integer parentDestId;

    public Integer getParentDestId() {
        return parentDestId;
    }

    public void setParentDestId(Integer parentDestId) {
        this.parentDestId = parentDestId;
    }

    public void setExtensionType(int extensionType) {
        this.extensionType = ExtensionType.valueOf(extensionType);
    }

    public DestType getDestType() {
        return destType;
    }

    public void setDestType(int destType) {
        this.destType = DestType.valueOf(destType);
    }

    public int getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(int authUserId) {
        this.authUserId = authUserId;
    }

    public void addJourneyId(int id) {
        if (journeyIdList == null)
            journeyIdList = new ArrayList<Integer>();
        journeyIdList.add(id);
    }

    public List<Integer> getJourneyIdList() {
        return journeyIdList;
    }

    public void setJourneyIdList(List<Integer> journeyIdList) {
        this.journeyIdList = journeyIdList;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<Integer> userIdList;

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }

    public Map getQueryMap() {
        Field[] fields = PageQueryRequest.class.getFields();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for (Field field : fields) {
                map.put(field.getName(), field.get(this));
            }
        } catch (Exception e) {
            LogUtils.error(e);
        } finally {

        }
        return map;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = UserRole.valueOf(userRole);
    }

    public void setRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public PageQueryRequest() {

    }

    public PageQueryRequest(int numPerPage, long pageNum) {
        this.numPerPage = numPerPage;
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage == null || numPerPage == 0 ? Constants.DEFAULT_PAGE_SIZE : numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }



}
