package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
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
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		ArrayList<JabatanModel> jabatanPegawai = new ArrayList<JabatanModel>();
		pegawai.setJabatan(jabatanPegawai);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		
		model.addAttribute("title", "Tambah Pegawai");
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, @RequestParam(value = "idProvinsi") long idProvinsi, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(pegawai.getInstansi().getId());
		pegawai.setInstansi(instansi);
		
		String nip = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nip);
		
		List<JabatanModel> listJabatan = pegawai.getJabatan();
		JabatanModel jabatan;
		long idJabatan;
		for(int i = 0; i < listJabatan.size(); i++) {
			idJabatan = listJabatan.get(i).getId();
			jabatan = jabatanService.getJabatanDetailById(idJabatan);
			listJabatan.set(i, jabatan);
		}
		
		pegawai.setJabatan(listJabatan);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai",pegawai);
		model.addAttribute("title", "Sukses");
		model.addAttribute("msg", "Pegawai dengan NIP " + pegawai.getNip() + " berhasil ditambahkan");
		return "success";
	}

	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.GET)
	private String updatePegawai(@RequestParam(value="nip", required=true) String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listInstansi", instansiService.getListInstansi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Ubah Pegawai");
		return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("title", "Sukses");
		model.addAttribute("msg", "Pegawai dengan NIP " + pegawai.getNip() + " berhasil diubah");
		return "success";
	}
	
	@RequestMapping(value= "/pegawai/cari", method=RequestMethod.GET)
	private String findPegawai(
			@RequestParam(value="idProvinsi", required = false) String idProvinsi,
			@RequestParam(value="idInstansi", required = false) String idInstansi,
			@RequestParam(value="idJabatan", required = false) String idJabatan,
			Model model) {
		
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listInstansi", instansiService.getListInstansi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		List<PegawaiModel> listPegawai = pegawaiService.getListPegawai();
		
		if ((idProvinsi==null || idProvinsi.equals("")) && (idInstansi==null||idInstansi.equals("")) && (idJabatan==null||idJabatan.equals(""))) {
			return "find-pegawaii";
		}
		else {
			if (idProvinsi!=null && !idProvinsi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel pegawai: listPegawai) {
					if (((Long)pegawai.getInstansi().getProvinsi().getId()).toString().equals(idProvinsi)) {
						temp.add(pegawai);
					}
				}
				listPegawai = temp;
				model.addAttribute("idProvinsi", Long.parseLong(idProvinsi));
			}
			else {
				model.addAttribute("idProvinsi", "");
			}
			if (idInstansi!=null&&!idInstansi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel pegawai: listPegawai) {
					if (((Long)pegawai.getInstansi().getId()).toString().equals(idInstansi)) {
						temp.add(pegawai);
					}
				}
				listPegawai = temp;
				model.addAttribute("idInstansi", Long.parseLong(idInstansi));
			}
			else {
				model.addAttribute("idInstansi", "");
			}
			if (idJabatan!=null&&!idJabatan.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel pegawai: listPegawai) {
					for (JabatanModel jabatan:pegawai.getJabatan()) {
						if (((Long)jabatan.getId()).toString().equals(idJabatan)) {
							temp.add(pegawai);
							break;
						}
					}
					
				}
				listPegawai = temp;
				model.addAttribute("idJabatan", Long.parseLong(idJabatan));
			}
			else {
				model.addAttribute("idJabatan", "");
			}
		}
		model.addAttribute("listPegawai",listPegawai);
		model.addAttribute("title", "Cari Pegawai");
		return "find-pegawaii";
		
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String viewPegawaiTermudaTertua(@RequestParam ("id") long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id);
		List<PegawaiModel> listPegawai = instansi.getListPegawai();
		PegawaiModel pegawaiTermuda = pegawaiService.generateMinAge(listPegawai);
		PegawaiModel pegawaiTertua = pegawaiService.generateMaxAge(listPegawai);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("title", "Detail Pegawai");
		return "view-pegawai-muda-tua";
	}
	
	@RequestMapping(value = "/generateInstansi", method = RequestMethod.GET)
	 public @ResponseBody List<InstansiModel> generateInstansi(@RequestParam(value = "idProvinsi", required = true) long idProvinsi) {
	 ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(idProvinsi);
	 List<InstansiModel> listInstansi = provinsi.getListInstansi();
	 
	  for(int i = 0; i < listInstansi.size(); i++) {
	   listInstansi.get(i).setListPegawai(null);
	   listInstansi.get(i).setProvinsi(null);
	  }
	  
	 return listInstansi;
	 }

}
