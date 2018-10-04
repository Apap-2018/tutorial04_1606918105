package com.apap.tutorial4.service;
import java.util.List;
import java.util.Optional;
import com.apap.tutorial4.model.DealerModel;
/*
 * kalau optional<> harus di tambah .get() buat dapet model dealernya
 */
public interface DealerService {
	Optional<DealerModel> getDealerDetailById(Long id);
	
	void addDealer(DealerModel dealer);
	
	void  deleteDealer(DealerModel dealer);
	
	List<DealerModel> getAllDealer();
	
	void updateDealerForm(Long id, String alamat, String noTelp);
}
