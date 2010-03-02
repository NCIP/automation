package gov.nih.nci.bda.domain;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

import java.net.URL;
import java.net.MalformedURLException;

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
    public void shouldGoToDefaultWhenSettingStatusValuesToNull() {
        Product product = new Product("Foo");
        product.setBdaEnabled(PracticeStatus.OPTIONAL);
        assertEquals(PracticeStatus.OPTIONAL, product.getBdaEnabled());
        product.setBdaEnabled((String) null);
        assertEquals("Null strings should set the status to the default.", 
                Product.DEFAULT_STATUS, product.getBdaEnabled());
    }

    @Test
    public void shouldStoreUrlAndAltTextWhenProvided() throws MalformedURLException {
        Product product = new Product("Foo");

        product.setBdaEnabled(PracticeStatus.OPTIONAL, new URL("http://stelligent.com"),
                "Some alternate text to display.");
    }

    @Test
    public void shouldNeverHaveNullPracticeWhenOtherwiseEmptyProduct() {
        Product blank = new Product("TestName");
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
        Product p = new Product("TestName");

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

    @Test
    public void shouldBeAbleToSetWhenUsingStringStatusToo() {
        Product p = new Product("TestName");

        PracticeStatus status = PracticeStatus.random();
        p.setBdaEnabled(status.toString());
        assertEquals(status, p.getBdaEnabled());

        status = PracticeStatus.random();
        p.setCertificationStatus(status.toString());
        assertEquals(status, p.getCertificationStatus());

        status = PracticeStatus.random();
        p.setCiBuild(status.toString());
        assertEquals(status, p.getCiBuild());

        status = PracticeStatus.random();
        p.setCommandLineInstall(status.toString());
        assertEquals(status, p.getCommandLineInstall());

        status = PracticeStatus.random();
        p.setDbIntegration(status.toString());
        assertEquals(status, p.getDbIntegration());

        status = PracticeStatus.random();
        p.setDeploymentShakeout(status.toString());
        assertEquals(status, p.getDeploymentShakeout());

        status = PracticeStatus.random();
        p.setPrivateProperties(status.toString());
        assertEquals(status, p.getPrivateProperties());

        status = PracticeStatus.random();
        p.setRemoteUpgrade(status.toString());
        assertEquals(status, p.getRemoteUpgrade());

        status = PracticeStatus.random();
        p.setSingleCommandBuild(status.toString());
        assertEquals(status, p.getSingleCommandBuild());

        status = PracticeStatus.random();
        p.setSingleCommandDeploy(status.toString());
        assertEquals(status, p.getSingleCommandDeploy());

        status = PracticeStatus.random();
        p.setTemplateValidation(status.toString());
        assertEquals(status, p.getTemplateValidation());
    }

   private void assertDefaultPracticeValue(PracticeStatus status) {
        assertEquals("Expected the default practice", Product.DEFAULT_STATUS, status);
    }
}
