package com.apap.tugas1.service;

import java.sql.Date;
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
	public List<PegawaiModel> getListPegawai() {
		return pegawaiDb.findAll();
	}


	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}
	
	@Override
public String generateNip(PegawaiModel pegawai) {
	   String kodeInstansi = String.valueOf(pegawai.getInstansi().getId());
	   String[] dateSplit = String.valueOf(pegawai.getTanggalLahir().toString()).split("-");
	   String tahun = dateSplit[0];
	   String bulan = dateSplit[1];
	   String tanggal = dateSplit[2];
	   tahun = tahun.substring(2, 4);
	   String tahunMasuk = pegawai.getTahunMasuk();
	   
	   // generate nomor urut
	   int noUrut;
	   List<PegawaiModel> listPegawai = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasukOrderByNipAsc(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
	   if(listPegawai.size() == 0) {
		   noUrut = 1;
	   }
	   else {
		   PegawaiModel lastPegawai = listPegawai.get(listPegawai.size() - 1);
		   noUrut =Integer.parseInt(lastPegawai.getNip().substring(14, 16)) + 1;
	   }
	   String noUrutStr;
	   if(noUrut == 0) {
		   noUrutStr = "01";
	   }
	   else if (noUrut < 10) {
		   noUrutStr = "0";
		   noUrutStr += String.valueOf(noUrut);
	   }
	   else {
		   noUrutStr = String.valueOf(noUrut);
	   }
	   // END generate nomor urut
	   String nip = kodeInstansi + tanggal + bulan + tahun + tahunMasuk + noUrutStr;
	   return nip;
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
				if(pegawai.getTanggalLahir().compareTo(pegawaiTertua.getTanggalLahir()) < 0)
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
				if(pegawai.getTanggalLahir().compareTo(pegawaiTermuda.getTanggalLahir()) > 0)
					pegawaiTermuda = pegawai;
			}
		}

		return pegawaiTermuda;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasukOrderByNipAsc(instansi, tanggalLahir, tahunMasuk);
	}
}