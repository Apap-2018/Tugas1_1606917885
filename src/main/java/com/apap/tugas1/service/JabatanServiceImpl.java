package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	@Override
	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDb.findById(id);
	}

	@Override
	public List<JabatanModel> getListJabatan() {
		return jabatanDb.findAll();
	}

	@Override
	public void deleteJabatan(long id) {
		jabatanDb.deleteById(id);
	}

	@Override
	public int calculatePegawai() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}