package gov.nih.nci.bda.domain;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * Contains the status of the practice, e.g. Single Button Deployment, and associated details, if any.
 */
public class Practice {
    private URL url;
    private String alternateText;

    public Practice(PracticeStatus status) {
        
    }

    public Practice(PracticeStatus status, String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public URL getUrl() {
        return url;
    }

    public String getAlternateText() {
        return alternateText;
    }
}
