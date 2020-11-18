package com.abederrahmen.voitures.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.abederrahmen.voitures.entities.Voiture;
import com.abederrahmen.voitures.service.VoitureService;

@Controller
public class VoitureController {
	
	@Autowired
	VoitureService voituretService;
	@RequestMapping("/showCreate")
	public String showCreate()
	{
	return "ajoutVoiture";
	}
	@RequestMapping("/saveVoiture")
	public String saveVoiture(@ModelAttribute("voiture") Voiture voiture,
	@RequestParam("date") String date,
	ModelMap modelMap) throws ParseException
	{
	//conversion de la date
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	Date dateArrive = dateformat.parse(String.valueOf(date)); voiture.setDateArrive(dateArrive);
	Date dateSortie = dateformat.parse(String.valueOf(date)); voiture.setDateSortie(dateSortie);
	Voiture saveVoiture = voituretService.saveVoiture(voiture);
	String msg ="voiture enregistré avec Id "+saveVoiture.getIdVoiture();
	modelMap.addAttribute("msg", msg);
	return "ajoutVoiture";
	}
	
	
	
	@RequestMapping("/ListeVoitures")
	public String listeVoitures(ModelMap modelMap,@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "2") int size)
	{
		Page<Voiture> v = voituretService.getAllVoituresParPage(page, size);
		modelMap.addAttribute("voitures", v);
		modelMap.addAttribute("pages", new int[v.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeVoitures";
	}
	
	
	
	@RequestMapping("/supprimerVoiture")
	public String supprimerVoiture(@RequestParam("id") Long id,
	ModelMap modelMap,@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "2") int size)
	{
	voituretService.deleteVoitureById(id);
	Page<Voiture> v = voituretService.getAllVoituresParPage(page,
			size);
			modelMap.addAttribute("voitures", v);
			modelMap.addAttribute("pages", new int[v.getTotalPages()]);
			modelMap.addAttribute("currentPage", page);
			modelMap.addAttribute("size", size);
	return "listeVoitures";
	}
	
	
	
	@RequestMapping("/modifierVoiture")
	public String editerVoiture(@RequestParam("id") Long id,ModelMap modelMap)
	{
	Voiture v= voituretService.getVoiture(id);
	modelMap.addAttribute("voiture", v);
	return "ajoutVoiture";
	}
	
	
	@RequestMapping("/updateVoiture")
	public String updateVoiture(@ModelAttribute("voiture") Voiture voiture,
	@RequestParam("date") String date,ModelMap modelMap) throws ParseException
	{
		//conversion de la date 
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateArrive = dateformat.parse(String.valueOf(date));
		voiture.setDateArrive(dateArrive);
		Date dateSortie = dateformat.parse(String.valueOf(date));
		voiture.setDateSortie(dateSortie);
		voituretService.updateVoiture(voiture);
		List<Voiture> v = voituretService.getAllVoitures();
		modelMap.addAttribute("voiture", v);
		return "listeVoitures";
		}
	
	
}
