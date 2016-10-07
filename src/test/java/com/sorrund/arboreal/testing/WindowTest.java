package com.sorrund.arboreal.testing;

import org.junit.Test;
import static org.junit.Assert.*;

import com.sorrund.arboreal.engine.Window;

public class WindowTest {
	@Test
	public void testHeight() {
		Window w = new Window("Title", 800, 600, false);
		assertTrue(w.getWidth() == 800);
	}
}
