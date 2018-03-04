
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.CommentService;
import domain.Comment;

@Component
@Transactional
public class StringToCommentConverter implements Converter<String, Comment> {

	@Autowired
	CommentService	commentService;


	@Override
	public Comment convert(final String text) {
		Comment result;
		int id;
		if (text == "") {
			result = null;
		} else {
			try {
				id = Integer.valueOf(text);
				result = commentService.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		}
		return result;
	}

}
