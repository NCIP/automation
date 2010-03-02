package gov.nih.nci.bda.domain;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class ProductTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldBombWhenCreatingAProductWithNullName() {
        new Product(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBombWhenCreatingAProductWithEmptyName() {
        new Product("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBombWhenCreatingAProductWithBlankName() {
        new Product("   ");
    }

    @Test
    public void shouldGetTheNameAsSet() {
        assertEquals("Foo", new Product("Foo").getName());
    }

    @Test
    public void shouldAlwaysGetSameValueWhenSet() {
        Product p = new Product("TestName");

        PracticeStatus status;
        Practice practice = new Practice(PracticeStatus.random());
        p.setBdaEnabled(practice);
        assertEquals(practice, p.getBdaEnabled());

        practice = new Practice(PracticeStatus.random());
        p.setCertificationStatus(practice);
        assertEquals(practice, p.getCertificationStatus());

        practice = new Practice(PracticeStatus.random());
        p.setCiBuild(practice);
        assertEquals(practice, p.getCiBuild());

        practice = new Practice(PracticeStatus.random());
        p.setCommandLineInstall(practice);
        assertEquals(practice, p.getCommandLineInstall());

        practice = new Practice(PracticeStatus.random());
        p.setDbIntegration(practice);
        assertEquals(practice, p.getDbIntegration());

        practice = new Practice(PracticeStatus.random());
        p.setDeploymentShakeout(practice);
        assertEquals(practice, p.getDeploymentShakeout());

        practice = new Practice(PracticeStatus.random());
        p.setPrivateProperties(practice);
        assertEquals(practice, p.getPrivateProperties());

        practice = new Practice(PracticeStatus.random());
        p.setRemoteUpgrade(practice);
        assertEquals(practice, p.getRemoteUpgrade());

        practice = new Practice(PracticeStatus.random());
        p.setSingleCommandBuild(practice);
        assertEquals(practice, p.getSingleCommandBuild());

        practice = new Practice(PracticeStatus.random());
        p.setSingleCommandDeploy(practice);
        assertEquals(practice, p.getSingleCommandDeploy());

        practice = new Practice(PracticeStatus.random());
        p.setTemplateValidation(practice);
        assertEquals(practice, p.getTemplateValidation());
    }
}
