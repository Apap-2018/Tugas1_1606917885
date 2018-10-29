package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	void addPegawai(PegawaiModel pegawai);
	List<PegawaiModel> getListPegawai();
	PegawaiModel getPegawaiDetailByNip(String nip);
	String generateNip(PegawaiModel pegawai);
	double generateGaji(PegawaiModel pegawai);
	PegawaiModel generateMaxAge(List<PegawaiModel> listPegawai);
	PegawaiModel generateMinAge(List<PegawaiModel> listPegawai);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
}
