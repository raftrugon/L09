
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.UserService;
import domain.User;

@Component
@Transactional
public class StringToUserConverter implements Converter<String, User> {

	@Autowired
	UserService	userService;


	@Override
	public User convert(final String text) {
		User result;
		int id;
		if (text == "") {
			result = null;
		} else {
			try {
				id = Integer.valueOf(text);
				result = userService.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		}
		return result;
	}

}
