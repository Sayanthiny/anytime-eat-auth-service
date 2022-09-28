package it.itwtech.ateauth.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.dto.MailPropertiesDto;
import it.itwtech.ateauth.model.MailProperties;
import it.itwtech.ateauth.repositories.MailPropertiesRepository;

@Service
public class MailPropertiesServicelmpl implements MailPropertiesService {
	@Autowired
	private MailPropertiesRepository mailPropertiesRepository;

	@Transactional
	public void saveMailProperties(MailProperties mailProperties) {
		mailPropertiesRepository.save(mailProperties);

	}

	@Transactional
	public boolean ismailPropertiesExists(Long id) {
		return mailPropertiesRepository.existsById(id);
	}

	@Transactional
	public void deleteMailProperties(Long id) {
		mailPropertiesRepository.deleteById(id);

	}

	@Transactional
	public boolean isEmailExists(String username) {
		return mailPropertiesRepository.existsByUsernameIgnoreCase(username);
	}

	@Transactional
	public Long getTotalCountMailProperties() {
		return mailPropertiesRepository.count();
	}

	@Transactional
	public List<MailProperties> getAllMailProperties(Pageable pageble) {
		return mailPropertiesRepository.findAll(pageble).toList();
	}

	@Transactional
	public MailProperties getMailPropertiesById(Long id) {
		return mailPropertiesRepository.findById(id).get();
	}

	@Transactional
	public boolean isUpdateEmail(MailPropertiesDto mailPropertiesDto) {
		return !getMailPropertiesById(mailPropertiesDto.getId()).getUsername()
				.equalsIgnoreCase(mailPropertiesDto.getUsername()) && isEmailExists(mailPropertiesDto.getUsername());
	}

	@Transactional
	public boolean isStatusTrue(MailPropertiesDto mailPropertiesDto) {
		return !getMailPropertiesById(mailPropertiesDto.getId()).isActive()
				&& mailPropertiesRepository.existsByActive(true);

	}
}
