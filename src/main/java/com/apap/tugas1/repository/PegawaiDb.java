package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findByNip(String nip);
	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasukOrderByNipAsc(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
}