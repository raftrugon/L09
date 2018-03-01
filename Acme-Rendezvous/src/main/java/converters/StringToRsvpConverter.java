
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.RsvpService;
import domain.Rsvp;

@Component
@Transactional
public class StringToRsvpConverter implements Converter<String, Rsvp> {

	@Autowired
	RsvpService	rsvpService;


	@Override
	public Rsvp convert(final String text) {
		Rsvp result;
		int id;
		if (text == "") {
			result = null;
		} else {
			try {
				id = Integer.valueOf(text);
				result = rsvpService.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		}
		return result;
	}

}
