package com.trip.plans.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.plans.vo.PlanDetail;
import com.trip.plans.vo.Plans;

@Mapper
public interface plansMapper {
	
	public ArrayList<Plans> plansSelect(int userId) throws Exception;

	public void addPlan(Plans p) throws Exception;
	
	public void deletePlan(Plans p) throws Exception;

	public ArrayList<PlanDetail> getPlanDetails(Map<String, Object> params);

	public void addDetailPlan(PlanDetail p) throws Exception;

	public void addOrder(PlanDetail p) throws Exception;

	public void addNewActivity(PlanDetail p) throws Exception;
	
	public void updateActivity(PlanDetail p);
	
	public void deleteActivity(PlanDetail p) throws Exception;

}
