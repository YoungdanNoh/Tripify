package com.trip.place.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.place.vo.Places;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;

@Mapper
public interface placeMapper {

	public ArrayList<Type> typeSelect() throws Exception;
	
	List<SidoGugunCode> getSido() throws SQLException;
	List<SidoGugunCode> getGugunInSido(String sido) throws SQLException;
	

	public List<Places> listAttr(Map<String, Object> param) throws Exception;

	public int getTotalAttrCount(Map<String, Object> param) throws Exception;
}
