package com.trip.plans.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.plans.vo.PlanDetail;
import com.trip.plans.vo.Plans;

@Mapper
public interface plansMapper {
	
	public ArrayList<Plans> plansSelect(int userId) throws Exception;

	public void insertPlan(Plans plan);

	public ArrayList<PlanDetail> getPlanDetails(Map<String, Object> params);

}
