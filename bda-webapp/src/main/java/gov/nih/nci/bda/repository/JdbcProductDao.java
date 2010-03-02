package gov.nih.nci.bda.repository;

import gov.nih.nci.bda.domain.Practice;
import gov.nih.nci.bda.domain.PracticeStatus;
import gov.nih.nci.bda.domain.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcProductDao extends SimpleJdbcDaoSupport implements ProductDao {
    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = LogFactory.getLog(getClass());


    public List<Product> getProductList() {
        logger.info("Getting products!");
        return getSimpleJdbcTemplate().query(
                "select id, product, certification_status, single_command_build, single_command_deployment," +
                        " remote_upgrade, database_integration, template_validation, private_properties, ci_build," +
                        " deployment_shakeout, bda_enabled, commandline_installer from project_certification_status",
                new ProductMapper());
    }

    private static class ProductMapper implements ParameterizedRowMapper<Product> {

        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product prod = new Product(rs.getString("product"));
            prod.setId(rs.getInt("id"));
            prod.setCertificationStatus(mapStatus(rs.getString("certification_status")));
            prod.setSingleCommandBuild(mapStatus(rs.getString("single_command_build")));
            prod.setSingleCommandDeploy(mapStatus(rs.getString("single_command_deployment")));
            prod.setRemoteUpgrade(mapStatus(rs.getString("remote_upgrade")));
            prod.setDbIntegration(mapStatus(rs.getString("database_integration")));
            prod.setTemplateValidation(mapStatus(rs.getString("template_validation")));
            prod.setPrivateProperties(mapStatus(rs.getString("private_properties")));
            prod.setCiBuild(mapStatus(rs.getString("ci_build")));
            prod.setDeploymentShakeout(mapStatus(rs.getString("deployment_shakeout")));
            prod.setBdaEnabled(mapStatus(rs.getString("bda_enabled")));
            prod.setCommandLineInstall(mapStatus(rs.getString("commandline_installer")));
            return prod;
        }

        /**
         * Maps legacy data values to the PracticeStatus with which they are associated.
         */
        private Practice mapStatus(String dbValue) throws SQLException {
            String status = dbValue;
            String url = null;
            String altText = null;
            if (dbValue.startsWith("'[")) {
                int firstPipeIndex = dbValue.indexOf('|');
                int secondPipeIndex = dbValue.indexOf('|', firstPipeIndex + 1);
                status = dbValue.substring(dbValue.indexOf('[') + 1, firstPipeIndex);
                url = dbValue.substring(firstPipeIndex + 1, secondPipeIndex);
                altText = dbValue.substring(secondPipeIndex + 1, dbValue.length() - 2);
            }

            if ("(/)".equals(status)) {
                return buildPractice(PracticeStatus.SUCCESS, url, altText);
            }
            if ("(x)".equals(status)) {
                return buildPractice(PracticeStatus.NOT_SUCCESSFUL, url, altText);
            }
            if ("(+)".equals(status)) {
                return buildPractice(PracticeStatus.OPTIONAL, url, altText);
            }
            if ("(off)".equals(status)) {
                return buildPractice(PracticeStatus.DEFERRED, url, altText);
            }
            if ("(!)".equals(status)) {
                return buildPractice(PracticeStatus.PROBLEM, url, altText);
            }
            if ("(on)".equals(status)) {
                return buildPractice(PracticeStatus.WAIVER, url, altText);
            }
            throw new IllegalArgumentException(status + " is not recognized.");
        }

        private Practice buildPractice(PracticeStatus status, String url, String altText) throws SQLException {
            if (url != null) {
                try {
                    if (altText != null) {
                        return new Practice(status, url, altText);
                    }
                    return new Practice(status, url);
                } catch (MalformedURLException e) {
                    throw new SQLException(e);
                }
            }
            return new Practice(status);
        }


    }
}
