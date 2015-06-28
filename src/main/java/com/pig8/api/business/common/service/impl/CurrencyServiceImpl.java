/**
 * 
 */
package com.pig8.api.business.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pig8.api.business.common.entity.response.CommonResponse;
import com.pig8.api.business.common.service.CurrencyService;
import com.pig8.api.platform.dao.BaseDaoSupport;
import com.pig8.api.platform.global.ReturnEnum;

/**
 * @author navy
 *
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {
	
	@Resource
	private BaseDaoSupport baseDaoSupport;

	/* (non-Javadoc)
	 * @see com.pig8.api.business.common.service.CurrencyService#currencyList()
	 */
	public CommonResponse currencyList() {
		List<Map<String, Object>> currencyList = baseDaoSupport.find("currencyList");
		
		return new CommonResponse(ReturnEnum.SUCCESS.getReturnCode(), ReturnEnum.SUCCESS.getReturnMsg(), currencyList);
	}

}
