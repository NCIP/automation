package gov.nih.nci.bda.domain;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.net.MalformedURLException;

public class PracticeTest {
    private static final String GOOD_URL = "http://stelligent.com";
    private static final String BAD_URL = "bad";

    @Test
    public void shouldHaveNullUrlWhenNoneProvided() {
        Practice p = new Practice(PracticeStatus.DEFERRED);
        assertNull(p.getUrl());
    }

    @Test
    public void shouldHaveNullAlternateTextWhenNoneProvided() {
        Practice p = new Practice(PracticeStatus.DEFERRED);
        assertNull(p.getAlternateText());
    }

    @Test
    public void shouldHaveSameUrlWhenProvided() throws MalformedURLException {
        Practice p = new Practice(PracticeStatus.DEFERRED, GOOD_URL);
        assertEquals(GOOD_URL, p.getUrl().toString());
    }

    @Test (expected = MalformedURLException.class)
    public void shouldBombWhenBadUrlProvided() throws MalformedURLException {
        new Practice(PracticeStatus.DEFERRED, BAD_URL);
    }

    @Test (expected = MalformedURLException.class)
    public void shouldBombWhenBadUrlProvidedBothConstructors() throws MalformedURLException {
        new Practice(PracticeStatus.DEFERRED, BAD_URL, "Alternate text");
    }

    @Test
    public void shouldHaveSameAlternateTextWhenProvided() throws MalformedURLException {
        String expected = "This is some alternate text.";
        Practice p = new Practice(PracticeStatus.DEFERRED, GOOD_URL, expected);
        assertEquals(expected, p.getAlternateText());
    }
}
