
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Zervice;

@Component
@Transactional
public class ZerviceToStringConverter implements Converter<Zervice, String> {

	@Override
	public String convert(final Zervice zervice) {
		String result;

		if (zervice == null) {
			result = null;
		} else {
			result = String.valueOf(zervice.getId());
		}

		return result;
	}

}
