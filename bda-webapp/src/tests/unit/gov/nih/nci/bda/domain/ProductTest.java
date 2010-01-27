package gov.nih.nci.bda.domain;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class ProductTest {

    @Test
    public void shouldNeverHaveNullPracticeWhenOtherwiseEmptyProduct() {
        Product blank = new Product();
        assertDefaultPracticeValue(blank.getCertificationStatus());
        assertDefaultPracticeValue(blank.getBdaEnabled());
        assertDefaultPracticeValue(blank.getSingleCommandBuild());
        assertDefaultPracticeValue(blank.getSingleCommandDeploy());
        assertDefaultPracticeValue(blank.getRemoteUpgrade());
        assertDefaultPracticeValue(blank.getDbIntegration());
        assertDefaultPracticeValue(blank.getPrivateProperties());
        assertDefaultPracticeValue(blank.getDeploymentShakeout());
        assertDefaultPracticeValue(blank.getTemplateValidation());
        assertDefaultPracticeValue(blank.getCiBuild());
        assertDefaultPracticeValue(blank.getCommandLineInstall());
    }

    @Test
    public void shouldAlwaysGetSameValueWhenSet() {
        Product p = new Product();

        PracticeStatus status = PracticeStatus.random();
        p.setBdaEnabled(status);
        assertEquals(status, p.getBdaEnabled());

        status = PracticeStatus.random();
        p.setCertificationStatus(status);
        assertEquals(status, p.getCertificationStatus());

        status = PracticeStatus.random();
        p.setCiBuild(status);
        assertEquals(status, p.getCiBuild());

        status = PracticeStatus.random();
        p.setCommandLineInstall(status);
        assertEquals(status, p.getCommandLineInstall());

        status = PracticeStatus.random();
        p.setDbIntegration(status);
        assertEquals(status, p.getDbIntegration());

        status = PracticeStatus.random();
        p.setDeploymentShakeout(status);
        assertEquals(status, p.getDeploymentShakeout());

        status = PracticeStatus.random();
        p.setPrivateProperties(status);
        assertEquals(status, p.getPrivateProperties());

        status = PracticeStatus.random();
        p.setRemoteUpgrade(status);
        assertEquals(status, p.getRemoteUpgrade());

        status = PracticeStatus.random();
        p.setSingleCommandBuild(status);
        assertEquals(status, p.getSingleCommandBuild());

        status = PracticeStatus.random();
        p.setSingleCommandDeploy(status);
        assertEquals(status, p.getSingleCommandDeploy());

        status = PracticeStatus.random();
        p.setTemplateValidation(status);
        assertEquals(status, p.getTemplateValidation());
    }

    private void assertDefaultPracticeValue(PracticeStatus status) {
        assertEquals("Expected the default practice", PracticeStatus.NOT_SUCCESSFUL, status);
    }
}
