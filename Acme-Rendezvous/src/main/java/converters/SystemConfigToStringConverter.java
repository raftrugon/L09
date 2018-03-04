
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SystemConfig;

@Component
@Transactional
public class SystemConfigToStringConverter implements Converter<SystemConfig, String> {

	@Override
	public String convert(final SystemConfig systemConfig) {
		String result;

		if (systemConfig == null) {
			result = null;
		} else {
			result = String.valueOf(systemConfig.getId());
		}

		return result;
	}

}
