package org.mql.crowddonating.controllers;


import org.mql.crowddonating.business.implementations.EmailSenderService;
import org.mql.crowddonating.dao.*;
import org.mql.crowddonating.models.ConfirmationToken;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class UserRegisterController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DonorRepository donorRepository;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView displayRegistration(ModelAndView modelAndView, Donor donor){
		modelAndView.addObject("donor", donor);
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerUser(ModelAndView modelAndView, Donor donor){
		
		User existingUser = userRepository.findByEmailIgnoreCase(donor.getEmail());
		if(existingUser != null)
		{
			modelAndView.addObject("message","This email already exists!");
			modelAndView.setViewName("error");
		}
		else 
		{
			donorRepository.save(donor);
			
			ConfirmationToken confirmationToken = new ConfirmationToken(donor);
			
			confirmationTokenRepository.save(confirmationToken);
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(donor.getEmail());
			mailMessage.setSubject("Complete Registration!");
			mailMessage.setFrom("onep.meknes2018@gmail.com");
			mailMessage.setText("To confirm your account, please click here : "
			+"http://localhost:8080/confirm-accoun/="+confirmationToken.getConfirmationToken());
			
			emailSenderService.sendEmail(mailMessage);
			
			modelAndView.addObject("emailId", donor.getEmail());
			
			modelAndView.setViewName("successfulRegisteration");
		}
		
		return modelAndView;
	}
	
	
	
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
	{
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		
		if(token != null)
		{
			User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
			user.setEnabled(true);
			userRepository.save(user);
			modelAndView.setViewName("accountVerified");
		}
		else
		{
			modelAndView.addObject("message","The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ConfirmationTokenRepository getConfirmationTokenRepository() {
		return confirmationTokenRepository;
	}

	public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

	public EmailSenderService getEmailSenderService() {
		return emailSenderService;
	}

	public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}
	
}
