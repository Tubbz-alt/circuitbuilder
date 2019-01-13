package fwcd.circuitbuilder.model.utils;

import static org.junit.Assert.assertThat;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

public class ValueOverTimeTest {
	@Test
	public void testValueOverTime() {
		ValueOverTime vot = new ValueOverTime("VOT", 4);
		vot.add(true);
		vot.add(true);
		vot.add(false);
		assertThat(vot, votEquals(false, true, true));
		
		vot.add(false);
		vot.add(true);
		assertThat(vot, votEquals(true, false, false, true));
	}
	
	private Matcher<ValueOverTime> votEquals(Boolean... expected) {
		return new BaseMatcher<ValueOverTime>() {
			@Override
			public boolean matches(Object item) {
				ValueOverTime actual = (ValueOverTime) item;
				if (actual.getValueCount() != expected.length) {
					return false;
				}
				for (int i = 0; i < expected.length; i++) {
					if (expected[i] != actual.get(i)) {
						return false;
					}
				}
				return true;
			}
			
			@Override
			public void describeTo(Description description) {
				description
					.appendText("ValueOverTime should equal")
					.appendValueList("[", ",", "]", expected);
			}
		};
	}
}
