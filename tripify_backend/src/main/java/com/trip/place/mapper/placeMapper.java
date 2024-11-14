package com.trip.place.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.place.vo.Places;
import com.trip.place.vo.Siguns;
import com.trip.place.vo.Type;

@Mapper
public interface placeMapper {
	
	public ArrayList<Siguns> sigunSelect() throws Exception;

	public ArrayList<Type> typeSelect() throws Exception;

	public List<Places> listAttr(Map<String, Object> param) throws Exception;

	public int getTotalAttrCount(Map<String, Object> param) throws Exception;
}
