package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	double generateGaji(PegawaiModel pegawai);
	PegawaiModel generateMaxAge(List<PegawaiModel> listPegawai);
	PegawaiModel generateMinAge(List<PegawaiModel> listPegawai);
}
