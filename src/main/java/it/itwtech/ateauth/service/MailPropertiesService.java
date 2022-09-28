package it.itwtech.ateauth.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import it.itwtech.ateauth.dto.MailPropertiesDto;
import it.itwtech.ateauth.model.MailProperties;

public interface MailPropertiesService {
	public void saveMailProperties(MailProperties mailProperties);

	public boolean ismailPropertiesExists(Long id);

	public void deleteMailProperties(Long id);

	public boolean isEmailExists(String username);

	public Long getTotalCountMailProperties();

	public List<MailProperties> getAllMailProperties(Pageable pageble);

	public boolean isUpdateEmail(MailPropertiesDto mailPropertiesDto);

	public boolean isStatusTrue(MailPropertiesDto mailPropertiesDto);
}
