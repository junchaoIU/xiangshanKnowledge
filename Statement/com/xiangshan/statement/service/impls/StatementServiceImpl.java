package com.xiangshan.statement.service.impls;


import com.xiangshan.data.interfaces.Data;
import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.ontology.models.Statement;
import com.xiangshan.statement.service.interfaces.StatementService;
import com.xiangshan.utils.DataUtil;
import com.xiangshan.utils.JsonUtil;

/**
 * 处理三元组的Service类。
 * 
 * @author Rosahen
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class StatementServiceImpl extends BaseService implements StatementService{

	@Override
	public String processAsVisualData(String subject, String predicate, String object) {
		// TODO Auto-generated method stub
		
		Statement statement = new Statement(subject, predicate, object);
		
		statement.setSubjectClassName(getOntologyResolver().getClassName(subject));
		statement.setObjectClassName(getOntologyResolver().getClassName(object));
		statement.setPredicateClassName(getOntologyResolver().getClassName(predicate));
		
		Data data = DataUtil.transferStatementToData(statement);
		
		String result = JsonUtil.formatAsJson(data);
		
		return result;
	}
	
}
