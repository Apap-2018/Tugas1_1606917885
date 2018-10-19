package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;

public interface InstansiService {
	public List<InstansiModel> getListInstansi();
	InstansiModel getInstansiDetailById(long id);
}
