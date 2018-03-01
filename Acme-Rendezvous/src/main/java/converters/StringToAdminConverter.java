
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.AdminService;
import domain.Admin;

@Component
@Transactional
public class StringToAdminConverter implements Converter<String, Admin> {

	@Autowired
	AdminService	adminService;


	@Override
	public Admin convert(final String text) {
		Admin result;
		int id;
		if (text == "") {
			result = null;
		} else {
			try {
				id = Integer.valueOf(text);
				result = adminService.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		}
		return result;
	}

}
