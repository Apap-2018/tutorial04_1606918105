package com.apap.tutorial4.controller;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.apap.tutorial4.service.*;
import com.apap.tutorial4.model.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value="/dealer/add")
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	/*
	 * response body awalnya mengembalikan dealermodel 
	 */
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String  showDealerCar(@RequestParam("dealerId") long dealerId, Model model) {
		//return dealerService.getDealerDetailById(dealerId).get() untuk mengambil keseluruhan isi model dealer / id 
		List<CarModel> listDealerCar = dealerService.getDealerDetailById(dealerId).get().getListCar();
		Collections.sort(listDealerCar, new SortbyPrice());
		DealerModel getDealerModel = dealerService.getDealerDetailById(dealerId).get();
		long idAddCar = dealerId;
		model.addAttribute("dealer",getDealerModel);
		model.addAttribute("idDealer",idAddCar);
		model.addAttribute("listCar", listDealerCar);
		return "view-dealer";	 
	}
	@RequestMapping(value = "/dealer/viewall", method = RequestMethod.GET)
	private String viewDealer(Model model) {
		List<DealerModel> alldealer = dealerService.getAllDealer();
		for(int i= 0; i<alldealer.size();i++) {
			List<CarModel> listCar  = alldealer.get(i).getListCar();
			Collections.sort(listCar, new SortbyPrice());
		}
		model.addAttribute("listAlldealer",alldealer);
		return "viewall";
	}
	

	
	@RequestMapping(value = {"/dealer/delete/{id}"})
	public String deleteDealer(@PathVariable("id") Long id, Model mode) {
		DealerModel soondelete = dealerService.getDealerDetailById(id).get();
		dealerService.deleteDealer(soondelete);
		return "delete-Dealer";
	}
	
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String updateDealer(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		model.addAttribute("datadealer", new DealerModel());
		return "updateFormDealer";
		
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.POST)
	private String updateDealer(@PathVariable(value = "dealerId") Long dealerId,@ModelAttribute DealerModel dealer,Model model) {
		dealerService.updateDealerForm(dealerId, dealer.getAlamat(), dealer.getNoTelp());
		return "update-berhasil";
	}
	
	

	//untuk menampilkan data dengan cara terurut sesuai dengan harganya
	class SortbyPrice implements Comparator<CarModel> { 
	    
	    public int compare(CarModel a, CarModel b) 
	    { 
	        return a.getPrice() - b.getPrice(); 
	    } 
	}
	
	
	
	
	
	
}
