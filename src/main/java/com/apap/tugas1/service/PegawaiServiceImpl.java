package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public double generateGaji(PegawaiModel pegawai) {
		double maxGajiPokok = 0;
		for (JabatanModel jabatan: pegawai.getJabatan()) {
			if(jabatan.getGaji_pokok() > maxGajiPokok) maxGajiPokok = jabatan.getGaji_pokok(); 
		}
		InstansiModel instansi = pegawai.getInstansi();
		ProvinsiModel provinsi = instansi.getProvinsi();
		double presentase_tunjangan = provinsi.getPresentase_tunjangan();
		
		double gaji = maxGajiPokok + (presentase_tunjangan/100*maxGajiPokok);
		return gaji;
	}

	@Override
	public PegawaiModel generateMaxAge(List<PegawaiModel> listPegawai) {
		PegawaiModel pegawaiTertua = null;
		for(PegawaiModel pegawai: listPegawai) {
			if(pegawaiTertua == null) {
				pegawaiTertua = pegawai;
			}
			else {
				if(pegawai.getTanggal_lahir().compareTo(pegawaiTertua.getTanggal_lahir()) < 0)
					pegawaiTertua = pegawai;
			}
		}

		return pegawaiTertua;
	}

	@Override
	public PegawaiModel generateMinAge(List<PegawaiModel> listPegawai) {
		PegawaiModel pegawaiTermuda = null;
		for(PegawaiModel pegawai: listPegawai) {
			if(pegawaiTermuda == null) {
				pegawaiTermuda = pegawai;
			}
			else {
				if(pegawai.getTanggal_lahir().compareTo(pegawaiTermuda.getTanggal_lahir()) > 0)
					pegawaiTermuda = pegawai;
			}
		}

		return pegawaiTermuda;
	}
}