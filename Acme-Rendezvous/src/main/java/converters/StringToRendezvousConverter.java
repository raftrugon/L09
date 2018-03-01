
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.RendezvousService;
import domain.Rendezvous;

@Component
@Transactional
public class StringToRendezvousConverter implements Converter<String, Rendezvous> {

	@Autowired
	RendezvousService	rendezvousService;


	@Override
	public Rendezvous convert(final String text) {
		Rendezvous result;
		int id;
		if (text == "") {
			result = null;
		} else {
			try {
				id = Integer.valueOf(text);
				result = rendezvousService.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		}
		return result;
	}

}
