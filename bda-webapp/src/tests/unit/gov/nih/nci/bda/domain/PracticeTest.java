package gov.nih.nci.bda.domain;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.net.MalformedURLException;

public class PracticeTest {

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
        String expected = "http://stelligent.com";
        Practice p = new Practice(PracticeStatus.DEFERRED, expected);
        assertEquals(expected, p.getUrl().toString());
    }

    @Test (expected = MalformedURLException.class)
    public void shouldBombWhenBadUrlProvided() throws MalformedURLException {
        String badUrl = "bad";
        new Practice(PracticeStatus.DEFERRED, badUrl);
    }
}
