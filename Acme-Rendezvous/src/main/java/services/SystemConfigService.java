package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigRepository;
import domain.SystemConfig;

@Service
@Transactional
public class SystemConfigService {

	// Managed repository -----------------------------------------------------

		@Autowired
		private SystemConfigRepository	systemConfigRepository;

		// Supporting Services ----------------------------------------------------

		@Autowired
		private AdminService			adminService;


		// Simple CRUD methods ----------------------------------------------------

		public SystemConfig get() {
			return systemConfigRepository.findAll().get(0);
		}


		public SystemConfig save(final SystemConfig systemConfig) {
			Assert.notNull(systemConfig);
			Assert.notNull(adminService.findByPrincipal());
			Assert.isTrue(systemConfig.getId()==get().getId());
			return systemConfigRepository.save(systemConfig);
		}


		//Other Business Methods --------------------------------

}
