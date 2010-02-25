package gov.nih.nci.bda.repository;

import gov.nih.nci.bda.domain.PracticeStatus;
import gov.nih.nci.bda.domain.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

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
        private PracticeStatus mapStatus(String dbValue) {
            if (dbValue.startsWith("'[")) {
                dbValue = dbValue.substring(dbValue.indexOf('[') + 1, dbValue.indexOf('|'));
            }

            if ("(/)".equals(dbValue)) {
                return PracticeStatus.SUCCESS;
            }
            if ("(x)".equals(dbValue)) {
                return PracticeStatus.NOT_SUCCESSFUL;
            }
            if ("(+)".equals(dbValue)) {
                return PracticeStatus.OPTIONAL;
            }
            if ("(off)".equals(dbValue)) {
                return PracticeStatus.DEFERRED;
            }
            if ("(!)".equals(dbValue)) {
                return PracticeStatus.PROBLEM;
            }
            if ("(on)".equals(dbValue)) {
                return PracticeStatus.WAIVER;
            }
            throw new IllegalArgumentException(dbValue + " is not recognized.");
        }


    }
}
