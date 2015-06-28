package com.pig8.api.platform.interceptor;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author navy
 *
 */

public class LoggerInterceptor implements MethodInterceptor, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6309260523884030779L;
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
	
	/**
	 * 拦截方法
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			String before = getInvocationParamDescription( invocation);
			logger.info(before);
			Object rval = invocation.proceed();				
			String after = getInvocationResultDescription( invocation, rval);
			logger.info(after);
			return rval;
		}
		catch (Throwable ex) {
			String error= "调用" + getInvocationDescription(invocation)+"时出错!";
			logger.error(error , ex);
			throw ex;
		}
	}
	
	private String getInvocationDescription(MethodInvocation invocation){
		return "类 "+invocation.getThis().getClass().getName()+" 方法 " + invocation.getMethod().getName() ;
	}
	
	private String getInvocationParamDescription(MethodInvocation invocation){
		Object[] args = invocation.getArguments();
		String par=" 参数类型[";
		if(args!=null){
			for(int i=0; i<args.length; i++ ){
				if(i!=0){
					par+=",";
				}
				par += args[i]==null?"null":args[i].getClass().getName();
				
			}
		}
		par+="]";
		
		par +=" 参数[";
		if(args!=null){
			for(int i=0; i<args.length; i++ ){
				if(i!=0){
					par+=",";
				}
				par += args[i]==null?"null":args[i];
				
			}
		}
		par+="]";
		return "调用 "+getInvocationDescription(invocation)+par;
	}
	
	private String getInvocationResultDescription(MethodInvocation invocation, Object rval){
		String par=" 返回值类型["+(rval==null?"null":rval.getClass().getName())+"]";
		par +=" 返回值["+(rval==null?"null":rval)+"]";
		return "调用 "+getInvocationDescription(invocation)+par;
	}
}