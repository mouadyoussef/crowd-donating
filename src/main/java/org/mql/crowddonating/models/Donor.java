package org.mql.crowddonating.models;


import java.util.List;
import java.util.Vector;

import javax.persistence.*;

@Entity
public class Donor extends User {

    @Column
    private String phone;

    @Column
    private String address;
    
    @OneToMany(mappedBy = "donor", fetch = FetchType.LAZY)
    private List<BankCard> bankCards;

    public Donor() {
    	bankCards = new Vector<>();
    }

    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BankCard> getBankCards() {
		return bankCards;
	}
    
    public void setBankCards(List<BankCard> bankCards) {
		this.bankCards = bankCards;
	}
    
    public void addBankCard(BankCard bankCard) {
    	bankCards.add(bankCard);
    }
  
	@Override
	public String toString() {
		return "Donor [ " + super.toString() + ", phone=" + phone + ", address=" + address + "]";
	}
    
    
    
}