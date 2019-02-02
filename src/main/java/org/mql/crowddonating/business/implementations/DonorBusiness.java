package org.mql.crowddonating.business.implementations;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import javax.transaction.Transactional;

import org.mql.crowddonating.business.IDonorBusiness;
import org.mql.crowddonating.dao.BankCardRepository;
import org.mql.crowddonating.dao.DonationRepository;
import org.mql.crowddonating.models.BankCard;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.File;
import org.mql.crowddonating.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DonorBusiness extends UserBusiness implements IDonorBusiness {

	@Autowired
	private BankCardRepository cardDao;
	
	@Autowired
	private DonationRepository donationDao;
	
	@Override
	public BankCard addBankCard(BankCard bankCard) {
		return cardDao.save(bankCard);
	}

	@Override
	public void deleteBankCard(long id) {
		 cardDao.delete(cardDao.getOne(id));
	}

	@Override
	public BankCard updatebankCard(BankCard bankCrd) {
		return cardDao.save(bankCrd);
	}

	@Override
	public Donation addDonation(Donation donation) {
		return donationDao.save(donation);
	}


    @Override
	public List<BankCard> getAllByDonor(String userName) {
		return null;
	}

	@Override
	public BankCard getCardById(long id) {
		return cardDao.getOne(id);
	}
}