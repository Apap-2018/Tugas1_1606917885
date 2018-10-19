package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping(value="/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		List<InstansiModel> listInstansi = instansiService.getListInstansi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("title", "Home");
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method=RequestMethod.GET)
	private String viewPegawai(@RequestParam ("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		double gaji = pegawaiService.generateGaji(pegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", "Rp" + (int)gaji);
		model.addAttribute("title", "Detail Pegawai");
		return "detail-pegawai";
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String viewPegawaiTermudaTertua(@RequestParam ("id") long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id);
		List<PegawaiModel> listPegawai = instansi.getListPegawai();
		PegawaiModel pegawaiTermuda = pegawaiService.generateMinAge(listPegawai);
		double gajiTermuda = pegawaiService.generateGaji(pegawaiTermuda);
		PegawaiModel pegawaiTertua = pegawaiService.generateMaxAge(listPegawai);
		double gajiTertua = pegawaiService.generateGaji(pegawaiTertua);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("gajiTermuda", "Rp" + (int)gajiTermuda);
		model.addAttribute("gajiTertua", "Rp" + (int)gajiTertua);
		model.addAttribute("title", "Detail Pegawai");
		return "view-pegawai-muda-tua";
	}
}
