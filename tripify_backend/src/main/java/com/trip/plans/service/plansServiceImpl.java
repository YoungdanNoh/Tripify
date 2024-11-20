package com.trip.plans.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.plans.mapper.plansMapper;
import com.trip.plans.vo.PlanDetail;
import com.trip.plans.vo.Plans;

@Service
public class plansServiceImpl implements plansService {

	@Autowired
	plansMapper mapper;
	
	@Override
	public ArrayList<Plans> plansSelect(int userId) throws Exception {
		return mapper.plansSelect(userId);
	}

	@Override
	public void insertPlan(Plans plan) throws Exception {
		mapper.insertPlan(plan);
	}

	@Override
	public ArrayList<PlanDetail> getPlanDetails(Map<String, Object> params) {
		return mapper.getPlanDetails(params);
	}
}
