package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	public void addJabatan(JabatanModel jabatan);
	public JabatanModel getJabatanDetailById(long id);
	public List<JabatanModel> getListJabatan();
	public void deleteJabatan(long id);
	public int calculatePegawai();
}