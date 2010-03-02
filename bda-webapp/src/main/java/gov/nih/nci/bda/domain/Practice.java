package gov.nih.nci.bda.domain;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * Contains the status of the practice, e.g. Single Button Deployment, and associated details, if any.
 */
public class Practice {
    private URL url;
    private String alternateText;
    private final PracticeStatus status;

    public Practice(PracticeStatus status) {
        this.status = status;
    }

    public Practice(PracticeStatus status, String url) throws MalformedURLException {
        this(status);
        this.url = new URL(url);
    }

    public Practice(PracticeStatus status, String url, String alternateText) throws MalformedURLException {
        this(status, url);
        this.alternateText = alternateText;
    }

    public URL getUrl() {
        return url;
    }

    public String getAlternateText() {
        return alternateText;
    }

    public PracticeStatus getStatus() {
        return status;
    }
}
