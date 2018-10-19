package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public List<InstansiModel> getListInstansi() {
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel getInstansiDetailById(long id) {
		return instansiDb.findById(id);
	}	
}