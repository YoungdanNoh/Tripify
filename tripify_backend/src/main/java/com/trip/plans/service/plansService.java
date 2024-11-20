package com.trip.plans.service;

import java.util.ArrayList;
import java.util.Map;

import com.trip.plans.vo.PlanDetail;
import com.trip.plans.vo.Plans;

public interface plansService {
	
	public ArrayList<Plans> plansSelect(int userId) throws Exception;
	public void insertPlan(Plans plan) throws Exception;
	public ArrayList<PlanDetail> getPlanDetails(Map<String, Object> params);
}
