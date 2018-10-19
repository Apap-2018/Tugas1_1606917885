package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("title", "Sukses");
		model.addAttribute("msg", "Jabatan berhasil ditambahkan");
		return "success";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.GET)
	private String addJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("title", "Tambah Jabatan");
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	private String viewJabatan(@RequestParam(value="id", required=true) long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		List<PegawaiModel> listPegawai = jabatan.getListPegawai();
		int jumlahPegawai = listPegawai.size();
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("jumlahPegawai", jumlahPegawai);
		model.addAttribute("title", "Detail Jabatan");
		return "detail-jabatan";
	}
	
	@RequestMapping(value="/jabatan/viewall", method=RequestMethod.GET)
	private String viewAllJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Lihat Semua Jabatan");
		return "view-all-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	private String updateJabatan(@RequestParam(value="id", required=true) long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("title", "Ubah Jabatan");
		return "update-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("title", "Sukses");
		model.addAttribute("msg", "Jabatan berhasil diubah");
		return "success";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	private String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		System.out.println(jabatan.getListPegawai());
		if(jabatan.getListPegawai().isEmpty()) {
			jabatanService.deleteJabatan(jabatan.getId());
			model.addAttribute("title", "Sukses");
			model.addAttribute("msg", "Jabatan berhasil dihapus");
			return "success";
		}
		else {
			model.addAttribute("title", "Gagal");
			model.addAttribute("msg", "Jabatan tidak dapat dihapus");
			return "failed";
		}
	}
}
