
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.ZerviceService;
import domain.Zervice;

@Component
@Transactional
public class StringToZerviceConverter implements Converter<String, Zervice> {

	@Autowired
	ZerviceService	zerviceService;


	@Override
	public Zervice convert(final String text) {
		Zervice result;
		int id;
		if (text == "") {
			result = null;
		} else {
			try {
				id = Integer.valueOf(text);
				result = zerviceService.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		}
		return result;
	}

}
