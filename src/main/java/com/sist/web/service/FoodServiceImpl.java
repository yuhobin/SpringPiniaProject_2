package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.FoodMapper;

import lombok.RequiredArgsConstructor;
import java.util.*;
import com.sist.web.vo.*;
import com.sist.web.mapper.*;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService{
	private final FoodMapper fMapper;

	@Override
	public List<FoodVO> foodListData(int page) {
		// TODO Auto-generated method stub
		return fMapper.foodListData(page);
	}

	@Override
	public int foodTotalPage() {
		// TODO Auto-generated method stub
		return fMapper.foodTotalPage();
	}

	@Override
	public FoodVO foodDetailData(int no) {
		// TODO Auto-generated method stub
		return fMapper.foodDetailData(no);
	}

	@Override
	public int[] foodPages(int page) {
		// TODO Auto-generated method stub
		return null;
	}
}
